package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.World
import groovy.transform.CompileStatic
import pmauldin.shift.GameScreen
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.Rigidbody
import pmauldin.shift.entities.components.Transform

@CompileStatic
class PhysicsSystem extends IteratingSystem implements LogicSystem {
    ComponentMapper<Rigidbody> mRigidbody
    ComponentMapper<Transform> mTransform

    @Wire
    World b2dWorld

    PhysicsSystem() {
        super(Aspect.all(Rigidbody, Transform))
    }

    @Override
    protected void begin() {
        // TODO move out of here?
        b2dWorld.step((float) GameScreen.MILLIS_PER_LOGIC_TICK * 0.001, 8, 3)
    }

    @Override
    protected void process(int entityId) {
        def transform = mTransform.get(entityId)
        def rigidBody = mRigidbody.get(entityId)

        transform.setPosition(rigidBody.body.getPosition())
        transform.x += rigidBody.xOffset
        transform.y += rigidBody.yOffset
    }
}
