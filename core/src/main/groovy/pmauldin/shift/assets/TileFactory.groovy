package pmauldin.shift.assets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import groovy.transform.CompileStatic
import pmauldin.shift.GameScreen

@CompileStatic
class TileFactory {
	static Map<Tile, TextureRegion> tileTextureRegionMap = new HashMap<>()

	static TextureRegion getTileTexture(Tile tile) {
		def texture = tileTextureRegionMap.get(tile)
		if (!texture) {
			def tileset = GameScreen.assetManager.get("tiny-32-tileset.png") as Texture
			texture = new TextureRegion(tileset, tile.xOffset, tile.yOffset, Tile.TILE_SIZE, Tile.TILE_SIZE)
			tileTextureRegionMap.put(tile, texture)
		}
		return texture
	}
}
