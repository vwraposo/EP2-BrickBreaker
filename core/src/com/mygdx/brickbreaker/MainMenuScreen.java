package com.mygdx.brickbreaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;

/**
 * Created by vwraposo on 29/05/17.
 */

public class MainMenuScreen implements Screen {
    final BrickBreaker game;

    public MainMenuScreen(final BrickBreaker game) {
        this.game = game;

        game.ball = new Body("ball.png", 30, 30);
        game.bar = new Body("bar.png", 150, 25);

        game.ball.body.x = game.WIDTH / 2 - game.ball.width;
        game.ball.body.y = game.HEIGHT / 10 + game.bar.height;

        game.bar.body.x = game.WIDTH / 2 - game.bar.width;
        game.bar.body.y = game.HEIGHT / 10 - game.bar.height;

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
        game.batch.draw(game.bar.image, game.bar.body.x, game.bar.body.y);

        for (Array row : game.bricks)
            for (Object b : row) {
                Brick brick = (Brick) b;
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
