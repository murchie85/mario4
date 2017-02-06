package com.mcmurchie.mariobros.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mcmurchie.mariobros.MarioBros;
import com.mcmurchie.mariobros.Screens.PlayScreen;



/**
 * Created by adammcmurchie on 02/02/2017.
 */

public class Mario extends Sprite { // extends badlogic gaming sprites
    public enum State{ FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;// times how long we are in a jump/run state
    private boolean runningRight;


        // initialize
    public Mario(PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //RUNNING
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=1; i<4; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        //0.1 float duration of animation
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        //JUMPING (START FROM THE NEXT SPRITE)
        for(int i=4; i<6; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        //0.1 float duration of animation
        marioJump = new Animation(0.1f, frames);

        marioStand = new TextureRegion(getTexture(), 0,0, 16,16);

        defineMario();
        setBounds(0,0, 16/MarioBros.PPM, 16/MarioBros.PPM);
        setRegion(marioStand);// done now we need to render mario
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x  - getWidth() /2, b2body.getPosition().y - getHeight() /2 );// bottom left hand corner
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float  dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = (TextureRegion) marioJump.getKeyFrame(stateTimer); // statetimer cycles thru animaitons i think
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);// true means loop, if its passed individual frametime 0.1 it advances to next frame
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        // flip direction
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
                region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x   > 0 || runningRight) && region.isFlipX()){
                region.flip(true, false);
            runningRight = true;
        }
        // ? - if it does : else
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))//he is going up
            return  State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else return State.STANDING;

    };

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/MarioBros.PPM,32/MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        // create body
        b2body = world.createBody(bdef);

        // define circular shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ MarioBros.PPM);
        fdef.filter.categoryBits = MarioBros.MARIO_BIT;
        fdef.filter.maskBits = MarioBros.DEFAULT_BIT | MarioBros.COIN_BIT | MarioBros.BRICK_BIT;

        fdef.shape =shape;
        b2body.createFixture(fdef);

        //create sensor on mario head
        EdgeShape head = new EdgeShape(); // line between two points
        head.set(new Vector2(-2 / MarioBros.PPM, 6/ MarioBros.PPM), new Vector2(2 / MarioBros.PPM, 6/ MarioBros.PPM)); // in crelation to body origin center of body
        //             *-------*
        //              _______
        //              | o  o |
        //              |____  |


        fdef.shape = head;
        fdef.isSensor = true; //this no longer collides, just acts as a variable for...
        b2body.createFixture(fdef).setUserData("head"); // so we know this fixture is mario's head



    }
}
