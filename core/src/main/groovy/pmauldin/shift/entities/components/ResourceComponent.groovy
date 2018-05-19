package pmauldin.shift.entities.components

import com.artemis.Component
import groovy.transform.CompileStatic
import pmauldin.shift.entities.items.Resource

@CompileStatic
class ResourceComponent extends Component {
	Resource resource
	int count = 1
}
