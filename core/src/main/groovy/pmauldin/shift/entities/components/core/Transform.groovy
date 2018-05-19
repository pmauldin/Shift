package pmauldin.shift.entities.components.core

import com.artemis.Component
import com.badlogic.gdx.math.Vector2
import groovy.transform.CompileStatic

@CompileStatic
class Transform extends Component {
	Vector2 position = new Vector2()

	float getX() {
		position.x
	}

	void setX(float x) {
		position.x = x
	}

	float getY() {
		position.y
	}

	void setY(float y) {
		position.y = y
	}

	void setPosition(Vector2 position) {
		this.position = position
	}

	boolean equals(o) {
		if (this.is(o)) return true
		if (getClass() != o.class) return false

		Transform transform = (Transform) o

		if (Float.compare(transform.x, x) != 0) return false
		if (Float.compare(transform.y, y) != 0) return false

		return true
	}

	int hashCode() {
		int result
		result = (x != +0.0f ? Float.floatToIntBits(x) : 0)
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0)
		return result
	}
}
