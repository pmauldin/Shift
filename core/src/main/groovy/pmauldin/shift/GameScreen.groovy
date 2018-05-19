package pmauldin.shift

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
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
import com.badlogic.gdx.physics.box2d.World as Box2DWorld
import groovy.transform.CompileStatic
import pmauldin.shift.Util.Keyboard
import pmauldin.shift.entities.EntityManager

@CompileStatic
class GameScreen implements Screen {
	static AssetManager assetManager

	Box2DWorld box2DWorld
	SpriteBatch batch

	private OrthographicCamera camera
	private FPSLogger fpsLogger
	private Box2DDebugRenderer b2dRenderer
	private boolean showB2DDebugRenderer

	GameScreen() {
		/* LibGDX */
		def scaledWidth = Constants.WIDTH / Constants.PPM as float
		def scaledHeight = Constants.HEIGHT / Constants.PPM as float
		camera = new OrthographicCamera(scaledWidth, scaledHeight)
		camera.position.set(scaledWidth / 2 as float, scaledHeight / 2 as float, 0)
		batch = new SpriteBatch()
		fpsLogger = new FPSLogger()

		/* Box2D */
		Box2D.init()
		box2DWorld = new Box2DWorld(new Vector2(), true)
		b2dRenderer = new Box2DDebugRenderer()
		showB2DDebugRenderer = true

		/* Assets */
		assetManager = new AssetManager()
		assetManager.load("tiny-32-tileset.png", Texture)
		assetManager.finishLoading()

		/* artemis-odb */
		EntityManager.init(camera, batch, box2DWorld)
	}

	@Override
	void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		camera.update()
		batch.setProjectionMatrix(camera.combined)

		EntityManager.update(delta)

		if (Keyboard.anyKeyJustPressed(Input.Keys.F2)) {
			showB2DDebugRenderer = !showB2DDebugRenderer
		}

		if (showB2DDebugRenderer) {
//			fpsLogger.log()
			b2dRenderer.render(box2DWorld, camera.combined)
		}
	}

	@Override
	void resize(int width, int height) {
		camera.setToOrtho(false, width / Constants.PPM as float, height / Constants.PPM as float)
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
