package pmauldin.shift

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2D
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World as B2DWorld
import groovy.transform.CompileStatic

import pmauldin.shift.entities.EntityFactory
import pmauldin.shift.entities.GameLoopInvoker

import pmauldin.shift.entities.systems.PhysicsSystem
import pmauldin.shift.entities.systems.PlayerSystem
import pmauldin.shift.entities.systems.RenderSystem

@CompileStatic
class GameScreen implements Screen {
    static AssetManager assetManager

    World entityWorld
    B2DWorld b2dWorld
    SpriteBatch batch

    static final int MILLIS_PER_LOGIC_TICK = 20 // ~50 ticks/second
    static final float WIDTH = 800
    static final float HEIGHT = 480
    static final int PPM = 32 // Pixels per meter.

    private OrthographicCamera camera
    private FPSLogger fpsLogger
    private Box2DDebugRenderer b2dRenderer
    private boolean showB2DDebugRenderer

    GameScreen() {
        /* LibGDX */
        def scaledWidth = WIDTH / PPM as float
        def scaledHeight = HEIGHT / PPM as float
        camera = new OrthographicCamera(scaledWidth, scaledHeight)
        camera.position.set(scaledWidth / 2 as float, scaledHeight / 2 as float, 0)
        batch = new SpriteBatch()
        fpsLogger = new FPSLogger()

        /* Box2D */
        Box2D.init()
        b2dWorld = new B2DWorld(new Vector2(), true)
        b2dRenderer = new Box2DDebugRenderer()
        showB2DDebugRenderer = true

        /* Artemis-odb */
        // Systems are processed in the order defined here.
        def worldConfig = new WorldConfigurationBuilder().with(
                new PlayerSystem(),
                new PhysicsSystem(),
                new RenderSystem())
                .register(new GameLoopInvoker(MILLIS_PER_LOGIC_TICK))
                .build()

        assetManager = new AssetManager()
        assetManager.load("tiny-32-tileset.png", Texture)
        assetManager.finishLoading()

        def entityFactory = new EntityFactory()

        worldConfig.register(entityFactory)
        worldConfig.register(batch)
        worldConfig.register(b2dWorld)

        entityWorld = new World(worldConfig)
        entityWorld.inject(entityFactory)

        entityFactory.init(entityWorld, b2dWorld)
        entityFactory.createPlayer()
        entityFactory.createLevel()
    }

    @Override
    void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.setProjectionMatrix(camera.combined)

        entityWorld.setDelta(delta)
        entityWorld.process()

        if (showB2DDebugRenderer) {
            fpsLogger.log()
            b2dRenderer.render(b2dWorld, camera.combined)
        }
    }

    @Override
    void resize(int width, int height) {
        camera.setToOrtho(false, width / PPM as float, height / PPM as float)
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
