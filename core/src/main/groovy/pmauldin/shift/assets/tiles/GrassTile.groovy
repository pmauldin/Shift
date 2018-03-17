package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile
import pmauldin.shift.entities.Entity

@CompileStatic
class GrassTile extends Tile {
	GrassTile() {
		super(Entity.GRASS)
	}
}
