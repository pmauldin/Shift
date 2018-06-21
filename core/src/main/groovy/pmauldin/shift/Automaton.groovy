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
import groovy.transform.CompileStatic
import pmauldin.shift.Util.Keyboard
import pmauldin.shift.automaton.CellMap

@CompileStatic
class Automaton implements Screen {
	private OrthographicCamera camera
	private FPSLogger fpsLogger
	private boolean showFPS = false

	private CellMap cellMap
	static final int cellSize = 16

	Automaton() {
		/* LibGDX */
		def scaledWidth = Constants.WIDTH / Constants.PPM as float
		def scaledHeight = Constants.HEIGHT / Constants.PPM as float
		camera = new OrthographicCamera(scaledWidth, scaledHeight)
		camera.position.set(scaledWidth / 2 as float, scaledHeight / 2 as float, 0)
		fpsLogger = new FPSLogger()

		cellMap = new CellMap(Constants.WIDTH / cellSize as int, Constants.HEIGHT / cellSize as int)
	}

	@Override
	void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		camera.update()

		cellMap.render()

		if (Keyboard.anyKeyJustPressed(Input.Keys.F2)) {
			showFPS = !showFPS
		}

		if (showFPS) {
			fpsLogger.log()
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
	}
}
