package com.mygdx.brickbreaker.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private static GlyphLayout title1;
    private static GlyphLayout title2;
    public Stage stage;
    private ImageButton button;

    public MenuController (BrickBreaker game) {
        this.game = game;
        game.startGame();
        //Fundo Cinza
        game.batch.setColor(Color.LIGHT_GRAY);

        // Strings
        title1 = new GlyphLayout();
        title1.setText(game.font, "BRICK");
        title2 = new GlyphLayout(game.font, "BREAKER");

        // Menu
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        setPlayButton();
    }

    public void render(float delta) {
        game.batch.begin();

        game.batch.draw(game.ball.image, game.ball.body.x, game.ball.body.y);
        game.batch.draw(game.platform.image, game.platform.body.x, game.platform.body.y);

        for (Brick brick : game.bricks)
            game.batch.draw(brick.getImage(), brick.body.x, brick.body.y, brick.body.width, brick.body.height);


        game.font.draw(game.batch, title1, game.WIDTH/2 - title1.width/2, game.HEIGHT - 2*title1.height);
        game.font.draw(game.batch, title2, game.WIDTH/2 - title2.width/2, game.HEIGHT - 2*title1.height
                - game.HEIGHT/38 - title2.height);

        game.batch.end();

        stage.draw();
    }

    private void setPlayButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("play_up.png"))));
        style.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("play_down.png"))));

        button = new ImageButton(style);
        button.setPosition(game.WIDTH / 2 - button.getWidth() / 2, 3*game.HEIGHT / 8 - button.getHeight() / 2);
        button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON", "down");
                Gdx.input.vibrate(20);
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON", "up");
                game.batch.setColor(Color.WHITE);
                game.setState(game.GAME);
            }
        });
        stage.addActor(button);
    }



}
