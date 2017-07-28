package pmauldin.shift

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.managers.TagManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import groovy.transform.CompileStatic
import pmauldin.shift.entities.AssetManager
import pmauldin.shift.entities.EntityFactory

import pmauldin.shift.entities.systems.MovementSystem
import pmauldin.shift.entities.systems.PlayerInputSystem
import pmauldin.shift.entities.systems.RenderSystem

@CompileStatic
class GameScreen implements Screen {
    static World entityWorld

    GameScreen() {
        AssetManager.init()

        // Systems are processed in the order defined here.
        def worldConfig = new WorldConfigurationBuilder().with(
                new TagManager(),
                new PlayerInputSystem(),
                new MovementSystem(),
                new RenderSystem())
                .build()

        entityWorld = new World(worldConfig)

        EntityFactory.init(entityWorld)
        EntityFactory.createPlayer()
        EntityFactory.createLevel()
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
        entityWorld.getSystem(RenderSystem)?.resizeCamera(width, height)
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
        AssetManager.dispose()
    }
}
