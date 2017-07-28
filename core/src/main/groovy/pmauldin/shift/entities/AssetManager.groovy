package pmauldin.shift.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import groovy.transform.CompileStatic

@CompileStatic
class AssetManager {
    static Texture tileset
    static SpriteBatch batch

    static void init() {
        tileset = new Texture(Gdx.files.internal("tileset.png"))
        batch = new SpriteBatch()
    }

    static void dispose() {
        tileset?.dispose()
        batch?.dispose()
    }
}
