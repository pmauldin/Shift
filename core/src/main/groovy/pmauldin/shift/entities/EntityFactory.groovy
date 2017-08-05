package pmauldin.shift.entities

import com.artemis.ComponentMapper
import com.artemis.World
import com.artemis.managers.TagManager
import com.badlogic.gdx.math.Rectangle
import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile
import pmauldin.shift.assets.TileFactory
import pmauldin.shift.entities.components.LevelComponent
import pmauldin.shift.entities.components.PositionComponent
import pmauldin.shift.entities.components.TextureComponent as TextureComponent
import pmauldin.shift.entities.components.VelocityComponent

@CompileStatic
class EntityFactory {
    World world
    TagManager tagManager

    ComponentMapper<LevelComponent> mLevel
    ComponentMapper<PositionComponent> mPosition
    ComponentMapper<TextureComponent> mTexture
    ComponentMapper<VelocityComponent> mVelocity

    void init(World world) {
        this.world = world
    }

    int createPlayer() {
        def entity = world.create()

        addDrawableComponents(entity, 5, 400, 100, Tile.PLAYER)

        mVelocity.create(entity)

        tagEntity(entity, Tags.PLAYER)
        return entity
    }

    int createLevel() {
        def entity = world.create()
        def level = mLevel.create(entity)
        level.bounds = new Rectangle(0, 0, 800, 480)

        def tileSize = 32
        int xTiles = level.bounds.width / tileSize as int
        int yTiles = level.bounds.height / tileSize as int
        level.tileIds = new int[xTiles][yTiles]

        createTiles(level, tileSize, xTiles, yTiles)

        tagEntity(entity, Tags.LEVEL)
        return entity
    }

    void createTiles(LevelComponent level, int tileSize, int xTiles, int yTiles) {
        for (int x = 0; x < xTiles; x++) {
            for (int y = 0; y < yTiles; y++) {
                def tileId = world.create()
                addDrawableComponents(tileId, 0, x * tileSize, y * tileSize, Tile.GRASS)
                level.tileIds[x][0]
            }
        }
    }

    void addDrawableComponents(int entityId, int layer, float x, float y, Tile tile) {
        def pos = mPosition.create(entityId)
        pos.x = x
        pos.y = y
        pos.width = Tile.TILE_SIZE
        pos.height = Tile.TILE_SIZE

        def texture = mTexture.create(entityId)
        texture.texture = TileFactory.getTileTexture(tile)
        texture.layer = layer
    }

    void tagEntity(int entity, String tag) {
        tagManager.register(tag, entity)
    }
}
