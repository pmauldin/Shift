package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import groovy.transform.CompileStatic
import pmauldin.shift.entities.components.Player
import pmauldin.shift.entities.components.Rigidbody

@CompileStatic
class PlayerSystem extends IteratingSystem {
    ComponentMapper<Player> mPlayer
    ComponentMapper<Rigidbody> mRigidbody

    static final int SPEED = 10.0f

    private Vector2 velocity

    PlayerSystem() {
        super(Aspect.all(Player, Rigidbody))
        velocity = new Vector2()
    }

    @Override
    protected void process(int entityId) {
        def player = mPlayer.get(entityId)
        def body = mRigidbody.get(entityId).body

        if (anyKeyPressed(Keys.LEFT, Keys.A) && !anyKeyPressed(Keys.RIGHT, Keys.D)) {
            velocity.x = -1
        } else if (anyKeyPressed(Keys.RIGHT, Keys.D) && !anyKeyPressed(Keys.LEFT, Keys.A)) {
            velocity.x = 1
        } else {
            velocity.x = 0
        }

        if (anyKeyPressed(Keys.UP, Keys.W) && !anyKeyPressed(Keys.DOWN, Keys.S)) {
            velocity.y = 1
        } else if (anyKeyPressed(Keys.DOWN, Keys.S) && !anyKeyPressed(Keys.UP, Keys.W)) {
            velocity.y = -1
        } else {
            velocity.y = 0
        }

        body.setLinearVelocity(velocity.nor().scl(SPEED))
    }

    static boolean anyKeyPressed(int... keys) {
        return keys.any { int key ->
            Gdx.input.isKeyPressed(key)
        }
    }
}
