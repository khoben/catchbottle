package com.khoben.cb.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.khoben.cb.map.GameMap;

import java.util.HashMap;

/**
 * Created by extle on 01.10.2017.
 */

@SuppressWarnings("rawtypes")
public enum EntityType {

    PLAYER("player", Player.class, 49, 73, 40),
    BOTTLE("bottle", Bottle.class, 14, 32, 40);

    private String id;
    private Class loaderClass;
    private int width, height;
    private float weight;

    private EntityType(String id, Class loaderClass, int width, int height, float weight) {
        this.id = id;
        this.loaderClass = loaderClass;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    private static HashMap<String, EntityType> entityTypes;

    static {
        entityTypes = new HashMap<String, EntityType>();
        for (EntityType type : EntityType.values())
            entityTypes.put(type.id, type);
    }

}
