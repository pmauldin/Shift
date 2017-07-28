package pmauldin.shift.entities.systems

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.artemis.managers.TagManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import pmauldin.shift.entities.Tags
import pmauldin.shift.entities.components.Velocity

class PlayerInputSystem extends BaseSystem {
    ComponentMapper<Velocity> mVelocity

    static final int SPEED = 300
    int playerEntity = -1

    @Override
    void processSystem() {
        if (playerEntity < 0) {
            playerEntity = world.getSystem(TagManager).getEntityId(Tags.PLAYER)
        }

        def velocity = mVelocity.get(playerEntity)

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = SPEED
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -SPEED
        } else {
            velocity.x = 0
        }
    }
}
