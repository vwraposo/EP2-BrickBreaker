package com.mygdx.brickbreaker.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.lang.reflect.Array;

/**
 * Created by vwraposo on 29/05/17.
 */

public class Brick {
    public static final Integer width = 200;
    public static final Integer height = 90;
    public static final Integer LEFT_SIDE = 1;
    public static final Integer RIGHT_SIDE = 2;
    public static final Integer TOP_SIDE = 3;
    public static final Integer BOTTOM_SIDE = 4;

    private Texture[] images;
    public Rectangle body;
    private Integer initial_lives;
    private Integer lives;
    public Vector2 velocity;
    public Vector2 limitX;
    public Vector2 limitY;

    private boolean visible;

    public Brick (Integer lives, float w, float h, Vector2 velocity, Vector2 limitX, Vector2 limitY) {
        this.body = new Rectangle();
        body.height = h;
        body.width = w;
        this.velocity = velocity;
        this.limitX = limitX;
        this.limitY = limitY;

        this.initial_lives = lives;
        this.lives = lives;
        images = new Texture[3];
        images[0] = new Texture(Gdx.files.internal("brick_easy.png"));
        images[1] = new Texture(Gdx.files.internal("brick_medium.png"));
        images[2] = new Texture(Gdx.files.internal("brick_hard.png"));

        this.visible = true;
    }

    public void move(float delta) {
        Gdx.app.log("BALL", "Move");
        body.x += velocity.x * delta;
        body.y += velocity.y * delta;
        if (body.x < limitX.x) {
            body.x = limitX.x;
            velocity.x *= -1;
        } else if (body.x +body.width > limitX.y){
            body.x = limitX.y - body.width;
            velocity.x *= -1;
        }
        if (body.y < limitY.x) {
            body.y = limitY.x;
            velocity.y *= -1;
        } else if (body.y + body.height > limitY.y) {
            body.y = limitY.y - body.height;
            velocity.y *= -1;
        }
    }

    public Texture getImage() {
        return images[(lives - 1)%3];
    }

    public boolean hit() {
        if (--this.lives == 0)
            this.visible = false;
        return !this.visible;
    }

    public boolean is_visible () {
        return this.visible;
    }

    public void resetLives() {
        this.lives = this.initial_lives;
    }

    public void resetVisibility() {
        this.visible = true;
    }

    public float centerX () {
        return this.body.x + this.body.width/2;
    }

    public float centerY() {
        return this.body.y + this.body.height/2;
    }

    public float top () {
        return this.body.y + this.body.height;
    }

    public float bottom () {
        return this.body.y;
    }

    public float left () {
        return this.body.x;
    }

    public float right() {
        return this.body.x + this.body.width;
    }

    public int collision_side (Ball ball) {
        String TAG = "BRICK_COLL";

        Vector2 r = new Vector2(this.body.width/2,  this.body.height/2);
        Vector2 s = new Vector2(this.body.width/2, -this.body.height/2);
        Vector2 b = new Vector2(ball.centerX() - this.centerX(), ball.centerY() - this.centerY());

        float angleR = r.angle(b);
        float angleS = s.angle(b);

        if (angleR > 0) {
            if (angleS > 0) {
                Gdx.app.log(TAG, "TOP_SIDE");
                return TOP_SIDE;
            } else {
                Gdx.app.log(TAG, "LEFT_SIDE");
                return LEFT_SIDE;
            }
        } else {
            if (angleS > 0) {
                Gdx.app.log(TAG, "RIGHT_SIDE");
                return RIGHT_SIDE;
            } else {
                Gdx.app.log(TAG, "BOTTOM_SIDE");
                return BOTTOM_SIDE;
            }
        }
    }
}
