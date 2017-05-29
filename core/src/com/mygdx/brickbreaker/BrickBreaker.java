package com.mygdx.brickbreaker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class BrickBreaker extends ApplicationAdapter {
	SpriteBatch batch;
	Texture ball;
	Texture brick;
	Texture bar;
    BitmapFont font12;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		ball = new Texture("ball.png");
		brick = new Texture("brick_easy.png");
		bar = new Texture("bar.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        parameter.shadowColor = Color.BLACK;
        font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.17f, 0.19f, 0.25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(ball, 0, 0);
		batch.draw(brick, 100, 200);
		batch.draw(bar, 0, 0);
        font12.draw(batch, "Hello World", 400, 400);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		ball.dispose();
		brick.dispose();
		bar.dispose();
	}
}
