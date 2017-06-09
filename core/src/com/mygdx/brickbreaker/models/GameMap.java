package com.mygdx.brickbreaker.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by joaofran on 09/06/17.
 */

public class GameMap {

    public Array<Brick> bricks;
    public Array<Special> specials;
    public int brick_count;

    private int gameWidth;
    private int gameHeight;

    public GameMap (int level, int gameWidth, int gameHeight) {
        this.brick_count = 0;
        this.bricks = new Array<Brick>();
        this.specials = new Array<Special>();

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        switch (level) {
            case 1: map1(); break;
            case 2: map2(); break;
            case 3: map3(); break;
            case 4: map4(); break;
            default: map0();

//            razoável?
//            case 0: map0(); break;
//            default: throw new IllegalArgumentException();
        }
    }

    private Brick novoBrick (int lives, float w, float h, Vector2 vel, Vector2 limX, Vector2 limY) {
        this.brick_count++;
        return new Brick(lives,w,h,vel,limX,limY);
    }

    public void resetMap () {
        for (Brick each: this.bricks) {
            each.resetLives();
            if (!each.is_visible()) this.brick_count++;
            each.resetVisibility();
        }
    }

    private void map0() {
        int space = gameWidth / 38;
        int h = gameHeight - space - Brick.height;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                Brick brick = novoBrick(1, Brick.width, Brick.height, new Vector2(0, 0),
                        new Vector2(0, gameWidth), new Vector2(0, gameHeight));
                brick.body.x = j *(Brick.width + space) + space;
                brick.body.y = h;
                bricks.add(brick);
            }
            h -= Brick.height + space;
        }
    }

    private void map1() {
        int space = gameWidth / 36;
        int h = gameHeight - space - Brick.height;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < i+1; j++) {
                int lives = 1;
                if (j < 2)
                    lives = 3;
                else if (j < 4)
                    lives = 2;
                Brick brick = novoBrick(lives, Brick.width, Brick.height,
                        new Vector2(0, 0), new Vector2(0, gameWidth), new Vector2(0, gameHeight));
                brick.body.x = j *(brick.body.width + space) + space;
                brick.body.y = h;
                bricks.add(brick);
            }
            h -= Brick.height + space;
        }
    }

    private void map2() {
        int space = gameWidth / 38;
        int h = gameHeight;

        for (int i = 0; i < 9; i++) {
            if (i % 3 == 1) {
                h -= 2*Brick.height + space;
                for (int j = 0; j < 6; j++) {
                    Brick brick = novoBrick(2, 2* Brick.height, 2*Brick.height,
                            new Vector2(0, 0), new Vector2(0, gameWidth), new Vector2(0, gameHeight));
                    brick.body.x = j * (brick.body.width + space) + 2*space;
                    brick.body.y = h;
                    bricks.add(brick);
                }
            } else if (i % 3 == 2) {
                h -= Brick.height + space;
                Brick brick = novoBrick(3, gameWidth - space, Brick.height,
                        new Vector2(0, 0), new Vector2(0, gameWidth), new Vector2(0, gameHeight));
                brick.body.x = space;
                brick.body.y = h;
                bricks.add(brick);
            } else {
                h -= Brick.height + space;
                for (int j = 0; j < 6; j++) {
                    Brick brick = novoBrick(1, Brick.width, Brick.height,
                            new Vector2(0, 0), new Vector2(0, gameWidth), new Vector2(0, gameHeight));
                    brick.body.x = j * (brick.body.width + space) + space;
                    brick.body.y = h;
                    bricks.add(brick);
                }
            }
        }
    }

    private void map3() {
        int espaco_aereo = (int) (0.7 * gameHeight);
        int largura_banda = (int) (1.2 * Brick.height);
        int n_bandas = (espaco_aereo / largura_banda);
        int offset = gameHeight - espaco_aereo + (int) (largura_banda / 2.0);

        for (int i = 1; i <= n_bandas; i++) {
            Brick brick = novoBrick(1 + (i % 3), Brick.width, Brick.height,
                    new Vector2(2000/(2 + (i % 4)), 0), // velocidade
                    new Vector2(gameWidth / 10, 9 * gameWidth / 10), // limites horizontais
                    new Vector2(0, gameHeight)); // limites verticais
            brick.body.x = gameWidth / 2;
            brick.body.y = offset + (i-1) * largura_banda;

            bricks.add(brick);
        }
    }

    private void map4() {
        Brick brick = novoBrick(1, Brick.width, Brick.height,
                new Vector2(0,0),
                new Vector2(0,gameWidth),
                new Vector2(0,gameHeight));
        brick.body.x = 0;
        brick.body.y = 0;

        bricks.add(brick);

        Special bumper = new Special(Special.BUMPER, 100,
                new Vector2(0,0),
                new Vector2(0,gameWidth),
                new Vector2(0,gameHeight));
        bumper.body.x = (gameWidth - bumper.width)/2;
        bumper.body.y = (gameHeight - bumper.height)/2;

        specials.add(bumper);
    }
}
