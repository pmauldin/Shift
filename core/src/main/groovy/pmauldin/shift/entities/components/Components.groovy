package pmauldin.shift.entities.components

import com.artemis.ComponentMapper
import pmauldin.shift.entities.components.core.Renderable
import pmauldin.shift.entities.components.core.Rigidbody
import pmauldin.shift.entities.components.core.Transform
import pmauldin.shift.entities.components.events.InputEvent
import pmauldin.shift.entities.components.events.NewInventoryItem
import pmauldin.shift.entities.components.inventory.Inventory

class Components {
	static ComponentMapper<Renderable> mRenderable
	static ComponentMapper<Rigidbody> mRigidbody
	static ComponentMapper<Transform> mTransform

	static ComponentMapper<InputEvent> mInputEvent

	static ComponentMapper<Direction> mDirection
	static ComponentMapper<Velocity> mVelocity

	static ComponentMapper<TileComponent> mTile
	static ComponentMapper<ResourceComponent> mResource
	static ComponentMapper<Inventory> mInventory
	static ComponentMapper<NewInventoryItem> mNewInventoryItem
}
