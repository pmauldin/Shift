package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile
import pmauldin.shift.entities.Entity
import pmauldin.shift.entities.components.Resource

@CompileStatic
class TreeTile extends Tile {
	TreeTile() {
		super(Entity.TREE)
	}

	@Override
	boolean isSolid() {
		return true
	}

	@Override
	boolean isResource() {
		return true
	}

	@Override
	void buildResource(Resource resource) {
		resource.type = "Wood"
	}
}
