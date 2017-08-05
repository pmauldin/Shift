package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import groovy.transform.CompileStatic
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.TransformComponent
import pmauldin.shift.entities.components.VelocityComponent

@CompileStatic
class MovementSystem extends IteratingSystem implements LogicSystem {
    ComponentMapper<TransformComponent> mPositionComponent
    ComponentMapper<VelocityComponent> mVelocityComponent

    MovementSystem() {
        super(Aspect.all(TransformComponent, VelocityComponent))
    }

    @Override
    protected void process(int entityId) {
        def position = mPositionComponent.get(entityId)
        def velocity = mVelocityComponent.get(entityId)

        float delta = Gdx.app.graphics.getDeltaTime()
        position.x += velocity.x * delta
        position.y += velocity.y * delta
    }
}
