package pmauldin.shift.entities

enum Sprite {
	GRASS(14, 1),
	WATER(13, 6),
	TREE(8, 1),
	ROCK(9, 8),
	PLAYER_DOWN(0, 8),
	PLAYER_LEFT(0, 9),
	PLAYER_RIGHT(0, 10),
	PLAYER_UP(0, 11)

	static final List<Sprite> PLAYER_ENTITIES = [PLAYER_DOWN, PLAYER_LEFT, PLAYER_RIGHT, PLAYER_UP]

	static final int TILE_SIZE = 32
	int xTextureOffset, yTextureOffset, textureWidth, textureHeight

	Sprite(int xTextureOffset, int yTextureOffset,
		   int textureWidth = TILE_SIZE, int textureHeight = TILE_SIZE) {
		this.xTextureOffset = xTextureOffset * TILE_SIZE
		this.yTextureOffset = yTextureOffset * TILE_SIZE
		this.textureWidth = textureWidth
		this.textureHeight = textureHeight
	}
}
