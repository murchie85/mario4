package com.mcmurchie.mariobros.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmurchie.mariobros.MarioBros;
import com.mcmurchie.mariobros.Screens.PlayScreen;

/**
 * Created by adammcmurchie on 03/02/2017.
 */

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    // THIS CODE WAS CUT FROM BRICK AND COIN AND PASTED HERE (TO SAVE DUPLICATION - HENCE THE ABSTRACT ITS THE SAME)
    public InteractiveTileObject(PlayScreen screen, Rectangle bounds){
        this.world = screen.getWorld();
        this.map   = screen.getMap();
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
        fixture = body.createFixture(fdef);
    }
    //abstract function
    public abstract void onHeadHit();
    // this is what we do if we already have created fixture
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    //change the brick to broken brick
    //find the layer
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1); // layer is array 1
        //get position and scale up
        return layer.getCell((int)(body.getPosition().x * MarioBros.PPM /16),
                (int)(body.getPosition().y * MarioBros.PPM /16));

    }
}
