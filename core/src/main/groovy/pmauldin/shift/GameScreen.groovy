package pmauldin.shift

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.managers.TagManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import groovy.transform.CompileStatic
import pmauldin.shift.assets.AssetManager
import pmauldin.shift.entities.EntityFactory
import pmauldin.shift.entities.GameLoopInvoker
import pmauldin.shift.entities.systems.MovementSystem
import pmauldin.shift.entities.systems.PlayerInputSystem
import pmauldin.shift.entities.systems.RenderSystem

@CompileStatic
class GameScreen implements Screen {
    static World entityWorld

    AssetManager assetManager

    GameScreen() {
        // Systems are processed in the order defined here.
        def worldConfig = new WorldConfigurationBuilder().with(
                new TagManager(),
                new PlayerInputSystem(),
                new MovementSystem(),
                new RenderSystem())
                .register(new GameLoopInvoker(20)) // ~50 ticks/second
                .build()

        def entityFactory = new EntityFactory()
        assetManager = new AssetManager()
        worldConfig.register(entityFactory)
        worldConfig.register(assetManager)

        entityWorld = new World(worldConfig)

        entityWorld.inject(entityFactory)

        entityFactory.init(entityWorld)
        entityFactory.createPlayer()
        entityFactory.createLevel()
    }

    @Override
    void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        entityWorld.setDelta(delta)
        entityWorld.process()
    }

    @Override
    void resize(int width, int height) {
        entityWorld.getSystem(RenderSystem).resizeCamera(width, height)
    }

    @Override
    void show() {
    }

    @Override
    void hide() {
    }

    @Override
    void pause() {
    }

    @Override
    void resume() {
    }

    @Override
    void dispose() {
        assetManager.dispose()
    }
}
