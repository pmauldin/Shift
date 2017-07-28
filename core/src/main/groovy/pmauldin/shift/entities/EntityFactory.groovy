package pmauldin.shift.entities

import com.artemis.ComponentMapper
import com.artemis.World
import com.artemis.managers.TagManager
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Array
import groovy.transform.CompileStatic
import pmauldin.shift.entities.components.Level
import pmauldin.shift.entities.components.Position
import pmauldin.shift.entities.components.Texture as TextureComponent
import pmauldin.shift.entities.components.Velocity

@CompileStatic
class EntityFactory {
    static World world
    static ComponentMapper<Position> mPosition
    static ComponentMapper<Velocity> mVelocity
    static ComponentMapper<TextureComponent> mTexture
    static ComponentMapper<Level> mLevel

    static final int PLAYER_SIZE = 32

    static void init(World world) {
        this.world = world

        mPosition = world.getMapper(Position)
        mVelocity = world.getMapper(Velocity)
        mTexture = world.getMapper(TextureComponent)
        mLevel = world.getMapper(Level)
    }

    static int createPlayer() {
        def entity = world.create()

        def texture = new TextureRegion(AssetManager.tileset, PLAYER_SIZE, 0, PLAYER_SIZE, PLAYER_SIZE)
        addDrawableComponents(entity, 400, 100, texture)

        mVelocity.create(entity)

        tagEntity(entity, Tags.PLAYER)
        return entity
    }
    
    static int createLevel() {
        def entity = world.create()
        def level = mLevel.create(entity)
        level.bounds = new Rectangle(0, 0, 800, 480)
        level.tileIds = new Array<>()

        int tileSize = 32
        def blueTile = new TextureRegion(AssetManager.tileset, tileSize * 0, 0, tileSize, tileSize)
//        def redTile = new TextureRegion(AssetManager.tileset, tileSize * 1, 0, tileSize, tileSize)
//        def greenTile = new TextureRegion(AssetManager.tileset, tileSize * 2, 0, tileSize, tileSize)

        for (int x = 0; x < level.bounds.width; x += tileSize) {
            def tileId = world.create()
            addDrawableComponents(tileId, x, 0, blueTile)
            level.tileIds.add(tileId)
        }

        tagEntity(entity, Tags.LEVEL)
        return entity
    }

    static void addDrawableComponents(int entityId, float x, float y, TextureRegion textureRegion) {
        def pos = mPosition.create(entityId)
        pos.x = x
        pos.y = y

        def texture = mTexture.create(entityId)
        texture.texture = textureRegion
    }

    static void tagEntity(int entity, String tag) {
        world.getSystem(TagManager).register(tag, entity)
    }
}
