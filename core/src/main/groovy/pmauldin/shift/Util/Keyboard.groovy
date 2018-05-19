package pmauldin.shift.Util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import groovy.transform.CompileStatic

@CompileStatic
class Keyboard {
	static boolean anyKeyPressed(int ... keys) {
		return keys.any { int key ->
			Gdx.input.isKeyPressed(key)
		}
	}

	static boolean anyKeyJustPressed(int ... keys) {
		return keys.any { int key ->
			Gdx.input.isKeyJustPressed(key)
		}
	}

	static boolean isRightPressed() {
		return anyKeyPressed(Keys.RIGHT, Keys.D)
	}

	static boolean isLeftPressed() {
		return anyKeyPressed(Keys.LEFT, Keys.A)
	}

	static boolean isUpPressed() {
		return anyKeyPressed(Keys.UP, Keys.W)
	}

	static boolean isDownPressed() {
		return anyKeyPressed(Keys.DOWN, Keys.S)
	}
}
