package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import groovy.transform.CompileStatic
import pmauldin.shift.entities.components.Components
import pmauldin.shift.entities.components.Velocity
import pmauldin.shift.entities.components.core.Rigidbody

@CompileStatic
class MovementSystem extends IteratingSystem {

	MovementSystem() {
		super(Aspect.all(Velocity, Rigidbody))
	}

	@Override
	protected void process(int entityId) {
		def body = Components.mRigidbody.get(entityId).body
		def currentVelocity = Components.mVelocity.get(entityId)

		body.setLinearVelocity(currentVelocity.nor().scl(currentVelocity.speed))
	}
}
