package pmauldin.shift

import groovy.transform.CompileStatic

@CompileStatic
class Player {

    static final int SIZE = 32

//    static Entity buildEntity(Texture tileset) {
//        def entity = new Entity()
//
//        def positionComponent = new Position()
//        positionComponent.x = 400f
//        positionComponent.y = 100f
//        entity.add(positionComponent)
//
//        def textureComponent = new Texture()
//        textureComponent.texture = new TextureRegion(tileset, SIZE, 0, SIZE, SIZE)
//        entity.add(textureComponent)
//
//        return entity
//    }

//    void createBody() {
//        def halfSize = SIZE / 2f as float
//
//        def bodyDef = new BodyDef()
//        bodyDef.type = BodyDef.BodyType.DynamicBody
//        bodyDef.position.set(player.x, player.y)
//
//        def body = Framework.physicsEngine.world.createBody(bodyDef)
//        body.userData = this
//
//        def shape = new PolygonShape()
//        shape.setAsBox(halfSize, halfSize)
//
//        body.createFixture(shape, 0)
//
//        shape.dispose()
//
//        Framework.physicsEngine.bodies.add(body)
//    }

    void update() {
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
//            player.x -= SPEED * Gdx.graphics.getDeltaTime()
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
//            player.x += SPEED * Gdx.graphics.getDeltaTime()
//
//        def bounds = Framework.level.bounds
//
//        if (player.x > bounds.width - SIZE) player.x = bounds.width - SIZE
//        if (player.x < bounds.y) player.x = bounds.y
//
//        if (player.y > bounds.height) player.y = bounds.height
//        if (player.y < bounds.y + SIZE) player.y = bounds.y + SIZE
    }

//    @Override
//    void updatePosition(float x, float y) {
//        x -= SIZE / 2f as float
//        y -= SIZE / 2f as float
//        player.setPosition(x, y)
//    }
//
//    @Override
//    void updatePosition(Vector2 pos) {
//        updatePosition(pos.x, pos.y)
//    }
//
//    @Override
//    void draw(Batch batch) {
//        batch.draw(playerTexture, player.x, player.y)
//    }
}
