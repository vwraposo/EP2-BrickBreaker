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
    public final float NORM = 1100;
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

    public void reset() {
        norm = NORM;
        float ang =  random(- PI / 4, PI / 4);
        velocity = new Vector2();
        velocity.x = sin(ang)*norm;
        velocity.y = cos(ang)*norm;
    }
    public void speed_up () {
        Gdx.app.log("SPEED_UP", "Speedup");
       norm = (float) 1.7*NORM;
    }

    public void slow_down () {
        norm = NORM / (float) 1.7;
    }

    public void move(float delta) {

        //Restore Velocity
        Gdx.app.log("NORM", String.valueOf(norm));
        if (norm > NORM + eps) {
            norm -= delta*(1.7*NORM - NORM) / 3.2;
            velocity = velocity.nor().scl(norm);
        }
        this.body.x += velocity.x * delta;
        this.body.y += velocity.y * delta;
    }

    public float leftSide() {
        return this.body.x;
    }

    public float rightSide() {
        return this.body.x + this.body.width;
    }

    public float bottom() {
        return this.body.y;
    }

    public float top() {
        return this.body.y + this.body.height;
    }

    public float centerX () {
        return this.body.x + this.body.width/2;
    }

    public float centerY () {
        return this.body.y + this.body.height/2;
    }

    public void reflectX () {
        this.velocity.x *= -1;
    }

    public void reflectY () {
        this.velocity.y *= -1;
    }

    public Ball previousFrame (float delta) {
        Ball clone = new Ball("ball.png");
        clone.body.x = this.body.x - this.velocity.x * delta;
        clone.body.y = this.body.y - this.velocity.y * delta;

        return clone;
    }
}
