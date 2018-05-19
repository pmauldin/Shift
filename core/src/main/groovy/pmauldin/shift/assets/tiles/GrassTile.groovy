package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile
import pmauldin.shift.entities.Sprite

@CompileStatic
class GrassTile extends Tile {
	GrassTile() {
		super(Sprite.GRASS)
	}
}
