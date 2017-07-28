package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import pmauldin.shift.entities.components.Position
import pmauldin.shift.entities.components.Velocity

class MovementSystem extends IteratingSystem {
    ComponentMapper<Position> mPosition
    ComponentMapper<Velocity> mVelocity

    MovementSystem() {
        super(Aspect.all(Position, Velocity))
    }

    @Override
    protected void process(int entityId) {
        def velocity = mVelocity.get(entityId)

        if (velocity.x != 0) {
            def position = mPosition.get(entityId)
            position.x += velocity.x * Gdx.graphics.getDeltaTime()
        }
    }
}
