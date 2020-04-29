package com.khoben.cb.patterns.AbstractFactory;


import com.khoben.cb.entities.bottles.IBottle;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.AbstractFactory.Bottle.FriendlyBottle;
import com.khoben.cb.patterns.AbstractFactory.NPC.FriendlyNPC;
import com.khoben.cb.patterns.AbstractFactory.NPC.NPC;

public class FriendlyFactory implements IAbstractFactory {
    @Override
    public NPC createNPC(GameMap map) {
        return new FriendlyNPC(map);
    }

    @Override
    public IBottle createBottle(GameMap map) {
        return new FriendlyBottle(map);
    }
}
