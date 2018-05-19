package pmauldin.shift.entities.components.inventory

import com.artemis.Component
import groovy.transform.CompileStatic
import pmauldin.shift.entities.items.Resource

@CompileStatic
class Inventory extends Component {
	Map<Resource, InventoryItem> itemsMap
}
