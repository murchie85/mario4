package com.mcmurchie.mariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcmurchie.mariobros.MarioBros;
import com.mcmurchie.mariobros.Scenes.Hud;

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

    //steps, define orthograthic cam, viewport, construct these in playscreen, set in render and resize
    //view ports. Screen viewport doesn't scale so bigger screen can see more
    //fitviewport always maintains ration
    public PlayScreen(MarioBros game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, gamecam);
        hud = new Hud(game.batch);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


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
