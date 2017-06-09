package com.mygdx.brickbreaker.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;
import com.mygdx.brickbreaker.BrickBreaker;

/**
 * Created by vwraposo on 06/06/17.
 */

public class Maps {
    public static Integer total = 5;
    private BrickBreaker game;
    private GameMap[] maps;
    private Integer level;

    public Maps(BrickBreaker game) {
        this.game = game;
        this.level = 0;
        maps = new GameMap[total];
        for (int i = 0; i < total; i++)
            this.maps[i] = new GameMap(i,game.WIDTH,game.HEIGHT);
    }

    public void restartLevel(int level) {
        this.maps[level].resetMap();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GameMap getMap() {
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
<<<<<<< HEAD
}
=======
}
>>>>>>> b7168da65d69d8d830e4c9c57aa65c749e413ba4
