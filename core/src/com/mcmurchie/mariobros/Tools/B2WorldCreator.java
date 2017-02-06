package com.mcmurchie.mariobros.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmurchie.mariobros.MarioBros;
import com.mcmurchie.mariobros.Screens.PlayScreen;
import com.mcmurchie.mariobros.Sprites.Brick;
import com.mcmurchie.mariobros.Sprites.Coin;
import com.mcmurchie.mariobros.Sprites.Mario;

import static sun.audio.AudioPlayer.player;

/**
 * Created by adammcmurchie on 03/02/2017.
 */

public class B2WorldCreator {
    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
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

            new Brick(screen, rect);
        }

        //Create coin Body & fixture
        for (MapObject object : map.getLayers().get(4 ).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect);

        }


    }
}
