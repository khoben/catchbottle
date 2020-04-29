package com.khoben.cb.patterns.AbstractFactory;

import com.khoben.cb.entities.bottles.IBottle;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.AbstractFactory.Bottle.EnemyBottle;
import com.khoben.cb.patterns.AbstractFactory.NPC.EnemyNPC;
import com.khoben.cb.patterns.AbstractFactory.NPC.NPC;

public class EnemyFactory implements IAbstractFactory {
    @Override
    public NPC createNPC(GameMap map) {
        return new EnemyNPC(map);
    }

    @Override
    public IBottle createBottle(GameMap map) {
        return new EnemyBottle(map);
    }
}
