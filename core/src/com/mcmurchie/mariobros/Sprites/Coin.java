package com.mcmurchie.mariobros.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmurchie.mariobros.MarioBros;

/**
 * Created by adammcmurchie on 03/02/2017.
 */

public class Coin extends InteractiveTileObject {//look at interactive tileobject
    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);



    }
}
