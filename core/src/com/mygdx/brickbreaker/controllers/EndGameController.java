package com.mygdx.brickbreaker.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.BrickBreaker;
import com.mygdx.brickbreaker.models.Brick;

/**
 * Created by vwraposo on 02/06/17.
 */

public class EndGameController {
    final BrickBreaker game;
    private com.mygdx.brickbreaker.models.Ball ball;
    private com.mygdx.brickbreaker.models.Body platform;
    private Array<Brick> bricks;

    private GlyphLayout message;
    public EndGameController(BrickBreaker game) {
        // Locais
        this.game = game;
        this.ball = game.ball;
        this.platform = game.platform;
        this.bricks = game.bricks;

        // Strings
        message = new GlyphLayout();
    }

    public void render(float delta) {
        game.batch.setColor(Color.LIGHT_GRAY);
        game.batch.begin();

        game.batch.draw(game.ball.image, game.ball.body.x, game.ball.body.y);
        game.batch.draw(game.platform.image, game.platform.body.x, game.platform.body.y);

        for (Brick brick : bricks) {
                game.batch.draw(brick.getImage(), brick.body.x, brick.body.y);
        }


        if (game.result())
            message.setText(game.font, "You Won!");
        else
            message.setText(game.font, "You Lost");
        game.font.draw(game.batch, message, game.WIDTH/2 - message.width/2, 3*game.HEIGHT/4 - message.height/2);



        game.batch.end();
    }


}
