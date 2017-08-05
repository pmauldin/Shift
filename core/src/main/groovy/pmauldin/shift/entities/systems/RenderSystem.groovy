package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Array
import groovy.transform.CompileStatic

import pmauldin.shift.entities.components.TransformComponent
import pmauldin.shift.entities.components.RenderComponent

@CompileStatic
class RenderSystem extends BaseEntitySystem {
    ComponentMapper<TransformComponent> mTransform
    ComponentMapper<RenderComponent> mRender

    @Wire
    SpriteBatch batch

    OrthographicCamera camera

    Map<Integer, Array<Integer>> entityLayerMap

    RenderSystem() {
        super(Aspect.all(TransformComponent, RenderComponent))

        camera = new OrthographicCamera()
        resizeCamera(800, 480)

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
        layer.removeValue(entityId, true)
    }

    @Override
    void begin() {
        camera.update()
        batch.setProjectionMatrix(camera.combined)
        batch.begin()
    }

    @Override
    void processSystem() {
        entityLayerMap.keySet().sort().forEach { int layerId ->
            entityLayerMap.get(layerId).forEach { int entityId ->
                def pos = mTransform.get(entityId)
                def texture = mRender.get(entityId).texture

                batch.draw(texture, pos.x, pos.y)
            }
        }
    }

    @Override
    void end() {
        batch.end()
    }

    void resizeCamera(int width, int height) {
        camera.setToOrtho(false, width, height)
    }
}
