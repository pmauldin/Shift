package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.QueryCallback
import com.badlogic.gdx.physics.box2d.World
import groovy.transform.CompileStatic
import pmauldin.shift.Util.Keyboard
import pmauldin.shift.entities.EntityFactory
import pmauldin.shift.entities.EntityManager
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.Components
import pmauldin.shift.entities.components.events.InputEvent
import pmauldin.shift.entities.components.events.InputEvent.InputType
import pmauldin.shift.entities.systems.inventory.InventorySystem

@CompileStatic
class PlayerInputSystem extends IteratingSystem implements LogicSystem {
	class SpriteDirection {
		static final int DOWN = 0, LEFT = 1, RIGHT = 2, UP = 3
	}

	private static int[] ACTION = [Keys.SPACE]
	private static int[] INVENTORY = [Keys.I]

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
			attack()
		} else if (keyCode in INVENTORY && type == InputType.PRESSED) {
			InventorySystem.printInventory(playerId)
		} else if (keyCode in [Keys.R] && type == InputType.PRESSED) {
			place()
		} else {
			consumed = false
		}

		consumed
	}

	private void place() {
		def inventory = Components.mInventory.get(playerId).itemsMap
		if (inventory.size() == 0) return

		def selectedResource = inventory.get(inventory.keySet().first()).resource
		if (!InventorySystem.consumeItem(selectedResource, playerId)) return
		System.out.println("Placing ${selectedResource.type}")
		EntityFactory.createTile(selectedResource.tile, 10, 10, 1)
	}

	private void attack() {
		def direction = Components.mDirection.get(playerId)
		def body = Components.mRigidbody.get(playerId).body
		def reticulePosition = body.position.cpy()

		reticulePosition.x += 0.5f * direction.x as float
		reticulePosition.y += (0.5f * direction.y as float)

		box2dWorld.QueryAABB(new QueryCallback() {
			@Override
			boolean reportFixture(Fixture fixture) {
				try {
					int tileId = fixture.body.userData as int
					if (Components.mResource.has(tileId)) {
						ResourceSystem.interact(playerId, tileId)
					}
				} catch (Exception ex) {
					System.out.println("Error occurred while hitting tile: ${ex.message}")
				}

				return false
			}
		}, reticulePosition.x - 0.05f as float, reticulePosition.y - 0.05f as float,
				reticulePosition.x + 0.05f as float, reticulePosition.y + 0.05f as float)
	}
}
