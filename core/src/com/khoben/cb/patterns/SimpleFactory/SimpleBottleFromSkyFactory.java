package com.khoben.cb.patterns.SimpleFactory;

import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.PoolObjects.CommonBottlePool;
import com.khoben.cb.patterns.PoolObjects.DeadlyBottlePool;
import com.khoben.cb.patterns.PoolObjects.PoolObjects;

/**
 * Created by extle on 28.11.2017.
 */

public class SimpleBottleFromSkyFactory {

    PoolObjects<BottleFromSky> commonBottlePoolObjects;
    PoolObjects<BottleFromSky> deadlyBottlePoolObjects;

    public SimpleBottleFromSkyFactory(){
        commonBottlePoolObjects = CommonBottlePool.getInstance();
        deadlyBottlePoolObjects = DeadlyBottlePool.getInstance();
    }

    public BottleFromSky createBottle(Class o, GameMap m) {
        switch (o.getSimpleName()) {
            case "CommonBottle":
                return commonBottlePoolObjects.checkOut(m);
            case "DeadlyBottle":
                return deadlyBottlePoolObjects.checkOut(m);
        }
        return null;
    }

    public PoolObjects<BottleFromSky> getPool(Class o) {
        String className = o.getSimpleName().toString();
        if (className.equals("DeadlyBottle"))
            return deadlyBottlePoolObjects;
        else
        if (className.equals("CommonBottle"))
            return commonBottlePoolObjects;
        return null;
    }

}
