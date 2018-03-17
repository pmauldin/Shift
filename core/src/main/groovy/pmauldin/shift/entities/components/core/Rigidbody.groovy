package pmauldin.shift.entities.components.core

import com.artemis.Component
import com.badlogic.gdx.physics.box2d.Body
import groovy.transform.CompileStatic

@CompileStatic
class Rigidbody extends Component {
	Body body
	float xOffset = 0.0, yOffset = 0.0
}
