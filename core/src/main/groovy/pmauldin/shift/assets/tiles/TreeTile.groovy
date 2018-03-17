package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile

@CompileStatic
class TreeTile extends Tile {
	@Override
	int xTileOffset() {
		return 8
	}

	@Override
	int yTileOffset() {
		return 1
	}

	@Override
	boolean isSolid() {
		return true
	}
}
