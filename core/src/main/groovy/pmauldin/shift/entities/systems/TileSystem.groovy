package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.badlogic.gdx.math.Vector2
import groovy.transform.CompileStatic
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.Components
import pmauldin.shift.entities.components.TileComponent

@CompileStatic
class TileSystem extends BaseEntitySystem implements LogicSystem {

	private static final Map<Vector2, List<Integer>> tileMap = new HashMap<>()

	TileSystem() {
		super(Aspect.all(TileComponent))
	}

	static List<Integer> getTileIdsAtPosition(int x, int y) {
		getTileIdsPosition(new Vector2(x, y))
	}

	static List<Integer> getTileIdsPosition(Vector2 position) {
		tileMap.get(new Vector2(position.x as int, position.y as int))
	}

	@Override
	protected void processSystem() {

	}

	@Override
	void inserted(int entityId) {
		def position = Components.mTransform.get(entityId).position
		// tiles should only be placed at integer positions
		assert (position.x as int == position.x)
		assert (position.y as int == position.y)
		def tiles = tileMap.getOrDefault(position, new ArrayList<>())
		tiles.add(entityId)
		tileMap.put(position, tiles)
	}

	@Override
	void removed(int entityId) {
		def transform = Components.mTransform.get(entityId)
		def tiles = getTileIdsPosition(transform.position)
		if (!tiles?.contains(entityId)) {
			println("\nERROR: Tile[$entityId] was removed but was not in tileMap")
		}

		tiles.removeElement(entityId)
		tileMap.put(transform.position, tiles)
	}
}
