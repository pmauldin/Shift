package pmauldin.shift.entities.systems.inventory

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import groovy.transform.CompileStatic
import pmauldin.shift.entities.EntityManager
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.inventory.Inventory
import pmauldin.shift.entities.components.inventory.InventoryItem
import pmauldin.shift.entities.components.events.NewInventoryItem

@CompileStatic
class InventorySystem extends IteratingSystem implements LogicSystem {
	static ComponentMapper<Inventory> mInventory
	static ComponentMapper<NewInventoryItem> mNewInventoryItem

	InventorySystem() {
		super(Aspect.all(NewInventoryItem))
	}

	@Override
	protected void begin() {}

	@Override
	protected void process(int entityId) {
		transferItem(mNewInventoryItem.get(entityId))
		EntityManager.delete(entityId)
	}

	private static void transferItem(NewInventoryItem transfer) {
		if (!mInventory.has(transfer.ownerId)) return
		def itemsMap = mInventory.get(transfer.ownerId).itemsMap
		def newItem = transfer.item

		def item = itemsMap.get(newItem.label, new InventoryItem(label: newItem.label, count: 0))
		item.count += newItem.count
		itemsMap.put(newItem.label, item)
	}

	static void printInventory(int entityId) {
		def inventory = mInventory.get(entityId)
		if (inventory == null) return

		System.out.println("\n******* Player Inventory *******\n")
		inventory.itemsMap.each { String label, InventoryItem item ->
			System.out.println("${item.label}: ${item.count}")
		}
		System.out.println("\n********************************\n")
	}
}
