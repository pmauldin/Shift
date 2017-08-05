package pmauldin.shift.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import groovy.transform.CompileStatic

@CompileStatic
class AssetManager {
    SpriteBatch batch

    AssetManager() {
        def tileset = new Texture(Gdx.files.internal("tiny-32-tileset.png"))
        TileFactory.init(tileset)
        batch = new SpriteBatch()
    }

    void dispose() {
        TileFactory.tileset?.dispose()
        batch.dispose()
    }
}
