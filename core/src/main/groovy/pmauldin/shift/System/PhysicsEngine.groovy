package pmauldin.shift.System

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2D
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array
import groovy.transform.CompileStatic

@CompileStatic
class PhysicsEngine {
    private final float TIME_STEP = 1 / 60f as float
    private final int VELOCITY_ITERATIONS = 6
    private final int POSITION_ITERATIONS = 2

    private final float GRAVITY = 50f

    private float updateAccumulator = 0

    Array<Body> bodies

    World world
    Box2DDebugRenderer debugRenderer

    PhysicsEngine() {
        Box2D.init()
        world = new World(new Vector2(0, -GRAVITY), true)
        debugRenderer = new Box2DDebugRenderer()

        bodies = new Array<>()
        initFloor()
    }
    
    def initFloor() {
//        // Create our body definition
//        BodyDef groundBodyDef = new BodyDef()
//        // Set its world position
//        groundBodyDef.position.set(new Vector2(Framework.renderer.camera.viewportWidth / 2f as float, 16))
//
//        // Create a body from the definition and add it to the world
//        def groundBody = world.createBody(groundBodyDef)
//
//        // Create a polygon shape
//        def groundBox = new PolygonShape()
//        // Set the polygon shape as a box which is twice the size of our view port and 20 high
//        // (setAsBox takes half-width and half-height as arguments)
//        groundBox.setAsBox(Framework.renderer.camera.viewportWidth / 2f as float, 16.0f)
//        // Create a fixture from our polygon shape and add it to our ground body
//        groundBody.createFixture(groundBox, 0.0f)
//        // Clean up after ourselves
//        groundBox.dispose()
    }

    void update() {
//        debugRenderer.render(world, Framework.renderer.camera.combined)
//
//        // fixed time step
//        // max frame time to avoid spiral of death (on slow devices)
//        def frameTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f)
//        updateAccumulator += frameTime
//        while (updateAccumulator >= TIME_STEP) {
//            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
//            updateAccumulator -= TIME_STEP
//        }
//
//        bodies.each { body ->
//            def entity = body.userData as Entity
//            entity.updatePosition(body.position)
//        }
    }
}
