package pmauldin.shift.entities.systems

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.artemis.managers.TagManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import groovy.transform.CompileStatic
import pmauldin.shift.entities.Tags
import pmauldin.shift.entities.components.VelocityComponent

@CompileStatic
class PlayerInputSystem extends BaseSystem {
    ComponentMapper<VelocityComponent> mVelocity

    static final int SPEED = 300
    int playerEntity = -1

    @Override
    void processSystem() {
        if (playerEntity < 0) {
            playerEntity = world.getSystem(TagManager).getEntityId(Tags.PLAYER)
        }

        def velocity = mVelocity.get(playerEntity)
        def velVector = new Vector2()

        if (anyKeyPressed(Keys.LEFT, Keys.A) && !anyKeyPressed(Keys.RIGHT, Keys.D)) {
            velVector.x = -1
        } else if (anyKeyPressed(Keys.RIGHT, Keys.D) && !anyKeyPressed(Keys.LEFT, Keys.A)) {
            velVector.x = 1
        } else {
            velVector.x = 0
        }

        if (anyKeyPressed(Keys.UP, Keys.W) && !anyKeyPressed(Keys.DOWN, Keys.S)) {
            velVector.y = 1
        } else if (anyKeyPressed(Keys.DOWN, Keys.S) && !anyKeyPressed(Keys.UP, Keys.W)) {
            velVector.y = -1
        } else {
            velVector.y = 0
        }

        def normalizedVelocity = velVector.nor().scl(SPEED)
        velocity.x = normalizedVelocity.x
        velocity.y = normalizedVelocity.y
    }

    static boolean anyKeyPressed(int... keys) {
        return keys.any { int key ->
            Gdx.input.isKeyPressed(key)
        }
    }
}
