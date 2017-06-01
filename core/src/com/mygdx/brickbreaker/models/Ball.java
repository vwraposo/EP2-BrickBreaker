package com.mygdx.brickbreaker.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by vwraposo on 31/05/17.
 */

public class Ball extends Body {
    public Vector2 velocity;

    public Ball (String img) {
        super(img);

        velocity = new Vector2();
        //TODO: Aleatorizar velocidade inicial
        velocity.x = 500;
        velocity.y = 1000;
    }


    public void move(float delta) {
        Gdx.app.log("BALL", "Move");
        this.body.x += velocity.x * delta;
        this.body.y += velocity.y * delta;
    }
}
