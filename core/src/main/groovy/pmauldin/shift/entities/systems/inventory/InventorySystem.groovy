package pmauldin.shift.entities.systems.inventory

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import groovy.transform.CompileStatic
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.inventory.Inventory
import pmauldin.shift.entities.components.inventory.InventoryItem

@CompileStatic
class InventorySystem extends BaseSystem implements LogicSystem {
	static ComponentMapper<Inventory> mInventory

	@Override
	protected void processSystem() {
	}

	static void printInventory(int entityId) {
		def inventory = mInventory.get(entityId)
		if (inventory == null) return

		System.out.println("\n******* Player Inventory *******\n")
		inventory.itemsMap.each { String label, InventoryItem item ->
			System.out.println("${item.label}: ${item.count}")
		}
		System.out.println("\n********************************")
	}
}
