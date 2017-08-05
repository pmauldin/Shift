package pmauldin.shift.assets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import groovy.transform.CompileStatic

@CompileStatic
class TileFactory {
    static Texture tileset = null

    static Map<Tile, TextureRegion> tileTextureRegionMap = new HashMap<>()

    static void init(Texture tileset) {
        this.tileset = tileset
    }

    static TextureRegion getTileTexture(Tile tile) {
        def texture = tileTextureRegionMap.get(tile)
        if (!texture) {
            texture = new TextureRegion(tileset, tile.xOffset, tile.yOffset, Tile.TILE_SIZE, Tile.TILE_SIZE)
            tileTextureRegionMap.put(tile, texture)
        }
        return texture
    }
}
