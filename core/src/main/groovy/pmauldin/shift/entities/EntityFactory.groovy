package pmauldin.shift.entities

import com.artemis.ComponentMapper
import com.artemis.World
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.World as Box2DWorld
import groovy.transform.CompileStatic
import pmauldin.shift.Constants
import pmauldin.shift.Util.EntityTextureUtil
import pmauldin.shift.assets.Tile
import pmauldin.shift.assets.Tiles
import pmauldin.shift.entities.components.Player
import pmauldin.shift.entities.components.Resource
import pmauldin.shift.entities.components.core.Renderable
import pmauldin.shift.entities.components.core.Rigidbody
import pmauldin.shift.entities.components.core.Transform
import pmauldin.shift.entities.components.inventory.Inventory

@CompileStatic
class EntityFactory {
	World world
	Box2DWorld box2DWorld

	ComponentMapper<Player> mPlayer
	ComponentMapper<Transform> mPosition
	ComponentMapper<Renderable> mRender
	ComponentMapper<Rigidbody> mRigidbody
	ComponentMapper<Resource> mResource
	ComponentMapper<Inventory> mInventory

	void init(World world, Box2DWorld b2dWorld) {
		this.world = world
		this.box2DWorld = b2dWorld
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

		addDrawableComponents(entity, 5, 0, 0, Entity.PLAYER_ENTITIES)

		def inventory = mInventory.create(entity)
		inventory.itemsMap = new HashMap<>()

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
		final int yRock = 7

		for (int x = 0; x <= xTiles; x++) {
			for (int y = 0; y <= yTiles; y++) {
				def tiles = []
				if (y == yWater) {
					tiles += Tiles.WATER
				} else if ((x > minXTree && x < maxXTree && y == yTree)
						|| ((x == minXTree || x == maxXTree) && y <= yTree && y > yWater)) {
					tiles += Tiles.GRASS
					tiles += Tiles.TREE
				} else if (x > minXTree && x < maxXTree && y == yRock) {
					tiles += Tiles.GRASS
					tiles += Tiles.ROCK
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

					if (tile.resource) {
						def resource = mResource.create(tileId)
						tile.buildResource(resource)
					}

					addDrawableComponents(tileId, 0, x, y, [tile.entity])
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

		def body = box2DWorld.createBody(bodyDef)
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

	void addDrawableComponents(int entityId, int layer, float x, float y, List<Entity> entities) {
		def pos = mPosition.create(entityId)
		pos.x = x
		pos.y = y

		def renderComponent = mRender.create(entityId)
		renderComponent.sprites = new ArrayList<>()
		renderComponent.layer = layer
		renderComponent.activeSprite = 0

		entities.each { entity ->
			def sprite = new Sprite(EntityTextureUtil.getEntityTexture(entity))
			sprite.setPosition(x, y)
			sprite.setSize(sprite.getWidth() / 32 as float, sprite.getHeight() / 32 as float)
			sprite.setOrigin(0.5f, 0.5f)

			renderComponent.sprites.add(sprite)
		}
	}
}
