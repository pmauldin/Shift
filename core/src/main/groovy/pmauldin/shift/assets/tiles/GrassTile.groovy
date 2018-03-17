package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile

@CompileStatic
class GrassTile extends Tile {
	@Override
	int xTileOffset() {
		return 14
	}

	@Override
	int yTileOffset() {
		return 1
	}
}
