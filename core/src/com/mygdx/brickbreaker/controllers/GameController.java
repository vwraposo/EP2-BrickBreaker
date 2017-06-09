package com.mygdx.brickbreaker.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

/**
 * Created by vwraposo on 01/06/17.
 */

public class GameController {
    final BrickBreaker game;


    private Sound platformHit;
    private Sound bumperHit;


    public GameController(BrickBreaker game) {
        // Locais
        this.game = game;

        platformHit= Gdx.audio.newSound(Gdx.files.internal("platform_hit.ogg"));
        bumperHit = Gdx.audio.newSound(Gdx.files.internal("pinball_bumper_sound.mp3"));
    }

    public void render(float delta) {
        Gdx.app.log("FRAMES", String.valueOf(1 / delta));
        game.batch.begin();

        Gdx.app.log("BSIZE", String.valueOf(game.maps.getMap().bricks.size));
        for (Brick brick : game.gameMap.bricks) {
            if (brick.is_visible()) {
                game.batch.draw(brick.getImage(), brick.body.x, brick.body.y, brick.body.width, brick.body.height);
            }
        }
        for (Brick special : game.gameMap.specials)
            game.batch.draw(special.getImage(), special.body.x, special.body.y, special.body.width, special.body.height);

        game.batch.draw(game.ball.image, game.ball.body.x, game.ball.body.y);
        game.batch.draw(game.platform.image, game.platform.body.x, game.platform.body.y);

        game.batch.end();


        // Movement processing
        inputProcessing();
        objectsProcessing(delta);


    }

    private void objectsProcessing(float delta) {
        // Collisions
        Gdx.app.log("Movimento", "processando Objetos");
        // Left wall
        if(game.ball.body.x <= 0) {
            //sound.play();
            game.ball.velocity.x *= -1;
            game.ball.body.x = 0;
        }
        // Right wall
        else if(game.ball.body.x >= game.WIDTH - game.ball.body.width / 2) {
            //sound.play();
            game.ball.velocity.x *= -1;
            game.ball.body.x = game.WIDTH - game.ball.body.width;
        }
        // Top wall
        else if(game.ball.body.y >= game.HEIGHT - game.ball.body.height) {
            //sound.play();
            game.ball.velocity.y *= -1;
            game.ball.body.y = game.HEIGHT - game.ball.body.height;
        }
        else if (game.ball.body.y <= 0) {
            // You lost
            Gdx.app.log("ENDGAME", "You lost");
            game.gameLost();
            Gdx.app.log("ENDGAME", game.result().toString());
            game.setState(game.FINISH);
        }

        if (game.ball.body.overlaps(game.platform.body) &&
                game.ball.body.y + game.ball.body.height/2 > game.platform.body.y + game.platform.body.height) {
            Vector2 d = new Vector2(
                    (game.ball.body.x + game.ball.body.width / 2) - (game.platform.body.x + game.platform.body.width / 2),
                    (game.ball.body.y + game.ball.body.height / 2) - (game.platform.body.y - game.platform.body.height)
            );
            game.ball.velocity = d.nor().scl(game.ball.norm);
            game.ball.body.y = game.platform.body.y + game.platform.body.height;
            platformHit.play();
        }


        // Ball and game.bricks interaction
        for (Brick brick: game.gameMap.bricks) {
            brick.move(delta);
            if (brick.is_visible() && game.ball.body.overlaps(brick.body)) {
                Boolean is_under_or_above_height = (game.ball.body.y + game.ball.body.height / 2 < brick.body.y ||
                        game.ball.body.y + game.ball.body.height / 2 > brick.body.y + brick.body.height);
                Boolean is_under_or_above_width = (game.ball.body.x + game.ball.body.width/2 > brick.body.x ||
                                game.ball.body.x + game.ball.body.width / 2 < brick.body.x + brick.body.width);

                Boolean is_left_or_right_heigth = (game.ball.body.y + game.ball.body.height / 2 < brick.body.y + brick.body.height ||
                        game.ball.body.y + game.ball.body.height / 2 > brick.body.y);
                Boolean is_left_or_right_width = (game.ball.body.x + game.ball.body.width/2 < brick.body.x ||
                        game.ball.body.x + game.ball.body.width / 2 > brick.body.x + brick.body.width);
                // Vertical
                if (is_under_or_above_height && is_under_or_above_width) {
                    game.ball.velocity.y *= -1;
                    if (game.ball.body.y > brick.body.y)
                        game.ball.body.y = brick.body.y + brick.body.height;
                    else
                        game.ball.body.y = brick.body.y - game.ball.body.height;
                } else if (is_left_or_right_heigth && is_left_or_right_width) {
                    game.ball.velocity.x *= -1;
                    if (game.ball.body.x > brick.body.x)
                        game.ball.body.x = brick.body.x + brick.body.width;
                    else
                        game.ball.body.x = brick.body.x - game.ball.body.width;
                } else {
                    game.ball.velocity.x *= -1;
                    game.ball.velocity.y *= -1;
                }

                // Remove life
                if (brick.hit())
                    game.gameMap.brick_count--;
            }
        }

        // Specials
        for (Special special : game.gameMap.specials) {
            if (game.ball.body.overlaps(special.body)) {
                special.action(game);
                bumperHit.play();
            }
            special.move(delta);
        }


        if (game.gameMap.brick_count == 0) {
            Gdx.app.log("ENDGAME", "You won");
            game.gameWon();
            game.setState(game.FINISH);
            dispose();
        }


        game.ball.move(delta);
    }

    private void inputProcessing() {
        // Separar os modos de input

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);
            // Limitando altura do toque
            Gdx.app.log("TOUCH", touchPos.toString());
            if (touchPos.y <= game.HEIGHT / 4)
                game.platform.body.x = touchPos.x - game.platform.body.width / 2;
        }

        // Limitando laterais
        if (game.platform.body.x < 0) game.platform.body.x = 0;
        else if (game.platform.body.x + 2 * game.platform.body.width / 2 > game.WIDTH)
            game.platform.body.x = game.WIDTH - game.platform.body.width;
    }

    private  void dispose() {
        platformHit.dispose();
        bumperHit.dispose();
    }
}



