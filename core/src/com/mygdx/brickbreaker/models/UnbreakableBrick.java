package com.mygdx.brickbreaker.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by joaofran on 10/06/17.
 */

public class UnbreakableBrick extends Brick {

    private Texture image;

    public UnbreakableBrick(float w, float h, Vector2 velocity, Vector2 limitX, Vector2 limitY) {
        super(1, w, h, velocity, limitX, limitY);
        this.image = new Texture(Gdx.files.internal("brick_unbreak.png"));
    }

    @Override
    public boolean hit() {
        return false;
    }

    @Override
    public Texture getImage() {
        return this.image;
    }
}
