package pmauldin.shift

import com.badlogic.gdx.Gdx
import groovy.transform.CompileStatic

@CompileStatic
class Util {

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
}
