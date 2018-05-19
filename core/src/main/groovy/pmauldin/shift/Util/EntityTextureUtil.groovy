package pmauldin.shift.Util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import groovy.transform.CompileStatic
import pmauldin.shift.GameScreen
import pmauldin.shift.entities.Sprite

@CompileStatic
class EntityTextureUtil {
	static Map<Sprite, TextureRegion> entityTextureRegionMap = new HashMap<>()

	static TextureRegion getEntityTexture(Sprite entity) {
		def texture = entityTextureRegionMap.get(entity)
		if (!texture) {
			def tileset = GameScreen.assetManager.get("tiny-32-tileset.png") as Texture
			texture = new TextureRegion(tileset,
					entity.xTextureOffset, entity.yTextureOffset,
					entity.textureWidth, entity.textureHeight)
			entityTextureRegionMap.put(entity, texture)
		}
		return texture
	}
}
