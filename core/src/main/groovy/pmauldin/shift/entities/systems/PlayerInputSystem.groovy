package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import groovy.transform.CompileStatic
import pmauldin.shift.Util.Keyboard
import pmauldin.shift.assets.Tiles
import pmauldin.shift.entities.EntityFactory
import pmauldin.shift.entities.EntityManager
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.Components
import pmauldin.shift.entities.components.events.InputEvent
import pmauldin.shift.entities.components.events.InputEvent.InputType
import pmauldin.shift.entities.components.events.NewInventoryItem
import pmauldin.shift.entities.components.inventory.InventoryItem
import pmauldin.shift.entities.systems.inventory.InventorySystem

@CompileStatic
class PlayerInputSystem extends IteratingSystem implements LogicSystem {
	class SpriteDirection {
		static final int DOWN = 0, LEFT = 1, RIGHT = 2, UP = 3
	}

	private static final int[] ACTION = [Keys.SPACE]
	private static final int[] INVENTORY = [Keys.I]

	private static final Vector2 RETICULE_OFFSET = new Vector2(0.5f, 0.28f)

	private final Camera camera

	private Integer playerId

	@Wire
	private final World box2dWorld

	PlayerInputSystem(Camera camera) {
		super(Aspect.all(InputEvent))
		this.camera = camera
	}

	@Override
	protected void process(int entityId) {
		def inputEvent = Components.mInputEvent.get(entityId)
		def consumed = processInput(inputEvent)
		if (consumed) {
			EntityManager.delete(entityId)
		}
	}

	@Override
	protected void begin() {
		if (!playerId) playerId = EntityManager.playerId
		def direction = Components.mDirection.get(playerId)
		def velocity = Components.mVelocity.get(playerId)
		def renderable = Components.mRenderable.get(playerId)

		if (Keyboard.isUpPressed() && velocity.y != -1) {
			velocity.y = 1
			direction.y = 1
			direction.x = 0
			renderable.activeSprite = SpriteDirection.UP
		} else if (Keyboard.isDownPressed()) {
			velocity.y = -1
			direction.y = -1
			direction.x = 0
			renderable.activeSprite = SpriteDirection.DOWN
		} else {
			velocity.y = 0
		}

		if (Keyboard.isLeftPressed() && velocity.x != 1) {
			velocity.x = -1
			direction.x = -1
			direction.y = 0
			renderable.activeSprite = SpriteDirection.LEFT
		} else if (Keyboard.isRightPressed()) {
			velocity.x = 1
			direction.x = 1
			direction.y = 0
			renderable.activeSprite = SpriteDirection.RIGHT
		} else {
			velocity.x = 0
		}

		def body = Components.mRigidbody.get(playerId).body
		camera.position.set(body.position.x, body.position.y, 0)
	}

	private boolean processInput(InputEvent inputEvent) {
		def keyCode = inputEvent.keyCode
		def type = inputEvent.type
		def consumed = true

		if (keyCode in ACTION && type == InputType.PRESSED) {
			gather()
		} else if (keyCode in INVENTORY && type == InputType.PRESSED) {
			InventorySystem.printInventory(playerId)
		} else if (keyCode in [Keys.R] && type == InputType.PRESSED) {
			place()
		} else if (keyCode in [Keys.C] && type == InputType.PRESSED) {
			InventorySystem.transferItem(new NewInventoryItem(item: new InventoryItem(resource: Tiles.TREE.buildResource(), count: 100), ownerId: playerId))
		} else {
			consumed = false
		}

		consumed
	}

	private void place() {
		def inventory = Components.mInventory.get(playerId).itemsMap
		if (inventory.size() == 0) return

		def selectedResource = inventory.get(inventory.keySet().first()).resource

		def target = getTarget()
		def tiles = TileSystem.getTileIdsPosition(target)
		def tileFree = !tiles || getTilesAtTarget().every {
			!Components.mTile.get(it).tile.isSolid()
		}

		if (!tileFree || !InventorySystem.consumeItem(selectedResource, playerId)) return
		EntityFactory.createTile(selectedResource.tile, target.x as int, target.y as int)
	}

	private void gather() {
		def tiles = getTilesAtTarget()
		def resource = tiles.find {
			Components.mResource.has(it)
		}
		if (!resource) return
		ResourceSystem.interact(playerId, resource)
	}

	private List<Integer> getTilesAtTarget() {
		def tiles = TileSystem.getTileIdsPosition(getTarget())
		return tiles ? tiles : new ArrayList<Integer>()
	}

	private Vector2 getTarget() {
		def direction = Components.mDirection.get(playerId)
		def position = Components.mTransform.get(playerId).position.cpy()
		def reticulePosition = position.cpy().add(RETICULE_OFFSET)

		reticulePosition.x += 0.8f * direction.x as float
		reticulePosition.y += 0.8f * direction.y as float

		return reticulePosition
	}
}
