package pmauldin.shift.entities.systems

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import groovy.transform.CompileStatic
import pmauldin.shift.entities.EntityManager
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.Resource
import pmauldin.shift.entities.components.inventory.InventoryItem
import pmauldin.shift.entities.components.events.NewInventoryItem

@CompileStatic
class ResourceSystem extends BaseSystem implements LogicSystem {
	static ComponentMapper<Resource> mResource
	static ComponentMapper<NewInventoryItem> mNewInventoryItem

	@Override
	protected void processSystem() {}

	static void interact(int characterId, int resourceId) {
		def resource = mResource.get(resourceId)
		System.out.println("$characterId got ${resource.type}")

		def inventoryTransfer = EntityManager.create()
		def newItem = mNewInventoryItem.create(inventoryTransfer)
		newItem.ownerId = characterId
		newItem.item = new InventoryItem(label: resource.type, count: 1)

		resource.count--

		if (resource.count <= 0) {
			EntityManager.delete(resourceId)
		}
	}
}
