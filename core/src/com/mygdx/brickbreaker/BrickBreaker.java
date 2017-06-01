package com.mygdx.brickbreaker;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.models.Body;
import com.mygdx.brickbreaker.models.Brick;

public class BrickBreaker extends Game {
	public final Integer MODE_1 = 1;
	public final Integer MODE_2 = 2;

    public SpriteBatch batch;
    public BitmapFont font;
	public OrthographicCamera camera;
	public final Integer WIDTH = 1440;
	public final Integer HEIGHT = 2560;
	private Integer mode;

	//Objetos do jogo
	public com.mygdx.brickbreaker.models.Ball ball;
	public Body platform;
	public Array<Array<Brick>> bricks;

	// Estado do jogo
	private Integer state;
	//Estados
	public final Integer MENU = 0;
	public final Integer GAME = 1;
	public final Integer FINISH = 2;
	
	@Override
	public void create () {
		// Set up do batch
    	batch = new SpriteBatch();

		// Set up das fontes
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;

        parameter.shadowColor = Color.BLACK;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();

		// Set up da camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);

		// Set up do estado
		setState(MENU);
        this.setScreen(new GameScreen(this));
	}



	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getState() {
		return state;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Integer getMode() {
		return mode;
	}
}
