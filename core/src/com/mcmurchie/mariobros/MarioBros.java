package com.mcmurchie.mariobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcmurchie.mariobros.Screens.PlayScreen;

// this Game class 	is an applciation listener and delegates the rendering to a screen
//this allows the game to easily have multiple screens
// screens aren't disposed so we need to do that
// java.lang.object ---> com.badlogic.Game  (ApplicaitonListener interface)
public class MarioBros extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208; // Virtual width and height
	public static final float PPM = 100;

	public static final short DEFAULT_BIT = 1;// every fixture already is set to one in catagory
	public static final short MARIO_BIT = 2;// we use power of two bitwise and/or operation
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;


	public SpriteBatch batch;


	/*
	WARNING USING ASSET MANAGER IN A STATIC WAY CAN CAUSE ISSUES
	ESPECIALLY ON ANDROID, INSTEAD YOU MAY WANT TO PASS AROUND THE ASSET MANAGER TO
	THOSE CLASSES THAT NEED IT. BUT WE WILL BE USING THE STATIC CONTEXT IN THIS TUTORIAL
	TO SAVE TIME
	 */
	public static AssetManager manager;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		//queue up all the assets to be loaded by manager
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.finishLoading(); // blocks everything and allows assets to finish loading


		setScreen(new  PlayScreen(this)); // passes this screen itself
	}
	@Override
	public void dispose () {
		super.dispose();
		manager.dispose();
		batch.dispose();// check what this is
	}

	@Override
	public void render () {
		super.render();
		/*
		manager.update();once returns true/false to say loadedcomplete
		or
		if(manager.update()){
		put stuff in here that require these assets
		}
		 */
	}
	

}
