package com.khoben.cb.map;

import java.util.HashMap;

/**
 * Created by extle on 01.10.2017.
 */

public enum TileType {

    SKY(3, false, "Sky"),
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
