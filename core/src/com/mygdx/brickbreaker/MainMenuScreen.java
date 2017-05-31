package com.mygdx.brickbreaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.models.Body;
import com.mygdx.brickbreaker.models.Brick;

/**
 * Created by vwraposo on 29/05/17.
 */

public class MainMenuScreen implements Screen {
    final BrickBreaker game;

    public MainMenuScreen(final BrickBreaker game) {
        this.game = game;


        game.ball = new com.mygdx.brickbreaker.models.Ball("ball.png");
        game.platform = new Body("bar.png");

        game.ball.body.x = game.WIDTH / 2 - game.ball.body.width / 2;
        game.ball.body.y = game.HEIGHT / 8 + game.platform.body.height / 2;

        game.platform.body.x = game.WIDTH / 2 - game.platform.body.width / 2;
        game.platform.body.y = game.HEIGHT / 8 - game.platform.body.height / 2;

        game.bricks = MapGenerator.getMap(game, 1);

        //Fundo Cinza
        game.batch.setColor(Color.GRAY);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.17f, 0.19f, 0.25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();

        game.font.draw(game.batch, "Brick Breaker ", game.WIDTH/4, game.HEIGHT/2);
        game.font.draw(game.batch, "Tap anywhere to begin!", game.WIDTH/8, 3*game.HEIGHT/8);


        game.batch.draw(game.ball.image, game.ball.body.x, game.ball.body.y);
        game.batch.draw(game.platform.image, game.platform.body.x, game.platform.body.y);

        for (Array row : game.bricks)
            for (Object b : row) {
                com.mygdx.brickbreaker.models.Brick brick = (Brick) b;
                game.batch.draw(brick.getImage(), brick.body.x, brick.body.y);
            }

        game.batch.end();

        // Choose mode
        game.setMode(game.MODE_1);
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
