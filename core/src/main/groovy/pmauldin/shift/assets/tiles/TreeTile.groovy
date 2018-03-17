package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile
import pmauldin.shift.entities.Entity

@CompileStatic
class TreeTile extends Tile {
	TreeTile() {
		super(Entity.TREE)
	}

	@Override
	boolean isSolid() {
		return true
	}
}
