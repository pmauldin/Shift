package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import groovy.transform.CompileStatic
import pmauldin.shift.Util
import pmauldin.shift.entities.components.Player
import pmauldin.shift.entities.components.Rigidbody

@CompileStatic
class PlayerSystem extends IteratingSystem {
	ComponentMapper<Player> mPlayer
	ComponentMapper<Rigidbody> mRigidbody

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

		if (Util.anyKeyPressed(Keys.LEFT, Keys.A) && !Util.anyKeyPressed(Keys.RIGHT, Keys.D)) {
			currentVelocity.x = -1
		} else if (Util.anyKeyPressed(Keys.RIGHT, Keys.D) && !Util.anyKeyPressed(Keys.LEFT, Keys.A)) {
			currentVelocity.x = 1
		} else {
			currentVelocity.x = 0
		}

		if (Util.anyKeyPressed(Keys.UP, Keys.W) && !Util.anyKeyPressed(Keys.DOWN, Keys.S)) {
			currentVelocity.y = 1
		} else if (Util.anyKeyPressed(Keys.DOWN, Keys.S) && !Util.anyKeyPressed(Keys.UP, Keys.W)) {
			currentVelocity.y = -1
		} else {
			currentVelocity.y = 0
		}

		body.setLinearVelocity(currentVelocity.nor().scl(SPEED))

		camera.position.set(body.position.x, body.position.y, 0)
	}
}
