package com.khoben.cb.map;

/**
 * Created by extle on 01.10.2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.Random;

public class CustomGameMapLoader {

    private static Json json = new Json();
    private static final int SIZE = 100;

    public static CustomGameMapData loadMap (String id) {

        FileHandle file = Gdx.files.local("maps/" + id + ".map");

        CustomGameMapData data = json.fromJson(CustomGameMapData.class, file.readString());
        return data;
    }

    public static void saveMap (String id, int[][][] map) {
        CustomGameMapData data = new CustomGameMapData();
        data.id = id;
        data.name = id;
        data.map = map;

        Gdx.files.internal("maps/").file().mkdirs();
        FileHandle file = Gdx.files.local("maps/" + id + ".map");
        file.writeString(json.prettyPrint(data), false);
    }

    public static void saveMap (CustomGameMapData data) {
        Gdx.files.internal("maps/").file().mkdirs();
        FileHandle file = Gdx.files.local("maps/" + data.id+ ".map");
        file.writeString(json.prettyPrint(data), false);
    }

    public static CustomGameMapData createTiledMap (String id) {
        CustomGameMapData mapData = new CustomGameMapData();
        mapData.id = id;
        mapData.name = id;
        mapData.map = new int[2][SIZE][SIZE];

        Random random = new Random();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                mapData.map[0][row][col] = TileType.SKY.getId();

                if (row > SIZE - 3 - 1) {
                    mapData.map[1][row][col] = TileType.SKY.getId();
                } else if (row > SIZE - 20) {
                    mapData.map[1][row][col] = TileType.STONE.getId();
                } else if (row > SIZE - 25) {
                    mapData.map[1][row][col] = TileType.STONE.getId();
                } else if (row > SIZE - 30) {
                    mapData.map[1][row][col] = TileType.STONE.getId();
                } else {
                    if (random.nextInt(50) == 0)//1 chance out of 50 of being a cloud
                        mapData.map[1][row][col] = TileType.BOTTLE.getId();
                }
            }
        }

        return mapData;
    }


}
