package com.khoben.cb.entities.behavour;

import com.khoben.cb.entities.Entity;
import com.khoben.cb.map.GameMap;

/**
 * Created by extle on 02.10.2017.
 */

public interface IMoveAction {
    public void move(Entity e, GameMap map, float amount);
}
