package com.mygdx.brickbreaker;

import com.badlogic.gdx.utils.Array;
import com.mygdx.brickbreaker.Brick;

/**
 * Created by vwraposo on 30/05/17.
 */

public class MapGenerator {

    public static Array<Array<Brick>> getMap(BrickBreaker game, int level) {
        switch (level) {
            case 1:
                return map1(game);
            default:
                //error
                return null;
        }
    }

    private static Array<Array<Brick>> map1(BrickBreaker game) {
        Array<Array<Brick>> map = new Array<Array<Brick>>();

        int space = game.WIDTH / 16;
        int h = game.HEIGHT - space - Brick.height;
        for (int i = 0; i < 6; i++) {
            map.add(new Array<Brick>());
        }
        for (Array row : map){
            for (int i = 0; i < 8; i++) {
                Brick brick = new Brick(1);
                brick.body.x = i *(Brick.width + space) + space;
                brick.body.y = h;
                row.add(brick);
            }
            h -= Brick.height + space;
        }

        return map;
    }
}
