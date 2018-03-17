package pmauldin.shift.assets

import groovy.transform.CompileStatic

@CompileStatic
enum Tile {
	GRASS(14, 1, false),
	WATER(13, 6, true),
	TREE(8, 1, true),
	PLAYER(0, 8, false),
	RED(1.5f, 0, false, 16, 16)

	static final int TILE_SIZE = 32
	int xOffset, yOffset, width, height
	boolean solid

	Tile(float xOffset, float yOffset, boolean solid, int width = TILE_SIZE, int height = TILE_SIZE) {
		this.xOffset = xOffset * TILE_SIZE as int
		this.yOffset = yOffset * TILE_SIZE as int
		this.solid = solid
		this.width = width
		this.height = height
	}
}