package pmauldin.shift.entities.components

import com.artemis.Component
import com.badlogic.gdx.math.Vector2
import groovy.transform.CompileStatic

@CompileStatic
class Velocity extends Component {
	Vector2 velocity = new Vector2()

	void setX(float x) {
		velocity.x = x
	}

	void setY(float y) {
		velocity.y = y
	}

	Vector2 nor() {
		velocity.nor()
	}
}
