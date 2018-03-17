package pmauldin.shift.entities

enum Entity {
	GRASS(14, 1),
	WATER(13, 6),
	TREE(8, 1),
	PLAYER(0, 8)

	static final int TILE_SIZE = 32
	int xTextureOffset, yTextureOffset, textureWidth, textureHeight

	Entity(int xTextureOffset, int yTextureOffset,
		   int textureWidth = TILE_SIZE, int textureHeight = TILE_SIZE) {
		this.xTextureOffset = xTextureOffset * TILE_SIZE
		this.yTextureOffset = yTextureOffset * TILE_SIZE
		this.textureWidth = textureWidth
		this.textureHeight = textureHeight
	}
}
