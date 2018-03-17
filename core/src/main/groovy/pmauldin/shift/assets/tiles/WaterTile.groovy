package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile

@CompileStatic
class WaterTile extends Tile {
	@Override
	int xTileOffset() {
		return 13
	}

	@Override
	int yTileOffset() {
		return 6
	}

	@Override
	boolean isSolid() {
		return true
	}
}
