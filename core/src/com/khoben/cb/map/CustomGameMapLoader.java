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

    public static CustomGameMapData loadMap (String id, String name) {

        FileHandle file = Gdx.files.internal("maps/" + id + ".map");

        CustomGameMapData data = json.fromJson(CustomGameMapData.class, file.readString());
        return data;
    }


}
