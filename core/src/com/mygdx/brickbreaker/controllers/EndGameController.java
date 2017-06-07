package com.mygdx.brickbreaker.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.BrickBreaker;
import com.mygdx.brickbreaker.models.Brick;

/**
 * Created by vwraposo on 02/06/17.
 */

public class EndGameController {
    final BrickBreaker game;

    public Stage stage;
    private ImageButton home;
    private ImageButton replay;
    private GlyphLayout message;

    public EndGameController(BrickBreaker game) {
        // Locais
        this.game = game;
        // Strings
        message = new GlyphLayout();

        stage = new Stage(game.viewp, game.batch);
        setHomeButton();
        setReplayButton();
    }

    public void render(float delta) {
        game.batch.setColor(Color.LIGHT_GRAY);
        game.batch.begin();

        game.batch.draw(game.ball.image, game.ball.body.x, game.ball.body.y);
        game.batch.draw(game.platform.image, game.platform.body.x, game.platform.body.y);

        for (Brick brick : game.bricks)
            game.batch.draw(brick.getImage(), brick.body.x, brick.body.y, brick.body.width, brick.body.height);



        if (game.result())
            message.setText(game.font, "YOU WON!");
        else
            message.setText(game.font, "YOU LOST");
        game.font.draw(game.batch, message, game.WIDTH/2 - message.width/2, 3*game.HEIGHT/4 - message.height/2);


        game.batch.end();

        stage.draw();
    }

    private void setHomeButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("home_up.png"))));
        style.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("home_down.png"))));

        home = new ImageButton(style);
        home.setPosition(game.WIDTH / 2 - 5*home.getWidth() / 4, 3*game.HEIGHT / 8 - home.getHeight() / 2);
        home.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON", "down");
                Gdx.input.vibrate(20);
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON", "up");
                game.batch.setColor(Color.LIGHT_GRAY);
                game.startGame(0);
                game.setState(game.MENU);
            }
        });
        stage.addActor(home);
    }

    private void setReplayButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("replay_up.png"))));
        style.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("replay_down.png"))));

        replay = new ImageButton(style);
        replay.setPosition(game.WIDTH / 2 + replay.getWidth() / 4, 3*game.HEIGHT / 8 - replay.getHeight() / 2);
        replay.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON", "down");
                Gdx.input.vibrate(20);
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("BUTTON", "up");
                game.batch.setColor(Color.WHITE);
                game.startGame(game.maps.getLevel());
                game.setState(game.GAME);
            }
        });
        stage.addActor(replay);
    }


}
