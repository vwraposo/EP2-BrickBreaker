package com.mygdx.brickbreaker;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.brickbreaker.models.Ball;
import com.mygdx.brickbreaker.models.Body;
import com.mygdx.brickbreaker.models.GameMap;
import com.mygdx.brickbreaker.models.Maps;
import com.mygdx.brickbreaker.models.Special;


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
	public GameMap gameMap;

	// Estado do jogo
	private Boolean victory = true;
	private Integer state;

	//Estados
	public final Integer MENU = 0;
	public final Integer GAME = 1;
	public final Integer FINISH = 2;

	// Musica
	private Music background_track;
	private Sound platformHit;
	private Sound booster;
	private Sound mud;
	private Sound breaking;
	private Sound metalSound;
	
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

		background_track.setVolume(0.38f);
		background_track.setLooping(true);
		background_track.play();

		platformHit= Gdx.audio.newSound(Gdx.files.internal("platform_hit.ogg"));
		booster = Gdx.audio.newSound(Gdx.files.internal("speedup.mp3"));
		mud = Gdx.audio.newSound(Gdx.files.internal("slowdown.mp3"));
		breaking = Gdx.audio.newSound(Gdx.files.internal("brick_hit.wav"));
		metalSound = Gdx.audio.newSound(Gdx.files.internal("metal_hit.mp3"));

	}

	public void startGame() {
        ball = new Ball("ball.png");
        platform = new Body("bar.png", 300, 50);

        ball.body.x = WIDTH / 2 - ball.body.width / 2;
        ball.body.y = HEIGHT / 8 + platform.body.height / 2;

        platform.body.x = WIDTH / 2 - platform.body.width / 2;
        platform.body.y = HEIGHT / 8 - platform.body.height / 2;

        maps = new Maps(this);
        gameMap = maps.getMap();
    }

    public void restartGame(int level) {
        batch.setColor(Color.WHITE);
		ball.reset();
        ball.body.x = WIDTH / 2 - ball.body.width / 2;
        ball.body.y = HEIGHT / 8 + platform.body.height / 2;

        platform.body.x = WIDTH / 2 - platform.body.width / 2;
        platform.body.y = HEIGHT / 8 - platform.body.height / 2;

        maps.restartLevel(level);
        maps.setLevel(level);
        gameMap = maps.getMap();
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
		mud.dispose();
		booster.dispose();
		platformHit.dispose();
		metalSound.dispose();
	}

	public void nextLevel() {
        maps.nextLevel();
        gameMap = maps.getMap();
    }

    public void previousLevel() {
        maps.previousLevel();
        Gdx.app.log("PREV", String.valueOf(maps.getLevel()));
        gameMap = maps.getMap();
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

	public void platformSoundPlay() {
		this.platformHit.play();
	}

	public void specialSoundPlay(int specialType) {
		if (specialType == Special.BOOSTER)
			this.booster.play(0.5f);
		else
			this.mud.play(0.5f);
	}

	public void breakingSoundPlay() {
		this.breaking.play();
	}

	public void metalSoundPlay() {
		this.metalSound.play();
	}
}
