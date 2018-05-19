package pmauldin.shift.entities.systems

import com.artemis.BaseSystem
import groovy.transform.CompileStatic
import pmauldin.shift.entities.EntityManager
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.Components
import pmauldin.shift.entities.components.inventory.InventoryItem

@CompileStatic
class ResourceSystem extends BaseSystem implements LogicSystem {

	@Override
	protected void processSystem() {}

	static void interact(int characterId, int resourceId) {
		def component = Components.mResource.get(resourceId)
		def resource = component.resource
		System.out.println("$characterId got ${resource.type}")

		def inventoryTransfer = EntityManager.create()
		def newItem = Components.mNewInventoryItem.create(inventoryTransfer)
		newItem.ownerId = characterId
		newItem.item = new InventoryItem(resource: resource, count: 1)

		component.count--

		if (component.count <= 0) {
			EntityManager.delete(resourceId)
		}
	}
}
