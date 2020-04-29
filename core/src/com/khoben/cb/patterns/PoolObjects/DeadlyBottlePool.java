package com.khoben.cb.patterns.PoolObjects;

import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;
import com.khoben.cb.entities.BottlesFromSky.CommonBottle;
import com.khoben.cb.entities.BottlesFromSky.DeadlyBottle;
import com.khoben.cb.map.GameMap;

import java.util.HashSet;


public class DeadlyBottlePool extends PoolObjects<BottleFromSky> {

    private static volatile DeadlyBottlePool singletonBottle = null;

    public static synchronized DeadlyBottlePool getInstance() {
        if (singletonBottle == null) {
            singletonBottle = new DeadlyBottlePool();
        }
        return singletonBottle;
    }

    @Override
    protected DeadlyBottle create(GameMap map) {
        return new DeadlyBottle(map);
    }
    private DeadlyBottlePool(){
        available = new HashSet<>();
        inUse = new HashSet<>();
    }
}
