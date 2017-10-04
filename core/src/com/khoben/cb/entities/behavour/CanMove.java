package com.khoben.cb.entities.behavour;

import com.khoben.cb.entities.Entity;
import com.khoben.cb.map.GameMap;

/**
 * Created by extle on 02.10.2017.
 */

public class CanMove implements IMoveAction {
    public void move(Entity e, GameMap map, float amount)
    {
        float newX = e.getPos().x + amount;
        if (!map.doesRectCollideWithMap(newX, e.getPos().y, e.getWidth(), e.getHeight()))
            e.setPosX(newX);
    }
}

