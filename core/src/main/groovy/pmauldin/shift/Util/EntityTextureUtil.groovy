package pmauldin.shift.Util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import groovy.transform.CompileStatic
import pmauldin.shift.GameScreen
import pmauldin.shift.entities.Entity

@CompileStatic
class EntityTextureUtil {
	static Map<Entity, TextureRegion> entityTextureRegionMap = new HashMap<>()

	static TextureRegion getEntityTexture(Entity entity) {
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
