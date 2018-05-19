package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import groovy.transform.CompileStatic
import pmauldin.shift.Util.Keyboard
import pmauldin.shift.entities.components.Direction
import pmauldin.shift.entities.components.Player
import pmauldin.shift.entities.components.Velocity
import pmauldin.shift.entities.components.core.Renderable
import pmauldin.shift.entities.components.core.Rigidbody

@CompileStatic
class PlayerSystem extends IteratingSystem {
	class SpriteDirection {
		static final int DOWN = 0, LEFT = 1, RIGHT = 2, UP = 3
	}

	ComponentMapper<Rigidbody> mRigidbody
	ComponentMapper<Renderable> mRenderable
	ComponentMapper<Velocity> mVelocity
	ComponentMapper<Direction> mDirection

	static final int SPEED = 10.0f
	private final Camera camera

	PlayerSystem(Camera camera) {
		super(Aspect.all(Player, Rigidbody))
		this.camera = camera
	}

	@Override
	protected void process(int entityId) {
		def body = mRigidbody.get(entityId).body
		def renderable = mRenderable.get(entityId)
		def currentVelocity = mVelocity.get(entityId)
		def direction = mDirection.get(entityId)

		if (Keyboard.isLeftPressed()) {
			currentVelocity.x = -1
			direction.x = -1
			direction.y = 0
			renderable.activeSprite = SpriteDirection.LEFT
		} else if (Keyboard.isRightPressed()) {
			currentVelocity.x = 1
			direction.x = 1
			direction.y = 0
			renderable.activeSprite = SpriteDirection.RIGHT
		} else {
			currentVelocity.x = 0
		}

		if (Keyboard.isUpPressed()) {
			currentVelocity.y = 1
			direction.y = 1
			direction.x = 0
			renderable.activeSprite = SpriteDirection.UP
		} else if (Keyboard.isDownPressed()) {
			currentVelocity.y = -1
			direction.y = -1
			direction.x = 0
			renderable.activeSprite = SpriteDirection.DOWN
		} else {
			currentVelocity.y = 0
		}

		body.setLinearVelocity(currentVelocity.nor().scl(SPEED))

		camera.position.set(body.position.x, body.position.y, 0)
	}
}
