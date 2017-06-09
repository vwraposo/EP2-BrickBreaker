package com.mygdx.brickbreaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.brickbreaker.controllers.EndGameController;
import com.mygdx.brickbreaker.controllers.GameController;
import com.mygdx.brickbreaker.controllers.MenuController;

/**
 * Created by vwraposo on 29/05/17.
 */

public class GameScreen implements Screen {
    final BrickBreaker game;
    private GameController gameController;
    private MenuController menuController;
    private EndGameController endGameController;


    public GameScreen(final BrickBreaker game) {
        this.game = game;
        this.menuController = new MenuController(this.game);
        this.gameController = new GameController(this.game);
        this.endGameController = new EndGameController(this.game);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.32f, 0.56f, 0.52f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        if (game.getState() == game.MENU) {
            Gdx.app.log("STATE", "In menu");
            Gdx.input.setInputProcessor(menuController.stage);
            menuController.render(delta);
        }
        else if(game.getState() == game.GAME) {
            Gdx.app.log("STATE", "In game");
            gameController.render(delta);
        }
        else {
            Gdx.app.log("STATE", "End Game");
            Gdx.input.setInputProcessor(endGameController.stage);
            endGameController.render(delta);
        }
    }

    @Override
    public void dispose() {
        game.ball.image.dispose();
        game.platform.image.dispose();
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
}
