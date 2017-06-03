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
                    Brick brick = new Brick(1);
                    brick.body.x = j *(Brick.width + space) + space;
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
                Brick brick = new Brick(lives);
                brick.body.x = j *(Brick.width + space) + space;
                brick.body.y = h;
                map.add(brick);
            }
            h -= Brick.height + space;
        }

        return map;
    }
}
