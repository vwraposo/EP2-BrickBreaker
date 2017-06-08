package com.mygdx.brickbreaker;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.brickbreaker.models.Body;
import com.mygdx.brickbreaker.models.Brick;
import com.mygdx.brickbreaker.models.Maps;
import com.mygdx.brickbreaker.models.Special;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class BrickBreaker extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
	public OrthographicCamera camera;
    public FitViewport viewp;
	public final Integer WIDTH = 1440;
	public final Integer HEIGHT = 2560;

	//Objetos do jogo
	public com.mygdx.brickbreaker.models.Ball ball;
	public Body platform;
    public Maps maps;
	public Array<Brick> bricks;
	public Array<Special> specials;

	// Estado do jogo
	private Boolean victory = true;
	private Integer state;

	//Estados
	public final Integer MENU = 0;
	public final Integer GAME = 1;
	public final Integer FINISH = 2;

	// Musica
	private Music background_track;
	
	@Override
	public void create () {
		// Set up do batch
    	batch = new SpriteBatch();

		// Set up das fontes
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("clutchee.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 256;

        parameter.shadowColor = Color.BLACK;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();

		// Set up da camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
        viewp = new FitViewport(WIDTH, HEIGHT, camera);

		// Set up do estado
		setState(MENU);
        this.setScreen(new GameScreen(this));

		// Musica
		background_track = Gdx.audio.newMusic(Gdx.files.internal("background_track.mp3"));

		background_track.setVolume(0.42f);
		background_track.setLooping(true);
		background_track.play();

	}

	public void startGame(int level) {
        ball = new com.mygdx.brickbreaker.models.Ball("ball.png");
        platform = new Body("bar.png", 300, 50);

        ball.body.x = WIDTH / 2 - ball.body.width / 2;
        ball.body.y = HEIGHT / 8 + platform.body.height / 2;

        platform.body.x = WIDTH / 2 - platform.body.width / 2;
        platform.body.y = HEIGHT / 8 - platform.body.height / 2;

        maps = new Maps(this, level);
        bricks = maps.getMap();
        specials = maps.getSpecial();
    }


	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
        background_track.dispose();
	}

	public void nextLevel() {
        maps.nextLevel();
        bricks = maps.getMap();
        specials = maps.getSpecial();
    }

    public void previousLevel() {
        maps.previousLevel();
        Gdx.app.log("PREV", String.valueOf(maps.getLevel()));
        bricks = maps.getMap();
        specials = maps.getSpecial();
    }

	public void setState(Integer state) {
        this.state = state;
	}

	public Integer getState() {
		return state;
	}

	public void gameWon() {
		victory = true;
	}

	public void gameLost() {
		victory = false;
	}

	public Boolean result() {
		return victory;
	}
}
