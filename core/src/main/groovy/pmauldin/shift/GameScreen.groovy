package pmauldin.shift

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.managers.TagManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import groovy.transform.CompileStatic

import pmauldin.shift.entities.EntityFactory
import pmauldin.shift.entities.GameLoopInvoker
import pmauldin.shift.entities.systems.MovementSystem
import pmauldin.shift.entities.systems.PlayerInputSystem
import pmauldin.shift.entities.systems.RenderSystem

@CompileStatic
class GameScreen implements Screen {
    static World entityWorld

    SpriteBatch batch
    static AssetManager assetManager

    GameScreen() {
        // Systems are processed in the order defined here.
        def worldConfig = new WorldConfigurationBuilder().with(
                new TagManager(),
                new PlayerInputSystem(),
                new MovementSystem(),
                new RenderSystem())
                .register(new GameLoopInvoker(20)) // ~50 ticks/second
                .build()

        assetManager = new AssetManager()
        assetManager.load("tiny-32-tileset.png", Texture)
        assetManager.finishLoading()

        def entityFactory = new EntityFactory()

        batch = new SpriteBatch()

        worldConfig.register(entityFactory)
        worldConfig.register(batch)

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
        batch.dispose()
        assetManager.dispose()
    }
}
