package com.mygdx.brickbreaker;

import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.models.Brick;

/**
 * Created by vwraposo on 30/05/17.
 */

public class MapGenerator {

    public static Array<Array<Brick>> getMap(BrickBreaker game, int level) {
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

    private static Array<Array<Brick>> map1(BrickBreaker game) {
        Array<Array<Brick>> map = new Array<Array<Brick>>();

        int space = game.WIDTH / 18;
        int h = game.HEIGHT - space - Brick.height;
        for (int i = 0; i < 6; i++) {
            map.add(new Array<Brick>());
        }
        for (Array row : map){
            for (int i = 0; i < 7; i++) {
                Brick brick = new Brick(1);
                brick.body.x = i *(Brick.width + space) + space;
                brick.body.y = h;
                row.add(brick);
            }
            h -= Brick.height + space;
        }

        return map;
    }

    private static Array<Array<Brick>> map2(BrickBreaker game) {
        Array<Array<Brick>> map = new Array<Array<Brick>>();

        int space = game.WIDTH / 16;
        int h = game.HEIGHT - space - Brick.height / 2;
        for (int i = 0; i < 8; i++) {
            map.add(new Array<Brick>());
        }
        int k = 1;
        for (Array row : map){
            for (int i = 0; i < k; i++) {
                int lives = 1;
                if (i < 2)
                    lives = 3;
                else if (i < 4)
                    lives = 2;

                Brick brick = new Brick(lives);
                brick.body.x = i *(Brick.width / 2 + space) + space;
                brick.body.y = h;
                row.add(brick);
            }
            k++;
            h -= Brick.height / 2 + space;
        }

        return map;
    }
}
