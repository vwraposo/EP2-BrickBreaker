package com.mygdx.brickbreaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by vwraposo on 29/05/17.
 */

public class Body {
    public final Texture image;
    public Rectangle body;
    public final Integer height;
    public final Integer width;

    public Body (String img, Integer w, Integer h) {
        image = new Texture(Gdx.files.internal(img));
        body = new Rectangle();
        width = w;
        height = h;

    }
}
