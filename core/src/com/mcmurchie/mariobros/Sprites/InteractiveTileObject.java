package com.mcmurchie.mariobros.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmurchie.mariobros.MarioBros;

/**
 * Created by adammcmurchie on 03/02/2017.
 */

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    // THIS CODE WAS CUT FROM BRICK AND COIN AND PASTED HERE (TO SAVE DUPLICATION - HENCE THE ABSTRACT ITS THE SAME)
    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map   = map;
        this.bounds = bounds;

        //create coin/brick
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2)/ MarioBros.PPM, (bounds.getY() + bounds.getHeight() / 2)/ MarioBros.PPM);

        //lets add it
        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2/ MarioBros.PPM, bounds.getHeight() / 2/ MarioBros.PPM);
        fdef.shape = shape;
        // adds it
        body.createFixture(fdef);
    }
}
