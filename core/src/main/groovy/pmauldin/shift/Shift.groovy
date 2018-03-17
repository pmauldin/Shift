package pmauldin.shift

import com.badlogic.gdx.Game
import groovy.transform.CompileStatic

@CompileStatic
class Shift extends Game {

	@Override
	void create() {
		this.setScreen(new GameScreen())
	}

	@Override
	void render() {
		super.render()
	}

	@Override
	void dispose() {
		this.getScreen().dispose()
	}
}
