package com.mygdx.brickbreaker.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.BrickBreaker;

/**
 * Created by vwraposo on 06/06/17.
 */

public class Maps {
    public static Integer total = 4;
    private BrickBreaker game;
    private Array<Brick>[] maps;
    private Integer level;

    public Maps(BrickBreaker game, int level) {
        this.game = game;
        this.level = level;
        maps = new Array[total];
        for (int i = 0; i < total; i++)
            maps[i] = newMap(i);
    }

    public Array<Brick> getMap() {
        return maps[level];
    }

    public Integer getLevel() {
        return level;
    }

    public void nextLevel() {
        level = (level + 1) % total;
    }

    public void previousLevel() {
        level = level - 1 % total;
        if (level < 0) level = total - 1;
    }


    private Array<Brick> newMap(int level) {
        switch (level) {
            case 0:
                return map0();
            case 1:
                return map1();
            case 2:
                return map2();
            case 3:
                return map3();
            default:
                //error
                return null;
        }
    }

    private Array<Brick> map0() {
        Array<Brick> map = new Array<Brick>();

        int space = game.WIDTH / 38;
        int h = game.HEIGHT - space - Brick.height;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
                Brick brick = new Brick(1, Brick.width, Brick.height, new Vector2(0, 0),
                        new Vector2(0, game.WIDTH), new Vector2(0, game.HEIGHT));
                brick.body.x = j *(Brick.width + space) + space;;
                brick.body.y = h;
                map.add(brick);
            }
            h -= Brick.height + space;
        }

        return map;
    }

    // Tipos diferentes
    private Array<Brick> map1() {
        Array<Brick> map = new Array<Brick>();

        int space = game.WIDTH / 36;
        int h = game.HEIGHT - space - Brick.height;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < i+1; j++) {
                int lives = 1;
                if (j < 2)
                    lives = 3;
                else if (j < 4)
                    lives = 2;
                Brick brick = new Brick(lives, Brick.width, Brick.height,
                        new Vector2(0, 0), new Vector2(0, game.WIDTH), new Vector2(0, game.HEIGHT));
                brick.body.x = j *(brick.body.width + space) + space;
                brick.body.y = h;
                map.add(brick);
            }
            h -= Brick.height + space;
        }

        return map;
    }

    // Formatos diferetnes
    private Array<Brick> map2() {
        Array<Brick> map = new Array<Brick>();

        int space = game.WIDTH / 38;
        int h = game.HEIGHT;
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 1) {
                h -= 2*Brick.height + space;
                for (int j = 0; j < 6; j++) {
                    Brick brick = new Brick(2, 2* Brick.height, 2*Brick.height,
                        new Vector2(0, 0), new Vector2(0, game.WIDTH), new Vector2(0, game.HEIGHT));
                    brick.body.x = j * (brick.body.width + space) + 2*space;
                    brick.body.y = h;
                    map.add(brick);
                }
            } else if (i % 3 == 2) {
                h -= Brick.height + space;
                Brick brick = new Brick(3, game.WIDTH - space, Brick.height,
                        new Vector2(0, 0), new Vector2(0, game.WIDTH), new Vector2(0, game.HEIGHT));
                brick.body.x = space;
                brick.body.y = h;
                map.add(brick);
            } else {
                h -= Brick.height + space;
                for (int j = 0; j < 6; j++) {
                    Brick brick = new Brick(1, Brick.width, Brick.height,
                            new Vector2(0, 0), new Vector2(0, game.WIDTH), new Vector2(0, game.HEIGHT));
                    brick.body.x = j * (brick.body.width + space) + space;
                    brick.body.y = h;
                    map.add(brick);
                }
            }
        }

        return map;
    }

    // Movimento
    private Array<Brick> map3() {
        Array<Brick> map = new Array<Brick>();

        int espaco_aereo = (int) (0.7 * game.HEIGHT);
        int largura_banda = (int) (1.2 * Brick.height);
        int n_bandas = (espaco_aereo / largura_banda);
        int offset = game.HEIGHT - espaco_aereo + (int) (largura_banda / 2.0);

        for (int i = 1; i <= n_bandas; i++) {
            Brick brick = new Brick(1 + (i % 3), Brick.width, Brick.height,
                    new Vector2(2000/(2 + (i % 4)), 0), // velocidade
                    new Vector2(game.WIDTH / 10, 9 * game.WIDTH / 10), // limites horizontais
                    new Vector2(0, game.HEIGHT)); // limites verticais
            brick.body.x = game.WIDTH / 2;
            brick.body.y = offset + (i-1) * largura_banda;

            map.add(brick);
        }

        return map;
    }

}
