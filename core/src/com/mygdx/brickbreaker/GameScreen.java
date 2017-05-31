package com.mygdx.brickbreaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.models.Body;
import com.mygdx.brickbreaker.models.Brick;

/**
 * Created by vwraposo on 29/05/17.
 */

public class GameScreen implements Screen {
    final BrickBreaker game;
    private com.mygdx.brickbreaker.models.Ball ball;
    private Body platform;
    private Array<Array<com.mygdx.brickbreaker.models.Brick>> bricks;



    public GameScreen(final BrickBreaker game) {
        // Locais
        this.game = game;
        this.ball = game.ball;
        this.platform = game.platform;
        this.bricks = game.bricks;

        game.batch.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        switch (state) {
//            case (MENU)
//                renderMenu(delta);
//                break;
            case (GAME)
                renderGame(delta);
                break;
            case (FINISH)
                renderFinish(delta);
        }


        Gdx.gl.glClearColor(0.17f, 0.19f, 0.25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(ball.image, ball.body.x, ball.body.y);
        game.batch.draw(platform.image, platform.body.x, platform.body.y);

        for (Array row : bricks)
            for (Object b : row) {
                com.mygdx.brickbreaker.models.Brick brick = (com.mygdx.brickbreaker.models.Brick) b;
                game.batch.draw(brick.getImage(), brick.body.x, brick.body.y);
            }

        game.batch.end();

        // Movement processing
        inputProcessing();
        objectsProcessing(delta);
    }


    @Override
    public void dispose() {
        ball.image.dispose();
        platform.image.dispose();
    }



    public void objectsProcessing(float delta) {
        // Collisions
        Gdx.app.log("Movimento", "processando Objetos");
        // Left wall
        if(ball.body.x <= 0) {
            //sound.play();
            ball.velocity.x *= -1;
            ball.body.x = 0;
        }
        // Right wall
        else if(ball.body.x >= game.WIDTH - ball.body.width / 2) {
            //sound.play();
            ball.velocity.x *= -1;
            ball.body.x = game.WIDTH - ball.body.width / 2;
        }
        // Top wall
        else if(ball.body.y >= game.HEIGHT - ball.body.height) {
            //sound.play();
            ball.velocity.y *= -1;
            ball.body.y = game.HEIGHT - ball.body.height;
        }
        else if (ball.body.y <= 0) {
            // You lose
        }

        //Bar and ball interaction ARRUMAR
        if (ball.body.overlaps(platform.body) &&
                ball.body.y + ball.body.height/2 > platform.body.y + platform.body.height) {
            ball.velocity.y *= -1;
            ball.body.y = platform.body.y + platform.body.height;
        }
        else {
            // Bater lateralmente
        }

        // Ball and bricks interaction
        for (Array row : bricks) {
            for (Object b : row) {
                com.mygdx.brickbreaker.models.Brick brick = (Brick) b;
                if (ball.body.overlaps(brick.body)) {
                    // Vertical (VERIFICAR SE TA CERTO)
                    if (ball.body.x + ball.body.width >= brick.body.x &&
                            ball.body.x <= brick.body.x + brick.body.width)
                        ball.velocity.y *= -1;
                    // Horizontal
                    else
                        ball.velocity.x *= -1;

                    // Remove life
                    if (brick.hit()) row.removeValue(brick, true);
                }
            }
            if (row.size == 0) bricks.removeValue(row, true);
        }

        ball.move(delta);
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
                    platform.body.x = touchPos.x - platform.body.width / 2;
            }
        }
        else {
        }
        // Limitando laterais
        if (platform.body.x < 0) platform.body.x = 0;
        else if (platform.body.x + 2 * platform.body.width / 2 > game.WIDTH)
            platform.body.x = game.WIDTH - platform.body.width;
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
