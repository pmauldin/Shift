package pmauldin.shift.assets.tiles

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile
import pmauldin.shift.entities.Sprite

@CompileStatic
class TreeTile extends Tile {
	TreeTile() {
		super(Sprite.TREE)
	}

	@Override
	boolean isSolid() {
		true
	}
}
