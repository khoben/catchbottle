package com.khoben.cb.map;

import java.util.HashMap;

/**
 * Created by extle on 01.10.2017.
 */

public enum TileType {

    GRASS(1, true, "Grass"),
    DIRT(2, true, "Dirt"),
    SKY(3, false, "Sky"),
    LAVA(4, true, "Lava"),
    CLOUD(5, true, "Cloud"),
    STONE(6, true, "Stone"),
    BOTTLE(7,false,"Bottle");

    public static final int TILE_SIZE = 16;

    private int id;
    private boolean collidable;
    private String name;


    private TileType (int id, boolean collidable, String name) {
        this.id = id;
        this.collidable = collidable;
        this.name= name;

    }

    public int getId() {
        return id;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public String getName() {
        return name;
    }



    private static HashMap<Integer, TileType> tileMap;

    static {
        tileMap = new HashMap<Integer, TileType>();
        for (TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }

    public static TileType getTileTypeById (int id) {
        return tileMap.get(id);
    }

}
