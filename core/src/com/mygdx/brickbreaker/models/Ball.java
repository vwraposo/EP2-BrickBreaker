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
    public final float NORM = 1300;
    public float norm;
    private float eps = 5;

    public Ball (String img) {
        super(img, 60, 60);

        norm = NORM;
        float ang =  random(- PI / 4, PI / 4);
        velocity = new Vector2();
        velocity.x = sin(ang)*norm;
        velocity.y = cos(ang)*norm;
    }

    public void speed_up () {
        Gdx.app.log("SPEED_UP", "Speedup");
       norm = 2*NORM;
    }

    public void slow_down () {
        norm = NORM / 2;
    }

    public void move(float delta) {

        //Restore Velocity
        Gdx.app.log("NORM", String.valueOf(norm));
        if (norm > NORM + eps) {
            norm -= delta*(2*NORM - NORM) / 3;
            velocity = velocity.nor().scl(norm);
        }
        this.body.x += velocity.x * delta;
        this.body.y += velocity.y * delta;
    }
}
