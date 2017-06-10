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
    private int total_bricks;

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
        this.brick_count = this.total_bricks;
        for (Brick each: this.bricks) {
            each.resetLives();
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

        this.total_bricks = this.brick_count;
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
        this.total_bricks = this.brick_count;
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
        this.total_bricks = this.brick_count;
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
        this.total_bricks = this.brick_count;
    }

    private void map4() {
        Brick brick;

        float ini = gameWidth / 4;
        for (int i = 0; i < 7; i++) {
            brick = novoBrick(3, Brick.width, Brick.height,
                    new Vector2(0,0),
                    new Vector2(0,gameWidth),
                    new Vector2(0,gameHeight));
            brick.body.x = ini + i*Brick.width;
            brick.body.y = gameHeight/2 - 100;

            bricks.add(brick);

            brick = novoBrick(3, Brick.width, Brick.height,
                    new Vector2(0,0),
                    new Vector2(0,gameWidth),
                    new Vector2(0,gameHeight));
            brick.body.x = ini + i*Brick.width;
            brick.body.y = gameHeight;

            bricks.add(brick);

            if (i < 6) {
                brick = novoBrick(3, Brick.height, Brick.width,
                        new Vector2(0, 0),
                        new Vector2(0, gameWidth),
                        new Vector2(0, gameHeight));
                brick.body.x = gameWidth - brick.body.width;
                brick.body.y = gameHeight - Brick.height - (i + 1) * brick.body.height;

                bricks.add(brick);
            }
        }

        brick = novoBrick(2, Brick.width, Brick.height,
                new Vector2(0, 500),
                new Vector2(0, gameWidth),
                new Vector2(gameHeight/2 - 100 + Brick.height, gameHeight - Brick.height));

        brick.body.x = ini;
        brick.body.y = gameHeight/2 - 100 + Brick.height;

        bricks.add(brick);

        brick = novoBrick(2, Brick.width, Brick.height,
                new Vector2(0, -500),
                new Vector2(0, gameWidth),
                new Vector2(gameHeight/2 - 100 + Brick.height, gameHeight - Brick.height));

        brick.body.x = gameWidth - Brick.height - Brick.width;
        brick.body.y = gameHeight - 2*Brick.height;

        bricks.add(brick);


        brick = novoBrick(2, Brick.width, Brick.height,
                new Vector2(370, 0),
                new Vector2(ini, gameWidth - Brick.height),
                new Vector2(0, gameHeight));

        brick.body.x = ini;
        brick.body.y = gameHeight - 2*Brick.height;

        bricks.add(brick);

        brick = novoBrick(2, Brick.width, Brick.height,
                new Vector2(-370, 0),
                new Vector2(ini, gameWidth - Brick.height),
                new Vector2(0, gameHeight));

        brick.body.x = gameWidth - Brick.height - Brick.width;
        brick.body.y = gameHeight/2 - 100 + Brick.height;

        bricks.add(brick);

        brick = novoBrick(1, Brick.width, Brick.width,
                new Vector2(0, 0),
                new Vector2(0, gameWidth),
                new Vector2(0, gameHeight));

        brick.body.x = 5*gameWidth/8 - brick.body.width;
        brick.body.y = 3*gameHeight/4;
        bricks.add(brick);

        brick = novoBrick(1, Brick.width, Brick.width,
                new Vector2(0, 0),
                new Vector2(0, gameWidth),
                new Vector2(0, gameHeight));
        brick.body.x = 5*gameWidth/8;
        brick.body.y = 3*gameHeight/4;
        bricks.add(brick);

        brick = novoBrick(1, Brick.width, Brick.width,
                new Vector2(0, 0),
                new Vector2(0, gameWidth),
                new Vector2(0, gameHeight));
        brick.body.x = 5*gameWidth/8 - brick.body.width;
        brick.body.y = 3*gameHeight/4 - brick.body.height;
        bricks.add(brick);

        brick = novoBrick(1, Brick.width, Brick.width,
                new Vector2(0, 0),
                new Vector2(0, gameWidth),
                new Vector2(0, gameHeight));
        brick.body.x = 5*gameWidth/8;
        brick.body.y = 3*gameHeight/4 - brick.body.height;
        bricks.add(brick);


        Special booster = new Special(Special.BOOSTER, 200, 400,
                new Vector2(0,600),
                new Vector2(0,gameWidth),
                new Vector2(gameHeight/2 - 200,gameHeight - 200));
        booster.body.x = booster.body.width/2;
        booster.body.y = gameHeight / 2;

        specials.add(booster);

        booster = new Special(Special.BOOSTER, 2*Brick.width+50, 2*Brick.width + 50,
                new Vector2(0,0),
                new Vector2(0,gameWidth),
                new Vector2(0, gameHeight));
        booster.body.x = 5*gameWidth / 8 - booster.body.width / 2;
        booster.body.y = 3*gameHeight/4 - booster.body.height / 2;

        specials.add(booster);

        this.total_bricks = this.brick_count;
    }
}
