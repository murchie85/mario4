package com.mcmurchie.mariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcmurchie.mariobros.MarioBros;
import com.mcmurchie.mariobros.Scenes.Hud;
import com.mcmurchie.mariobros.Sprites.Mario;

/**
 * Created by adammcmurchie on 14/01/2017.
 */
// SCREEN IS A GDX CLASS represents many screens
    // this exectues logic and drws things to scree n

public class PlayScreen implements Screen { //GENERATE ALL GDX METHODS
    private MarioBros game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // BOX 2D VARIABLES
    private World world;
    private Box2DDebugRenderer b2dr; // GIVES GRAPHICAL REPRESENTAITON OF FIXTURES



    private Mario player;




    //steps, define orthograthic cam, viewport, construct these in playscreen, set in render and resize
    //view ports. Screen viewport doesn't scale so bigger screen can see more
    //fitviewport always maintains ration
    public PlayScreen(MarioBros game) {
        this.game = game;
        //create cam used to follow mario
        gamecam = new OrthographicCamera();
        //create viewport to maintain aspect ration
        gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT/ MarioBros.PPM, gamecam);
        //create hud for game score etc
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ MarioBros.PPM);

        //
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);



        // ADDING IN BOX2D
        world = new World(new Vector2(0, -10), true); // true = sleep objects at rest (good for memory)
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();//LATER WILL COME OUT OF CONSTRUCTOR INTO ITS OWN CLAS
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Create Ground Body & fixture
        //get(2) - takes object from tiles
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            // three types/ dynamicbody (afected by forces), static body,kinematic can be manipulated with velocity
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ MarioBros.PPM, (rect.getY() + rect.getHeight() / 2)/ MarioBros.PPM);

            //lets add it
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2/ MarioBros.PPM, rect.getHeight() / 2/ MarioBros.PPM);
            fdef.shape = shape;
            // adds it
            body.createFixture(fdef);
        }


        //Create pipe Body & fixture
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            // three types/ dynamicbody (afected by forces), static body,kinematic can be manipulated with velocity
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ MarioBros.PPM, (rect.getY() + rect.getHeight() / 2)/ MarioBros.PPM);

            //lets add it
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2/ MarioBros.PPM, rect.getHeight() / 2/ MarioBros.PPM);
            fdef.shape = shape;
            // adds it
            body.createFixture(fdef);
        }


        //Create brick Body & fixture
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            // three types/ dynamicbody (afected by forces), static body,kinematic can be manipulated with velocity
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ MarioBros.PPM, (rect.getY() + rect.getHeight() / 2)/ MarioBros.PPM);

            //lets add it
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2/ MarioBros.PPM, rect.getHeight() / 2/ MarioBros.PPM);
            fdef.shape = shape;
            // adds it
            body.createFixture(fdef);
        }

        //Create coin Body & fixture
        for (MapObject object : map.getLayers().get(4 ).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            // three types/ dynamicbody (afected by forces), static body,kinematic can be manipulated with velocity
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ MarioBros.PPM, (rect.getY() + rect.getHeight() / 2)/ MarioBros.PPM);

            //lets add it
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2/ MarioBros.PPM, rect.getHeight() / 2/ MarioBros.PPM);
            fdef.shape = shape;
            // adds it
            body.createFixture(fdef);
        }
        player = new Mario(world);
    }
    @Override
    public void show () {

        }

    public void handleInput(float dt) {
        // UP BUTTON
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            //impulse is instantaneous change /force is diff. getworldcenter is where on body we want to apply force.
        // of center will give a torque
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true); // true wake body up asleep
        // RIGHT BUTTON
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        // LEFT BUTTON
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }


    // for updating gameworld
    public void update(float dt) {
        handleInput(dt);
        // vel and pos iterations - affects how two bodies react during a colision
        world.step(1/60f, 6, 2);

        gamecam.position.x = player.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {// passed into update
        update(delta);//
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();// should be after screen is cleared

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height); // find out the actual size
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    } // screen interface badlogic
}
