package com.khoben.cb.patterns.Flyweight;

import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.bottles.BigBottle;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.bottles.MidBottle;
import com.khoben.cb.entities.bottles.SmallBottle;
import com.khoben.cb.map.GameMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by extle on 19.11.2017.
 */

public class FlyweightFactory {
    private static Map<String, Bottle> hashBottle;
    private GameMap map;
    private static final Vector2 defaultPos = new Vector2(0, 0);

    public FlyweightFactory(GameMap m) {
        hashBottle = new HashMap<String, Bottle>();
        map = m;
    }

    public Bottle getConcreteBottle(String typeBottle) {
        Bottle bottle = hashBottle.get(typeBottle);

        if (bottle == null) {
            switch (typeBottle) {
                case "MidBottle":
                    bottle = new MidBottle(defaultPos, map);
                    break;
                case "SmallBottle":
                    bottle = new SmallBottle(defaultPos, map);
            }
            hashBottle.put(typeBottle, bottle);
        }
        return bottle;
    }

}
