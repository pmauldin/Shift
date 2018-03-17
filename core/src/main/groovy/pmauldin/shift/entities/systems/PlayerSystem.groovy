package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.QueryCallback
import com.badlogic.gdx.physics.box2d.World
import groovy.transform.CompileStatic
import pmauldin.shift.GameScreen
import pmauldin.shift.Util.Keyboard
import pmauldin.shift.entities.components.Player
import pmauldin.shift.entities.components.Resource
import pmauldin.shift.entities.components.Rigidbody

@CompileStatic
class PlayerSystem extends IteratingSystem {
	ComponentMapper<Player> mPlayer
	ComponentMapper<Rigidbody> mRigidbody
	ComponentMapper<Resource> mResource

	@Wire
	World b2dWorld

	static final int SPEED = 10.0f

	private Vector2 currentVelocity
	private final Camera camera

	PlayerSystem(Camera camera) {
		super(Aspect.all(Player, Rigidbody))
		currentVelocity = Vector2.Zero
		this.camera = camera
	}

	@Override
	protected void process(int entityId) {
		def player = mPlayer.get(entityId)
		def body = mRigidbody.get(entityId).body

		if (Keyboard.isLeftPressed()) {
			currentVelocity.x = -1
			player.xDirection = -1
			if (!Keyboard.isUpPressed() && !Keyboard.isDownPressed()) {
				player.yDirection = 0
			}
		} else if (Keyboard.isRightPressed()) {
			currentVelocity.x = 1
			player.xDirection = 1
			if (!Keyboard.isUpPressed() && !Keyboard.isDownPressed()) {
				player.yDirection = 0
			}
		} else {
			currentVelocity.x = 0
		}

		if (Keyboard.isUpPressed()) {
			currentVelocity.y = 1
			player.yDirection = 1
			if (!Keyboard.isLeftPressed() && !Keyboard.isRightPressed()) {
				player.xDirection = 0
			}
		} else if (Keyboard.isDownPressed()) {
			currentVelocity.y = -1
			player.yDirection = -1
			if (!Keyboard.isLeftPressed() && !Keyboard.isRightPressed()) {
				player.xDirection = 0
			}
		} else {
			currentVelocity.y = 0
		}

		if (Keyboard.anyKeyJustPressed(Keys.SPACE)) {
			attack(entityId)
		}

		body.setLinearVelocity(currentVelocity.nor().scl(SPEED))

		camera.position.set(body.position.x, body.position.y, 0)
	}

	private void attack(int playerId) {
		def player = mPlayer.get(playerId)
		def body = mRigidbody.get(playerId).body
		def reticulePosition = body.position.cpy()

		reticulePosition.x += 0.5f * player.xDirection as float
		reticulePosition.y += (0.5f * player.yDirection as float)

		b2dWorld.QueryAABB(new QueryCallback() {
			@Override
			boolean reportFixture(Fixture fixture) {
				try {
					int tileId = fixture.body.userData as int
					if (mResource.has(tileId)) {
						def resource = mResource.get(tileId)
						System.out.println("Hit " + resource.type)
						GameScreen.entityWorld.delete(tileId)
					}
				} catch (Exception ex) {
					System.out.println("Error occurred while hitting tile: " + ex.toString())
				}

				return true
			}
		}, reticulePosition.x - 0.05f as float, reticulePosition.y - 0.05f as float,
				reticulePosition.x + 0.05f as float, reticulePosition.y + 0.05f as float)
	}
}
