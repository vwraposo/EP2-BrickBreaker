package com.mygdx.brickbreaker.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.BrickBreaker;
import com.mygdx.brickbreaker.GameScreen;
import com.mygdx.brickbreaker.MapGenerator;
import com.mygdx.brickbreaker.models.Body;
import com.mygdx.brickbreaker.models.Brick;

/**
 * Created by vwraposo on 01/06/17.
 */

public class MenuController {
    final BrickBreaker game;

    public MenuController (BrickBreaker game) {
        this.game = game;

        game.ball = new com.mygdx.brickbreaker.models.Ball("ball.png");
        game.platform = new Body("bar.png");

        game.ball.body.x = game.WIDTH / 2 - game.ball.body.width / 2;
        game.ball.body.y = game.HEIGHT / 8 + game.platform.body.height / 2;

        game.platform.body.x = game.WIDTH / 2 - game.platform.body.width / 2;
        game.platform.body.y = game.HEIGHT / 8 - game.platform.body.height / 2;

        game.bricks = MapGenerator.getMap(game, 1);

        //Fundo Cinza
        game.batch.setColor(Color.LIGHT_GRAY);
    }

    public void render(float delta) {

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
            game.batch.setColor(Color.WHITE);
            game.setState(game.GAME);
        }
    }


}
