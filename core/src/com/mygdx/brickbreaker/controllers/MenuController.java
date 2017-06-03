package com.mygdx.brickbreaker.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private static GlyphLayout title;
    private static GlyphLayout phrase;
    private Stage stage;
    TextButton button;
    TextButton.TextButtonStyle textButtonStyle;

    public MenuController (BrickBreaker game) {
        this.game = game;

        game.ball = new com.mygdx.brickbreaker.models.Ball("ball.png");
        game.platform = new Body("bar.png");

        game.ball.body.x = game.WIDTH / 2 - game.ball.body.width / 2;
        game.ball.body.y = game.HEIGHT / 8 + game.platform.body.height / 2;

        game.platform.body.x = game.WIDTH / 2 - game.platform.body.width / 2;
        game.platform.body.y = game.HEIGHT / 8 - game.platform.body.height / 2;

        //TODO : Refatorar e transformar o mapa em um modelo, alem disso pensar no melhor jeito de salvar
        game.bricks = MapGenerator.getMap(game, 1);

        //Fundo Cinza
        game.batch.setColor(Color.LIGHT_GRAY);

        // Strings
        title = new GlyphLayout();
        title.setText(game.font, "Brick Breaker");

        phrase = new GlyphLayout();
        phrase.setText(game.font, "Tap anywhere to begin!");

        // Menu
        Gdx.input.setInputProcessor(stage);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.font;
        button = new TextButton("Button1", textButtonStyle);
    }

    public void render(float delta) {
        game.batch.begin();

        game.batch.draw(game.ball.image, game.ball.body.x, game.ball.body.y);
        game.batch.draw(game.platform.image, game.platform.body.x, game.platform.body.y);

        for (Array row : game.bricks)
            for (Object b : row) {
                com.mygdx.brickbreaker.models.Brick brick = (Brick) b;
                game.batch.draw(brick.getImage(), brick.body.x, brick.body.y);
            }


        game.font.draw(game.batch, title, game.WIDTH/2 - title.width/2, 3*game.HEIGHT/4 - title.height/2);
        game.font.draw(game.batch, phrase, game.WIDTH/2 - phrase.width/2  , 3*game.HEIGHT/8 - phrase.height/2);


        game.batch.end();

        // Choose mode
        game.setMode(game.MODE_1);
        if (Gdx.input.isTouched()) {
            game.batch.setColor(Color.WHITE);
            game.setState(game.GAME);
        }
    }


}
