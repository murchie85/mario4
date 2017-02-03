package com.mcmurchie.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmurchie.mariobros.MarioBros;

import static com.badlogic.gdx.math.Interpolation.circle;

/**
 * Created by adammcmurchie on 02/02/2017.
 */

public class Mario extends Sprite { // extends badlogic gaming sprites
    public World world;
    public Body b2body;

    public Mario(World world){
        this.world = world;
        defineMario();
    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/MarioBros.PPM,32/MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        // create body
        b2body = world.createBody(bdef);

        // define circular shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/ MarioBros.PPM);

        fdef.shape =shape;
        b2body.createFixture(fdef);

    }
}
