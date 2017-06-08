package com.mygdx.brickbreaker.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.PI;
import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.sin;

/**
 * Created by vwraposo on 31/05/17.
 */

public class Ball extends Body {
    public Vector2 velocity;
    public final float norm = 1300;

    public Ball (String img) {
        super(img);

        float ang =  random(- PI / 4, PI / 4);
        velocity = new Vector2();
        velocity.x = sin(ang)*norm;
        velocity.y = cos(ang)*norm;
    }


    public void move(float delta) {
        Gdx.app.log("BALL", "Move");
        this.body.x += velocity.x * delta;
        this.body.y += velocity.y * delta;
    }
}
