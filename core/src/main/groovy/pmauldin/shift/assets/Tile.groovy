package pmauldin.shift.assets

import groovy.transform.CompileStatic
import pmauldin.shift.assets.tiles.GrassTile
import pmauldin.shift.assets.tiles.PlayerEntity
import pmauldin.shift.assets.tiles.TreeTile
import pmauldin.shift.assets.tiles.WaterTile

@CompileStatic
class Tiles {
	static Tile GRASS = new GrassTile()
	static Tile WATER = new WaterTile()
	static Tile TREE = new TreeTile()
	static Tile PLAYER = new PlayerEntity()
}

@CompileStatic
abstract class Tile {
	static final int TILE_SIZE = 32

	abstract int xTileOffset()
	abstract int yTileOffset()

	int getXOffset() {
		return xTileOffset() * TILE_SIZE
	}

	int getYOffset() {
		return yTileOffset() * TILE_SIZE
	}

	int getWidth() {
		return TILE_SIZE
	}

	int getHeight() {
		return TILE_SIZE
	}

	boolean isSolid() {
		return false
	}
}
