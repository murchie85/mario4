package com.mcmurchie.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmurchie.mariobros.Screens.PlayScreen;

/**
 * Created by adammcmurchie on 05/02/2017.
 */

 public class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
    }
}
