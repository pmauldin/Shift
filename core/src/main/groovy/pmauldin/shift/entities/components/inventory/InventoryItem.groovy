package pmauldin.shift.entities.components.inventory

import groovy.transform.CompileStatic
import pmauldin.shift.entities.items.Resource

@CompileStatic
class InventoryItem {
	Resource resource
	int count
}
