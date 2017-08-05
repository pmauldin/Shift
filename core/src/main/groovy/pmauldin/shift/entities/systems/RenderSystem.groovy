package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Array
import groovy.transform.CompileStatic
import pmauldin.shift.assets.AssetManager
import pmauldin.shift.entities.components.PositionComponent
import pmauldin.shift.entities.components.TextureComponent

@CompileStatic
class RenderSystem extends BaseEntitySystem {
    ComponentMapper<PositionComponent> mPosition
    ComponentMapper<TextureComponent> mTexture

    @Wire
    AssetManager assetManager

    OrthographicCamera camera

    Map<Integer, Array<Integer>> entityLayerMap

    RenderSystem() {
        super(Aspect.all(PositionComponent, TextureComponent))

        camera = new OrthographicCamera()
        resizeCamera(800, 480)

        entityLayerMap = new HashMap<>()
    }

    @Override
    void inserted(int entityId) {
        def layerId = mTexture.get(entityId).layer
        def layer = entityLayerMap.getOrDefault(layerId, new Array<Integer>())
        layer.add(entityId)
        entityLayerMap.put(layerId, layer)
    }

    @Override
    void removed(int entityId) {
        def layerId = mTexture.get(entityId).layer
        def layer = entityLayerMap.get(layerId)
        layer.removeValue(entityId, true)
    }

    @Override
    void begin() {
        camera.update()
        assetManager.batch.setProjectionMatrix(camera.combined)
        assetManager.batch.begin()
    }

    @Override
    void processSystem() {
        entityLayerMap.keySet().sort().forEach { int layerId ->
            entityLayerMap.get(layerId).forEach { int entityId ->
                def pos = mPosition.get(entityId)
                def texture = mTexture.get(entityId).texture

                assetManager.batch.draw(texture, pos.x, pos.y)
            }
        }
    }

    @Override
    void end() {
        assetManager.batch.end()
    }

    void resizeCamera(int width, int height) {
        camera.setToOrtho(false, width, height)
    }
}
