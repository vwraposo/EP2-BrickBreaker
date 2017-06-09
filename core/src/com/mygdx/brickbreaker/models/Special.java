package com.mygdx.brickbreaker.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.brickbreaker.BrickBreaker;
import com.badlogic.gdx.graphics.Color;



/**
 * Created by vwraposo on 08/06/17.
 */

public class Special extends Brick {
    public static Integer BUMPER = 1;
    private Integer type;
    private Texture image;


    public Special (Integer type, float radius, Vector2 velocity, Vector2 limitX, Vector2 limitY) {
        super(0, radius, radius, velocity, limitX, limitY);

        this.type = type;
        image = new Texture(Gdx.files.internal("bumper.png"));
    }

    public Texture getImage() {
        return image;
    }

    public boolean isBumper() {
        return this.type == BUMPER;
    }

    public void action(BrickBreaker game) {
        if (isBumper()) {
            game.ball.speed_up();
        }
    }
}

