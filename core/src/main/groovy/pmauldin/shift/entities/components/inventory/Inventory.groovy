package pmauldin.shift.entities.components.inventory

import com.artemis.Component
import groovy.transform.CompileStatic

@CompileStatic
class Inventory extends Component {
	Map<String, InventoryItem> itemsMap
}
