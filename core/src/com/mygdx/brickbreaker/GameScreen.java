package com.mygdx.brickbreaker;

import com.badlogic.gdx.Screen;

/**
 * Created by vwraposo on 29/05/17.
 */

public class GameScreen implements Screen {
    final BrickBreaker game;

    public GameScreen(final BrickBreaker game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.font.draw(game.batch, "Playing", game.WIDTH/4, game.HEIGHT/2);
        game.batch.end();
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
