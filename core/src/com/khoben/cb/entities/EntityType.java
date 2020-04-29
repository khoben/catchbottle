package com.khoben.cb.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.khoben.cb.entities.EntitiesStorage.EntitySnapshot;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.map.GameMap;

import java.util.HashMap;

/**
 * Created by extle on 01.10.2017.
 */

@SuppressWarnings("rawtypes")
public enum EntityType {

    PLAYER("player", com.khoben.cb.entities.players.Player.class, 80, 110, 40),
    MIDBOTTLE("midbottle", com.khoben.cb.entities.bottles.MidBottle.class, 14, 32, 40),
    SMALLBOTTLE("smallbottle", com.khoben.cb.entities.bottles.SmallBottle.class, 7, 16, 40),
    BIGBOTTLE("bigbottle", com.khoben.cb.entities.bottles.BigBottle.class, 28,64, 40),
    MIDBOTTLEFROMSKY("midbottlefromsky", com.khoben.cb.entities.BottlesFromSky.BottleFromSky.class, 14, 32, 40),
    DEADLYBOTTLE("commonbottle", com.khoben.cb.entities.BottlesFromSky.DeadlyBottle.class, 14, 32, 40),
    COMMONBOTTLE("deadlybottle", com.khoben.cb.entities.BottlesFromSky.CommonBottle.class, 14, 32, 40);

    private String id;
    private Class loaderClass;
    private int width, height;
    private float weight;

    EntityType(String id, Class loaderClass, int width, int height, float weight) {
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

    public static Entity createEntityUsingSnapshot (EntitySnapshot entitySnapshot, GameMap map) {
        EntityType type = entityTypes.get(entitySnapshot.getType());

        try {
            Entity entity = (Entity) ClassReflection.newInstance(type.loaderClass);
            entity.create(entitySnapshot, type, map);
            return entity;
        } catch (ReflectionException e) {
            Gdx.app.error("Entity Loader", "Could not load entity of type " + type.id);
            return null;
        }
    }

    private static HashMap<String, EntityType> entityTypes;

    static {
        entityTypes = new HashMap<String, EntityType>();
        for (EntityType type : EntityType.values())
            entityTypes.put(type.id, type);
    }

}
