package com.mcmurchie.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmurchie.mariobros.MarioBros;
import com.mcmurchie.mariobros.Scenes.Hud;
import com.mcmurchie.mariobros.Screens.PlayScreen;

/**
 * Created by adammcmurchie on 03/02/2017.
 */

public class Coin extends InteractiveTileObject {//look at interactive tileobject
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28; // one more from actual value in tileset
    public Coin(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);//so we have access to this object in InteractiveTileObject
        setCategoryFilter(MarioBros.COIN_BIT);

    }
    @Override
    public void onHeadHit() {
        if(getCell().getTile().getId() == BLANK_COIN)
            MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else
            MarioBros.manager.get("audio/sounds/coin.wav", Sound.class).play();


        Gdx.app.log("Coin", "collision");
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);
    }

}
