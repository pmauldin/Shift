package pmauldin.shift.entities.systems.inventory

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import groovy.transform.CompileStatic
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.inventory.Inventory
import pmauldin.shift.entities.components.inventory.InventoryItem
import pmauldin.shift.entities.components.inventory.NewInventoryItem

@CompileStatic
class InventoryTransferSystem extends IteratingSystem implements LogicSystem {
	ComponentMapper<Inventory> mInventory
	ComponentMapper<NewInventoryItem> mNewInventoryItem

	InventoryTransferSystem() {
		super(Aspect.all(NewInventoryItem))
	}

	@Override
	protected void begin() {
	}

	@Override
	protected void process(int entityId) {
		def newItem = mNewInventoryItem.get(entityId).item
		def itemsMap = mInventory.get(entityId).itemsMap

		def item = itemsMap.get(newItem.label, new InventoryItem(label: newItem.label, count: 0))
		item.count += newItem.count
		itemsMap.put(newItem.label, item)

		mNewInventoryItem.remove(entityId)
	}
}
