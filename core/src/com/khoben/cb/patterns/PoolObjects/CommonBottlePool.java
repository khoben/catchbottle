package com.khoben.cb.patterns.PoolObjects;

import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;
import com.khoben.cb.entities.BottlesFromSky.CommonBottle;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.GameMap;

import java.util.HashSet;

public class CommonBottlePool extends PoolObjects<BottleFromSky> {

    private static volatile CommonBottlePool singletonBottle = null;

    public static CommonBottlePool getInstance() {
        if (singletonBottle == null) {
            singletonBottle = new CommonBottlePool();
        }
        return singletonBottle;
    }

    @Override
    protected CommonBottle create(GameMap map) {
        return new CommonBottle(map);
    }

    private CommonBottlePool(){
        available = new HashSet<>();
        inUse = new HashSet<>();
    }
}
