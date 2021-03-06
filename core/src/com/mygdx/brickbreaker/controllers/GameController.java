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

    public GameController(BrickBreaker game) {
        // Locais
        this.game = game;
    }

    public void render(float delta) {
        Gdx.app.log("FRAMES", String.valueOf(1 / delta));
        game.batch.begin();

        Gdx.app.log("BSIZE", String.valueOf(game.maps.getMap().bricks.size));
        for (Brick special : game.gameMap.specials)
            game.batch.draw(special.getImage(), special.body.x, special.body.y, special.body.width, special.body.height);
        for (Brick brick : game.gameMap.bricks) {
            if (brick.is_visible()) {
                game.batch.draw(brick.getImage(), brick.body.x, brick.body.y, brick.body.width, brick.body.height);
            }
        }

        game.batch.draw(game.ball.image, game.ball.body.x, game.ball.body.y,
                game.ball.body.width, game.ball.body.height);
        game.batch.draw(game.platform.image, game.platform.body.x, game.platform.body.y,
                game.platform.body.width, game.platform.body.height);

        game.batch.end();


        // Movement processing
        inputProcessing();
        objectsProcessing(delta);


    }

    private void wall_hit () {
        Ball ball = game.ball;
        if (ball.leftSide() <= 0) {
            ball.reflectX();
            ball.body.x = 0;
        } else if (ball.rightSide() >= game.WIDTH) {
            ball.reflectX();
            ball.body.x = game.WIDTH - ball.body.width;
        } else if (ball.top() >= game.HEIGHT) {
            ball.reflectY();
            ball.body.y = game.HEIGHT - ball.body.height;
        } else if (ball.bottom() <= 0) {
            Gdx.app.log("ENDGAME", "You lost");
            game.gameLost();
            Gdx.app.log("ENDGAME", game.result().toString());
            game.setState(game.FINISH);
        }
    }

    private void platform_hit () {
        Ball ball = game.ball;
        Body platform = game.platform;
        float platform_top = platform.body.y + platform.body.height;
        float platform_centerX = platform.body.x + platform.body.width/2;
        float platform_centerY = platform.body.y - platform.body.height/2;

        if (ball.body.overlaps(platform.body) && ball.centerY() > platform_top) {
            Vector2 d = new Vector2(ball.centerX() - platform_centerX, ball.centerY() - platform_centerY);
            ball.velocity = d.nor().scl(ball.norm);
            ball.body.y = platform_top;
            game.platformSoundPlay();
        }
    }

    private void collision (Brick brick, float delta) {
        Ball ball = game.ball;
        if (brick.is_visible() && ball.body.overlaps(brick.body)) {
            if (brick instanceof UnbreakableBrick)
                game.metalSoundPlay();
            switch (brick.collision_side(ball.previousFrame(delta))) {
                case 3: // TOP_SIDE
                    ball.body.y = brick.top();
                    ball.reflectY();
                    break;
                case 4: // BOTTOM_SIDE
                    ball.body.y = brick.bottom() - ball.body.height;
                    ball.reflectY();
                    break;
                case 1: // LEFT_SIDE
                    ball.body.x = brick.left() - ball.body.width;
                    ball.reflectX();
                    break;
                default: // RIGHT_SIDE
                    ball.body.x = brick.right();
                    ball.reflectX();
            }
            if (brick.hit()) {
                game.gameMap.brick_count--;
                game.breakingSoundPlay();
            }
        }
    }

    private void objectsProcessing(float delta) {
        Gdx.app.log("Movimento", "processando Objetos");

        wall_hit();
        platform_hit();

        for (Brick brick: game.gameMap.bricks) {
            brick.move(delta);
            collision(brick,delta);
        }

        for (Special special : game.gameMap.specials) {
            if (game.ball.body.overlaps(special.body)) {
                special.action(game);
                Ball prev = game.ball.previousFrame(delta);
                if (!prev.body.overlaps(special.body) && game.ball.body.overlaps(special.body))
                    game.specialSoundPlay(special.type);
            }
            special.move(delta);
        }

        if (game.gameMap.brick_count == 0) {
            Gdx.app.log("ENDGAME", "You won");
            game.gameWon();
            game.setState(game.FINISH);
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
}



