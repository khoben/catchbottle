package com.khoben.cb.entities.BottlesFromSky;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Composite.Component;

/**
 * Created by extle on 28.11.2017.
 */

public class DeadlyBottle  extends BottleFromSky{

    public DeadlyBottle(){
        isDeadly = true;
    }

    public DeadlyBottle(GameMap m)
    {
        super(m, EntityType.DEADLYBOTTLE);
        isDeadly = true;
    }


    public DeadlyBottle (DeadlyBottle temp){
        super(temp);
    }

    @Override
    public Component getPack() {
        return null;
    }

    @Override
    public void create(Vector2 pos, GameMap map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void createPackBottles() {

    }

    @Override
    public int getPointsForBottle() {
        return 0;
    }
}
