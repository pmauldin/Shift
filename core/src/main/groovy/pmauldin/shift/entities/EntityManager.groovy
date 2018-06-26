package pmauldin.shift.entities

import com.artemis.World
import com.artemis.WorldConfiguration
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World as Box2DWorld
import groovy.transform.CompileStatic
import pmauldin.shift.Constants
import pmauldin.shift.entities.components.Components
import pmauldin.shift.entities.systems.MovementSystem
import pmauldin.shift.entities.systems.PlayerInputSystem

import pmauldin.shift.entities.systems.TileSystem
import pmauldin.shift.entities.systems.core.InputSystem
import pmauldin.shift.entities.systems.core.PhysicsSystem
import pmauldin.shift.entities.systems.core.RenderSystem
import pmauldin.shift.entities.systems.inventory.InventorySystem

@CompileStatic
class EntityManager {
	static World entityWorld
	static int playerId

	static void init(OrthographicCamera camera, SpriteBatch batch, Box2DWorld box2DWorld) {
		def worldConfig = configureWorld(camera)
		def entityFactory = new EntityFactory()

		worldConfig.register(entityFactory)
		worldConfig.register(batch)
		worldConfig.register(box2DWorld)

		entityWorld = new World(worldConfig)
		entityWorld.inject(entityFactory)
		entityWorld.inject(new Components())

		entityFactory.init(entityWorld, box2DWorld)

		playerId = entityFactory.createPlayer()
		entityFactory.createLevel()
	}

	static void update(float delta) {
		entityWorld.setDelta(delta)
		entityWorld.process()
	}

	static int create() {
		return entityWorld.create()
	}

	static void delete(int entityId) {
		entityWorld.delete(entityId)
	}

	private static WorldConfiguration configureWorld(OrthographicCamera camera) {
		// Systems are processed in the order defined here.
		new WorldConfigurationBuilder().with(
				new InputSystem(),
				new TileSystem(),
				new PlayerInputSystem(camera),
				new MovementSystem(),
				new PhysicsSystem(),
				new RenderSystem(),
				new InventorySystem())
				.register(new GameLoopInvoker(Constants.MILLIS_PER_LOGIC_TICK))
				.build()
	}
}
