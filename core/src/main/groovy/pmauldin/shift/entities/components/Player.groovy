package pmauldin.shift.entities.components

import com.artemis.Component
import groovy.transform.CompileStatic

@CompileStatic
class Player extends Component {
	Integer xDirection
	Integer yDirection
	Integer reticuleId
}
