package com.mygdx.brickbreaker.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by vwraposo on 29/05/17.
 */

public class Body {
    public final Texture image;
    public Rectangle body;

    public Body (String img, float w, float h) {
        image = new Texture(Gdx.files.internal(img));
        body = new Rectangle();
        body.width = w;
        body.height = h;
    }
}
