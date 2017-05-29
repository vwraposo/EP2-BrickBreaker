package com.mygdx.brickbreaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.lang.reflect.Array;

/**
 * Created by vwraposo on 29/05/17.
 */

public class Brick {
    public static final Integer width = 75;
    public static final Integer height = 30;

    private Texture[] images;
    public Rectangle body;
    private Integer lives;


    public Brick (Integer lives) {
        this.body = new Rectangle();
        this.lives = lives;
        images = new Texture[3];
        images[0] = new Texture(Gdx.files.internal("brick_easy.png"));
    }

    public Texture getImage() {
        return images[lives - 1];
    }

}
