package pmauldin.shift.entities.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import groovy.transform.CompileStatic
import pmauldin.shift.entities.AssetManager
import pmauldin.shift.entities.components.Position
import pmauldin.shift.entities.components.Texture


@CompileStatic
class RenderSystem extends IteratingSystem {
    ComponentMapper<Position> mPosition
    ComponentMapper<Texture> mTexture

    SpriteBatch batch
    OrthographicCamera camera

    RenderSystem() {
        super(Aspect.all(Position, Texture))

        camera = new OrthographicCamera()
        resizeCamera(800, 480)
    }

    @Override
    void initialize() {
        batch = AssetManager.batch
    }

    @Override
    void begin() {
        camera.update()
        batch.setProjectionMatrix(camera.combined)
        batch.begin()
    }

    @Override
    void end() {
        batch.end()
    }

    @Override
    protected void process(int entityId) {
        def pos = mPosition.get(entityId)
        def texture = mTexture.get(entityId).texture

        batch.draw(texture, pos.x, pos.y)
    }

    void resizeCamera(int width, int height) {
        camera.setToOrtho(false, width, height)
    }
}
