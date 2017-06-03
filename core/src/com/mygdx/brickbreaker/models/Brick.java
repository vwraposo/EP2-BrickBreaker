package com.mygdx.brickbreaker.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.lang.reflect.Array;

/**
 * Created by vwraposo on 29/05/17.
 */

public class Brick {
    public static final Integer width = 200;
    public static final Integer height = 90;

    private Texture[] images;
    public Rectangle body;
    private Integer lives;


    public Brick (Integer lives, float w, float h) {
        this.body = new Rectangle();
        body.height = h;
        body.width = w;

        this.lives = lives;
        images = new Texture[3];
        images[0] = new Texture(Gdx.files.internal("brick_easy.png"));
        images[1] = new Texture(Gdx.files.internal("brick_medium.png"));
        images[2] = new Texture(Gdx.files.internal("brick_hard.png"));
    }

    public Texture getImage() {
        return images[lives - 1];
    }

    public boolean hit() {
        lives--;
        if (lives == 0) return true;
        return false;
    }

}
