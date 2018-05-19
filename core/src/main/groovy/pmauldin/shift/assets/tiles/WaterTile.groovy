package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile
import pmauldin.shift.entities.Sprite

@CompileStatic
class WaterTile extends Tile {
	WaterTile() {
		super(Sprite.WATER)
	}

	@Override
	boolean isSolid() {
		true
	}
}
