package pmauldin.shift.entities.systems.inventory

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import groovy.transform.CompileStatic
import pmauldin.shift.entities.EntityManager
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.Components
import pmauldin.shift.entities.components.events.NewInventoryItem
import pmauldin.shift.entities.components.inventory.InventoryItem
import pmauldin.shift.entities.items.Resource

@CompileStatic
class InventorySystem extends IteratingSystem implements LogicSystem {

	InventorySystem() {
		super(Aspect.all(NewInventoryItem))
	}

	@Override
	protected void process(int entityId) {
		transferItem(Components.mNewInventoryItem.get(entityId))
		EntityManager.delete(entityId)
	}

	private static void transferItem(NewInventoryItem transfer) {
		if (!Components.mInventory.has(transfer.ownerId)) return
		def itemsMap = Components.mInventory.get(transfer.ownerId).itemsMap
		def newItem = transfer.item

		def item = itemsMap.get(newItem.resource, new InventoryItem(resource: newItem.resource, count: 0))
		item.count += newItem.count
		itemsMap.put(newItem.resource, item)
	}

	static void printInventory(int entityId) {
		def inventory = Components.mInventory.get(entityId)
		if (inventory == null) return

		System.out.println("\n******* Player Inventory *******\n")
		inventory.itemsMap.each { Resource resource, InventoryItem item ->
			System.out.println("${resource.type}: ${item.count}")
		}
		System.out.println("\n********************************\n")
	}

	static boolean consumeItem(Resource resource, int entityId) {
		def inventory = Components.mInventory.get(entityId)
		def item = inventory?.itemsMap?.get(resource)
		if (item == null) return false

		item.count--
		if (item.count <= 0) {
			inventory.itemsMap.remove(resource)
		}

		true
	}
}
