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
    public static Integer BOOSTER = 1;
    private Integer type;
    private Texture image;


    public Special (Integer type, float w, float h, Vector2 velocity, Vector2 limitX, Vector2 limitY) {
        super(0, w, h, velocity, limitX, limitY);

        this.type = type;
        image = new Texture(Gdx.files.internal("speed_up.png"));
    }

    public Texture getImage() {
        return image;
    }

    public boolean isBooster() {
        return this.type == BOOSTER;
    }

    public void action(BrickBreaker game) {
        if (isBooster()) {
            game.ball.speed_up();
        }
    }
}

