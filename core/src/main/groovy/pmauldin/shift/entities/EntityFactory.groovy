package pmauldin.shift.entities

import com.artemis.ComponentMapper
import com.artemis.World
import com.artemis.managers.TagManager
import groovy.transform.CompileStatic

import pmauldin.shift.assets.Tile
import pmauldin.shift.assets.TileFactory

import pmauldin.shift.entities.components.TransformComponent
import pmauldin.shift.entities.components.RenderComponent as TextureComponent
import pmauldin.shift.entities.components.VelocityComponent

@CompileStatic
class EntityFactory {
    World world
    TagManager tagManager

    ComponentMapper<TransformComponent> mPosition
    ComponentMapper<TextureComponent> mRender
    ComponentMapper<VelocityComponent> mVelocity

    void init(World world) {
        this.world = world
    }

    int createPlayer() {
        def entity = world.create()

        addDrawableComponents(entity, 5, 400, 200, Tile.PLAYER)

        mVelocity.create(entity)

        tagEntity(entity, Tags.PLAYER)
        return entity
    }

    int createLevel() {
        def entity = world.create()

        def tileSize = 32
        int xTiles = 800 / tileSize as int
        int yTiles = 480 / tileSize as int

        createTiles(tileSize, xTiles, yTiles)

        tagEntity(entity, Tags.LEVEL)
        return entity
    }

    void createTiles(int tileSize, int xTiles, int yTiles) {
        for (int x = 0; x < xTiles; x++) {
            for (int y = 0; y < yTiles; y++) {
                def tileId = world.create()

                def tile
                if (y == 4) {
                    tile = Tile.WATER
                }  else {
                    tile = Tile.GRASS
                }
                addDrawableComponents(tileId, 0, x * tileSize, y * tileSize, tile)
            }
        }
    }

    void addDrawableComponents(int entityId, int layer, float x, float y, Tile tile) {
        def pos = mPosition.create(entityId)
        pos.x = x
        pos.y = y

        def renderComponent = mRender.create(entityId)
        renderComponent.texture = TileFactory.getTileTexture(tile)
        renderComponent.layer = layer
    }

    void tagEntity(int entity, String tag) {
        tagManager.register(tag, entity)
    }
}
