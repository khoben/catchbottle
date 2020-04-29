package com.khoben.cb.patterns.AbstractFactory;

import com.khoben.cb.entities.bottles.IBottle;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.AbstractFactory.NPC.NPC;

public interface IAbstractFactory {
    NPC createNPC(GameMap map);
    IBottle createBottle(GameMap map);
}
