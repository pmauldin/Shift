package pmauldin.shift.entities.components.events

import com.artemis.Component
import groovy.transform.CompileStatic
import pmauldin.shift.entities.components.inventory.InventoryItem

@CompileStatic
class NewInventoryItem extends Component {
	InventoryItem item
	int ownerId
}
