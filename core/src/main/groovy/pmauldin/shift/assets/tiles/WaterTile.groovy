package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile
import pmauldin.shift.entities.Entity

@CompileStatic
class WaterTile extends Tile {
	WaterTile() {
		super(Entity.WATER)
	}

	@Override
	boolean isSolid() {
		return true
	}
}
