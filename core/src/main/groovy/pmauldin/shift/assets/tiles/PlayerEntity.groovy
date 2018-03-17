package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile

@CompileStatic
class PlayerEntity extends Tile {
	@Override
	int xTileOffset() {
		return 0
	}

	@Override
	int yTileOffset() {
		return 8
	}
}
