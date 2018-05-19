package pmauldin.shift.entities.systems.inventory

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import groovy.transform.CompileStatic
import pmauldin.shift.entities.EntityManager
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.Components
import pmauldin.shift.entities.components.events.NewInventoryItem
import pmauldin.shift.entities.components.inventory.InventoryItem

@CompileStatic
class InventorySystem extends IteratingSystem implements LogicSystem {

	InventorySystem() {
		super(Aspect.all(NewInventoryItem))
	}

	@Override
	protected void begin() {}

	@Override
	protected void process(int entityId) {
		transferItem(Components.mNewInventoryItem.get(entityId))
		EntityManager.delete(entityId)
	}

	private static void transferItem(NewInventoryItem transfer) {
		if (!Components.mInventory.has(transfer.ownerId)) return
		def itemsMap = Components.mInventory.get(transfer.ownerId).itemsMap
		def newItem = transfer.item

		def item = itemsMap.get(newItem.label, new InventoryItem(label: newItem.label, count: 0))
		item.count += newItem.count
		itemsMap.put(newItem.label, item)
	}

	static void printInventory(int entityId) {
		def inventory = Components.mInventory.get(entityId)
		if (inventory == null) return

		System.out.println("\n******* Player Inventory *******\n")
		inventory.itemsMap.each { String label, InventoryItem item ->
			System.out.println("${item.label}: ${item.count}")
		}
		System.out.println("\n********************************\n")
	}
}
