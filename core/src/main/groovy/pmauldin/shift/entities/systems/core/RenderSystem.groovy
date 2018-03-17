package pmauldin.shift.entities.systems.core

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Array
import groovy.transform.CompileStatic
import pmauldin.shift.entities.components.core.Renderable
import pmauldin.shift.entities.components.core.Transform

@CompileStatic
class RenderSystem extends BaseEntitySystem {
	ComponentMapper<Transform> mTransform
	ComponentMapper<Renderable> mRender

	@Wire
	SpriteBatch batch
	Map<Integer, Array<Integer>> entityLayerMap

	RenderSystem() {
		super(Aspect.all(Transform, Renderable))

		entityLayerMap = new HashMap<>()
	}

	@Override
	void inserted(int entityId) {
		def layerId = mRender.get(entityId).layer
		def layer = entityLayerMap.getOrDefault(layerId, new Array<Integer>())
		layer.add(entityId)
		entityLayerMap.put(layerId, layer)
	}

	@Override
	void removed(int entityId) {
		def layerId = mRender.get(entityId).layer
		def layer = entityLayerMap.get(layerId)
		layer.removeValue(entityId, false)
	}

	@Override
	void begin() {
		batch.begin()
	}

	@Override
	void processSystem() {
		entityLayerMap.keySet().sort().forEach { int layerId ->
			entityLayerMap.get(layerId).forEach { int entityId ->
				def pos = mTransform.get(entityId)
				def renderable = mRender.get(entityId)
				def sprite = renderable.sprites.get(renderable.activeSprite)

				sprite.setPosition(pos.x - sprite.getOriginX() as float, pos.y - sprite.getOriginY() as float)
				sprite.draw(batch)
			}
		}
	}

	@Override
	void end() {
		batch.end()
	}
}
