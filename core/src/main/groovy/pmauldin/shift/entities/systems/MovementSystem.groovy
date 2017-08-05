package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import groovy.transform.CompileStatic
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.PositionComponent
import pmauldin.shift.entities.components.VelocityComponent

@CompileStatic
class MovementSystem extends IteratingSystem implements LogicSystem {
    ComponentMapper<PositionComponent> mPositionComponent
    ComponentMapper<VelocityComponent> mVelocityComponent

    MovementSystem() {
        super(Aspect.all(PositionComponent, VelocityComponent))
    }

    @Override
    protected void process(int entityId) {
        def position = mPositionComponent.get(entityId)
        def velocity = mVelocityComponent.get(entityId)

        position.x += velocity.x
        position.y += velocity.y
    }
}
