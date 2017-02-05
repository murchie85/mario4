package com.mcmurchie.mariobros.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mcmurchie.mariobros.Sprites.InteractiveTileObject;

/**
 * Created by adammcmurchie on 04/02/2017.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA(); // we don't know if one is the head and the other is an object
        Fixture fixB = contact.getFixtureB();

        // if it is the head, then we want to get the fixtures 
        if(fixA.getUserData() == "head" || fixB.getUserData() == "head") { //then we know the collison occured with his head
                Fixture head = fixA.getUserData() == "head" ? fixA : fixB;// if fixA is the head, assign otherwise assign fixB
                Fixture object = head == fixA ? fixB : fixA; // does head equal fix A, if it does we know Fix B is hte collision object, othewise fix A is collision object

            // returns true if object.getuserdata isn't null, and its actually a interactive tile object (extended)
            if(object.getUserData() !=null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();// so we can execute this method
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
