package pmauldin.shift.entities

import com.artemis.ComponentMapper
import com.artemis.World
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World as B2DWorld
import groovy.transform.CompileStatic
import pmauldin.shift.Constants
import pmauldin.shift.GameScreen
import pmauldin.shift.assets.Tile
import pmauldin.shift.assets.TileFactory
import pmauldin.shift.entities.components.Player
import pmauldin.shift.entities.components.Rigidbody
import pmauldin.shift.entities.components.Transform
import pmauldin.shift.entities.components.Renderable

@CompileStatic
class EntityFactory {
    World world
    B2DWorld b2dWorld

    ComponentMapper<Player> mPlayer
    ComponentMapper<Transform> mPosition
    ComponentMapper<Renderable> mRender
    ComponentMapper<Rigidbody> mRigidbody

    void init(World world, B2DWorld b2dWorld) {
        this.world = world
        this.b2dWorld = b2dWorld
    }

    int createPlayer() {
        def entity = world.create()
        mPlayer.create(entity)

        def body = createBox(10.0f, 10.0f, 0.8f, 0.3f, BodyType.DynamicBody)
        body.setUserData(entity)

        def rigidbody = mRigidbody.create(entity)
        rigidbody.body = body
        rigidbody.yOffset = 0.35f

        addDrawableComponents(entity, 5, 0, 0, Tile.PLAYER)

        return entity
    }

    void createLevel() {
        def tileSize = 32
        int xTiles = Constants.WIDTH / tileSize as int
        int yTiles = Constants.HEIGHT / tileSize as int

        createTiles(xTiles, yTiles)
    }

    void createTiles(int xTiles, int yTiles) {
        for (int x = 0; x <= xTiles; x++) {
            for (int y = 0; y <= yTiles; y++) {
                def tileId = world.create()

                def tile
                if (y == 4) {
                    tile = Tile.WATER
                } else {
                    tile = Tile.GRASS
                }

                if (tile.solid) {
					def body = createBox(x, y, 1f, 1f)
					body.setUserData(tileId)

					def rigidbody = mRigidbody.create(tileId)
					rigidbody.body = body
				}

                addDrawableComponents(tileId, 0, x, y, tile)
            }
        }
    }

    Body createBox(float x, float y, float width, float height, BodyType type=BodyType.KinematicBody) {
        def bodyDef = new BodyDef()
        bodyDef.type = type
        bodyDef.position.set(x, y)

        def shape = new PolygonShape()
        shape.setAsBox(width / 2 as float, height / 2 as float)

        def fixtureDef = new FixtureDef()
        fixtureDef.shape = shape

        def body = b2dWorld.createBody(bodyDef)
        body.createFixture(fixtureDef)
        shape.dispose()

        return body
    }

    void addDrawableComponents(int entityId, int layer, float x, float y, Tile tile) {
        def pos = mPosition.create(entityId)
        pos.x = x
        pos.y = y

        def renderComponent = mRender.create(entityId)
        def sprite = new Sprite(TileFactory.getTileTexture(tile))
        sprite.setPosition(x, y)
        sprite.setSize(sprite.getWidth() / 32 as float, sprite.getHeight() / 32 as float)
        sprite.setOrigin(0.5f, 0.5f)

        renderComponent.sprite = sprite
        renderComponent.layer = layer
    }
}
