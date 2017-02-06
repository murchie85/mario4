package com.mcmurchie.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmurchie.mariobros.MarioBros;
import com.mcmurchie.mariobros.Scenes.Hud;
import com.mcmurchie.mariobros.Screens.PlayScreen;

/**
 * Created by adammcmurchie on 03/02/2017.
 */

public class Brick extends InteractiveTileObject {//look at interactive tileobject

    public Brick(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);//so we have access to this object in InteractiveTileObject
        setCategoryFilter(MarioBros.BRICK_BIT);


    }
    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "collision");
        setCategoryFilter(MarioBros.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
        MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();

    }
}
