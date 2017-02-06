package com.mcmurchie.mariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.mcmurchie.mariobros.Tools.B2WorldCreator;
import com.mcmurchie.mariobros.Tools.WorldContactListener;

/**
 * Created by adammcmurchie on 14/01/2017.
 */
// SCREEN IS A GDX CLASS represents many screens
    // this exectues logic and drws things to scree n

public class PlayScreen implements Screen { //GENERATE ALL GDX METHODS
    private MarioBros game;
    private TextureAtlas atlas;


    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // BOX 2D VARIABLES
    private World world;
    private Box2DDebugRenderer b2dr; // GIVES GRAPHICAL REPRESENTAITON OF FIXTURES
    // sprites
    private Mario player;

    private Music music;




    //steps, define orthograthic cam, viewport, construct these in playscreen, set in render and resize
    //view ports. Screen viewport doesn't scale so bigger screen can see more
    //fitviewport always maintains ration
    public PlayScreen(MarioBros game) {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

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

        new B2WorldCreator(this);// code moved to tools

        // create mario in our game world
        player = new Mario(this);

        world.setContactListener(new WorldContactListener());

        music = MarioBros.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.play();

        }

        public TextureAtlas getAtlas(){return atlas;}



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

        player.update(dt);
        hud.update(dt); //update the hud time

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
        //set our batch to render BOX2DDEBUTLINES
        b2dr.render(world, gamecam.combined);

        //set our batch to draw mario
        game.batch.setProjectionMatrix(gamecam.combined);//this is the main cam running around
        game.batch.begin();// open the box to put textures inside
        player.draw(game.batch);// giving the sprite gamebatch to draw itself  (the spriteclass knows how to draw itself)
        game.batch.end();

        //set our batch to now draw what HUD sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height); // find out the actual size
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose(); // dispose method exists in hud
    } // screen interface badlogic
}
