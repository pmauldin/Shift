package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.QueryCallback
import com.badlogic.gdx.physics.box2d.World
import groovy.transform.CompileStatic
import pmauldin.shift.entities.EntityManager
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.Direction
import pmauldin.shift.entities.components.Resource
import pmauldin.shift.entities.components.core.Rigidbody
import pmauldin.shift.entities.components.events.InputEvent
import pmauldin.shift.entities.components.events.InputEvent.InputType
import pmauldin.shift.entities.systems.inventory.InventorySystem

@CompileStatic
class PlayerInputSystem extends IteratingSystem implements LogicSystem {
	static ComponentMapper<InputEvent> mInputEvent
	static ComponentMapper<Direction> mDirection
	static ComponentMapper<Resource> mResource
	static ComponentMapper<Rigidbody> mRigidbody
	
	private static int[] ACTION = [Keys.SPACE]
	private static int[] INVENTORY = [Keys.I]

	@Wire
	World b2dWorld

	PlayerInputSystem() {
		super(Aspect.all(InputEvent))
	}

	@Override
	protected void process(int entityId) {
		def inputEvent = mInputEvent.get(entityId)
		def consumed = processInput(inputEvent)
		if (consumed) {
			EntityManager.delete(entityId)
		}
	}
	
	private boolean processInput(InputEvent inputEvent) {
		def playerId = EntityManager.playerId

		def keyCode = inputEvent.keyCode
		def type = inputEvent.type
		def consumed = true

		if (keyCode in ACTION && type == InputType.DOWN) {
			attack(playerId)
		} else if (keyCode in INVENTORY && type == InputType.DOWN) {
			InventorySystem.printInventory(playerId)
		} else {
			consumed = false
		}

		consumed
	}

	private void attack(int playerId) {
		def direction = mDirection.get(playerId)
		def body = mRigidbody.get(playerId).body
		def reticulePosition = body.position.cpy()

		reticulePosition.x += 0.5f * direction.x as float
		reticulePosition.y += (0.5f * direction.y as float)

		b2dWorld.QueryAABB(new QueryCallback() {
			@Override
			boolean reportFixture(Fixture fixture) {
				try {
					int tileId = fixture.body.userData as int
					if (mResource.has(tileId)) {
						ResourceSystem.interact(playerId, tileId)
					}
				} catch (Exception ex) {
					System.out.println("Error occurred while hitting tile: ${ex.message}")
				}

				return true
			}
		}, reticulePosition.x - 0.05f as float, reticulePosition.y - 0.05f as float,
				reticulePosition.x + 0.05f as float, reticulePosition.y + 0.05f as float)
	}
}
