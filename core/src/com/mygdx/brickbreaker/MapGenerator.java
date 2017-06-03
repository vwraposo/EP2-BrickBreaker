package com.mygdx.brickbreaker;

import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.models.Brick;

/**
 * Created by vwraposo on 30/05/17.
 */

public class MapGenerator {

    public static Array<Brick> getMap(BrickBreaker game, int level) {
        switch (level) {
            case 1:
                return map1(game);
            case 2:
                return map2(game);
            case 3:
                return map3(game);
            default:
                //error
                return null;
        }
    }

    private static Array<Brick> map1(BrickBreaker game) {
        Array<Brick> map = new Array<Brick>();

        int space = game.WIDTH / 38;
        int h = game.HEIGHT - space - Brick.height;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
                    Brick brick = new Brick(1, Brick.width, Brick.height);
                    brick.body.x = j *(brick.body.width + space) + space;
                    brick.body.y = h;
                    map.add(brick);
            }
            h -= Brick.height + space;
        }

        return map;
    }

    private static Array<Brick> map2(BrickBreaker game) {
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
                Brick brick = new Brick(lives, Brick.width, Brick.height);
                brick.body.x = j *(brick.body.width + space) + space;
                brick.body.y = h;
                map.add(brick);
            }
            h -= Brick.height + space;
        }

        return map;
    }

    private static Array<Brick> map3(BrickBreaker game) {
        Array<Brick> map = new Array<Brick>();

        int space = game.WIDTH / 38;
        int h = game.HEIGHT;
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 1) {
                h -= 2*Brick.height + space;
                for (int j = 0; j < 6; j++) {
                    Brick brick = new Brick(2, 2* Brick.height, 2*Brick.height);
                    brick.body.x = j * (brick.body.width + space) + 2*space;
                    brick.body.y = h;
                    map.add(brick);

                }
            } else if (i % 3 == 2) {
                h -= Brick.height + space;
                Brick brick = new Brick(3, game.WIDTH - space, Brick.height);
                brick.body.x = space;
                brick.body.y = h;
                map.add(brick);
            } else {
                h -= Brick.height + space;
                for (int j = 0; j < 6; j++) {
                    Brick brick = new Brick(1, Brick.width, Brick.height);
                    brick.body.x = j * (brick.body.width + space) + space;
                    brick.body.y = h;
                    map.add(brick);
                }
            }
        }

        return map;
    }

}
