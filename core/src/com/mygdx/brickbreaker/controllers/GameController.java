package com.mygdx.brickbreaker.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.BrickBreaker;
import com.mygdx.brickbreaker.models.*;
import com.mygdx.brickbreaker.models.Brick;

import java.util.Iterator;

import static com.badlogic.gdx.math.MathUtils.PI;

/**
 * Created by vwraposo on 01/06/17.
 */

public class GameController {
    final BrickBreaker game;
    private com.mygdx.brickbreaker.models.Ball ball;
    private com.mygdx.brickbreaker.models.Body platform;
    private Array<Brick> bricks;

    private int acitve_bricks = 0;

    private Sound platformHit;


    public GameController(BrickBreaker game) {
        // Locais
        this.game = game;
        this.ball = game.ball;
        this.platform = game.platform;
        this.bricks = game.bricks;

        platformHit= Gdx.audio.newSound(Gdx.files.internal("platform_hit.ogg"));
//        platformHit= Gdx.audio.newSound(Gdx.files.internal("bat_hit_ball.ogg"));
    }

    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(ball.image, ball.body.x, ball.body.y);
        game.batch.draw(platform.image, platform.body.x, platform.body.y);

        for (Brick brick : bricks)
            game.batch.draw(brick.getImage(), brick.body.x, brick.body.y, brick.body.width, brick.body.height);

        game.batch.end();


        // Movement processing
        inputProcessing();
        objectsProcessing(delta);


    }

    private void objectsProcessing(float delta) {
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
            // You lost
            Gdx.app.log("ENDGAME", "You lost");
            game.gameLost();
            Gdx.app.log("ENDGAME", game.result().toString());
            game.setState(game.FINISH);
        }

        if (ball.body.overlaps(platform.body) &&
                ball.body.y + ball.body.height/2 > platform.body.y + platform.body.height) {
            Vector2 d = new Vector2(
                    (ball.body.x + ball.body.width / 2) - (platform.body.x + platform.body.width / 2),
                    (ball.body.y + ball.body.height / 2) - (platform.body.y - platform.body.height)
            );
            ball.velocity = d.nor().scl(ball.norm);
            ball.body.y = platform.body.y + platform.body.height;
            platformHit.play();
        }


        // Ball and bricks interaction

        Iterator<Brick> iter = bricks.iterator();
        while(iter.hasNext()) {
            Brick brick = iter.next();
            if (ball.body.overlaps(brick.body)) {
                // Vertical
                //TODO: Melhorar colisao

                boolean ball_above = is_ball_above_brick(ball,brick);
                boolean ball_beneath = is_ball_beneath_brick(ball,brick);
                boolean ball_from_left = is_ball_on_left_of_brick(ball,brick);
                boolean ball_from_right = is_ball_on_right_of_brick(ball,brick);

                if (ball_above && ball_from_left) {
                    Vector2 rotado = ball.velocity.rotate(-PI/4);
                    rotado.y *= -1;
                    ball.velocity = rotado.rotate(PI/4);
                } else if (ball_above && ball_from_right) {
                    Vector2 rotado = ball.velocity.rotate(PI/4);
                    rotado.y *= -1;
                    ball.velocity = rotado.rotate(-PI/4);
                } else if (ball_beneath && ball_from_left) {
                    Vector2 rotado = ball.velocity.rotate(PI/4 + PI);
                    rotado.y *= -1;
                    ball.velocity = rotado.rotate(-PI/4 - PI);
                } else if (ball_beneath && ball_from_right) {
                    Vector2 rotado = ball.velocity.rotate(PI/4 + PI/2);
                    rotado.y *= -1;
                    ball.velocity = rotado.rotate(-PI/4 - PI/2);
                } else if (ball_above || ball_beneath) {
                    ball.velocity.y *= -1;
                } else if (ball_from_left || ball_from_right) {
                    ball.velocity.x *= -1;
                }

                // Remove life
                if (brick.hit()) {
                    iter.remove();
                }
            }
        }

        if (bricks.size == 0) {
            Gdx.app.log("ENDGAME", "You won");
            game.gameWon();
            game.setState(game.FINISH);
            dispose();
        }

        ball.move(delta);
    }

    private boolean is_ball_beneath_brick (Ball ball, Brick brick) {
        return ball.hat() > brick.shoe() && (
                        ball.left() < brick.right() ||
                        ball.right() > brick.left()
        );
    }

    private boolean is_ball_above_brick (Ball ball, Brick brick) {
        return brick.hat() > ball.shoe() && (
                ball.left() < brick.right() ||
                        ball.right() > brick.left()
        );
    }

    private boolean is_ball_on_left_of_brick (Ball ball, Brick brick) {
        return ball.right() > brick.left() && (
                ball.shoe() < brick.hat() ||
                        ball.hat() > brick.shoe()
        );
    }

    private boolean is_ball_on_right_of_brick (Ball ball, Brick brick) {
        return brick.right() > ball.left() && (
                ball.shoe() < brick.hat() ||
                        ball.hat() > brick.shoe()
        );
    }

    private void vertex_hit (Ball ball) {

    }

    private void inputProcessing() {
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

    private  void dispose() {
        platformHit.dispose();
    }
}



