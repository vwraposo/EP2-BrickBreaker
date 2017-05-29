package com.mygdx.brickbreaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by vwraposo on 29/05/17.
 */

public class GameScreen implements Screen {
    final BrickBreaker game;
    private Body ball;
    private Body bar;
    private Array<Array<Brick>> bricks;

    private final Integer SPACE;

    public GameScreen(final BrickBreaker game) {
        this.game = game;
        SPACE = game.WIDTH / 16;
        ball = new Body("ball.png", 30, 30);
        bar = new Body("bar.png", 150, 25);

        ball.body.x = game.WIDTH / 2 - ball.width;
        ball.body.y = game.HEIGHT / 10 + bar.height;

        bar.body.x = game.WIDTH / 2 - bar.width;
        bar.body.y = game.HEIGHT / 10 - bar.height;

        // Fazendo o mapa (Construir um gerador talvez)
        int h = game.HEIGHT - SPACE - Brick.height;
        bricks = new Array<Array<Brick>>();
        for (int i = 0; i < 6; i++) {
            bricks.add(new Array<Brick>());
        }
        for (Array row : bricks){
            for (int i = 0; i < 8; i++) {
                Brick brick = new Brick(1);
                brick.body.x = i *(Brick.width + SPACE) + SPACE;
                brick.body.y = h;
                row.add(brick);
            }
            h -= Brick.height + SPACE;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.17f, 0.19f, 0.25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(ball.image, ball.body.x, ball.body.y);
        game.batch.draw(bar.image, bar.body.x, bar.body.y);

        for (Array row : bricks)
            for (Object b : row) {
                Brick brick = (Brick) b;
                game.batch.draw(brick.getImage(), brick.body.x, brick.body.y);
            }

        game.batch.end();

        // Movement processing
        inputProcessing();
    }


    @Override
    public void dispose() {
        ball.image.dispose();
        bar.image.dispose();
    }


    public void inputProcessing() {
        // Separar os modos de input
        if(game.getMode() == game.MODE_1) {
            if (Gdx.input.isTouched()) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                game.camera.unproject(touchPos);
                // Limitando altura do toque
                Gdx.app.log("TOUCH", touchPos.toString());
                if (touchPos.y <= game.HEIGHT / 4)
                    bar.body.x = touchPos.x - bar.width;
            }
        }
        else {
        }
        // Limitando laterais
        if (bar.body.x < 0) bar.body.x = 0;
        else if (bar.body.x + 2 * bar.width > game.WIDTH)
            bar.body.x = game.WIDTH - 2 * bar.width;
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
