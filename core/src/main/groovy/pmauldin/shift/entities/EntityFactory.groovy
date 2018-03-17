package pmauldin.shift.entities

import com.artemis.ComponentMapper
import com.artemis.World
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.World as B2DWorld
import groovy.transform.CompileStatic
import pmauldin.shift.Constants
import pmauldin.shift.assets.Tile
import pmauldin.shift.assets.TileFactory
import pmauldin.shift.assets.Tiles
import pmauldin.shift.entities.components.Player
import pmauldin.shift.entities.components.Renderable
import pmauldin.shift.entities.components.Rigidbody
import pmauldin.shift.entities.components.Transform

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
		def player = mPlayer.create(entity)
		player.xDirection = 0
		player.yDirection = -1

		def body = createBody(15.0f, 10.0f, createCircleShape(0.18f), BodyType.DynamicBody)
		body.setUserData(entity)

		def rigidbody = mRigidbody.create(entity)
		rigidbody.body = body
		rigidbody.yOffset = 0.35f

		addDrawableComponents(entity, 5, 0, 0, Tiles.PLAYER)

		return entity
	}

	void createLevel() {
		def tileSize = 32
		int xTiles = Constants.WIDTH / tileSize as int
		int yTiles = Constants.HEIGHT / tileSize as int

		createTiles(xTiles, yTiles)
	}

	void createTiles(int xTiles, int yTiles) {
		final int minXTree = 10
		final int maxXTree = 30
		final int yTree = 16
		final int yWater = 6

		for (int x = 0; x <= xTiles; x++) {
			for (int y = 0; y <= yTiles; y++) {
				def tiles = []
				if (y == yWater) {
					tiles += Tiles.WATER
				} else if ((x > minXTree && x < maxXTree && y == yTree)
						|| ((x == minXTree || x == maxXTree) && y <= yTree && y > yWater)) {
					tiles += Tiles.GRASS
					tiles += Tiles.TREE
				} else {
					tiles += Tiles.GRASS
				}

				tiles.forEach { Tile tile ->
					def tileId = world.create()

					if (tile.solid) {
						def body = createBody(x, y, createBoxShape(1f, 1f))
						body.setUserData(tileId)

						def rigidbody = mRigidbody.create(tileId)
						rigidbody.body = body
					}

					addDrawableComponents(tileId, 0, x, y, tile)
				}
			}
		}
	}

	Body createBody(float x, float y, Shape shape, BodyType type = BodyType.KinematicBody) {
		def bodyDef = new BodyDef()
		bodyDef.type = type
		bodyDef.position.set(x, y)

		def fixtureDef = new FixtureDef()
		fixtureDef.shape = shape

		def body = b2dWorld.createBody(bodyDef)
		body.createFixture(fixtureDef)
		shape.dispose()

		return body
	}

	private static PolygonShape createBoxShape(float width, float height) {
		def shape = new PolygonShape()
		shape.setAsBox(width / 2 as float, height / 2 as float)
		return shape
	}

	private static CircleShape createCircleShape(float radius) {
		def shape = new CircleShape()
		shape.setPosition(Vector2.Zero)
		shape.setRadius(radius)
		return shape
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
