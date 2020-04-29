package com.khoben.cb.patterns.PoolObjects;
import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;
import com.khoben.cb.map.GameMap;

import java.util.Iterator;
import java.util.Set;

public abstract class PoolObjects<T extends BottleFromSky>{
    public Set<T> available;
    public Set<T> inUse;

    protected abstract T create(GameMap map);

    public T checkOut(GameMap map) {
        if (available.isEmpty()) {
            available.add(create(map));
        }
        Iterator<T> iterator = available.iterator();
        T instance = iterator.next();
        available.remove(instance);
        inUse.add(instance);
//        System.out.println("--------");
//        System.out.println(available.size());
//        System.out.println(inUse.size());
        return instance;
    }

    public synchronized void checkIn(T instance) {
        instance.resetPos();
        available.add(instance);
        inUse.remove(instance);
    }
}
