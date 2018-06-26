package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile
import pmauldin.shift.entities.Sprite

@CompileStatic
class RockTile extends Tile {
	RockTile() {
		super(Sprite.ROCK)
	}

	@Override
	boolean isSolid() {
		true
	}
}
