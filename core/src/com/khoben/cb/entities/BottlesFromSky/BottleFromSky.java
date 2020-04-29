package com.khoben.cb.entities.BottlesFromSky;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.Entity;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.bottles.IBottle;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Composite.Component;

/**
 * Created by extle on 29.11.2017.
 */

public class BottleFromSky extends Bottle implements Component, IBottle{

    final static int maxX = 1000;

    GameMap map;

    protected boolean isDeadly;
    public BottleFromSky(){}

    @Override
    public void createPackBottles() {

    }

    @Override
    public int getPointsForBottle() {
        return 0;
    }

    public BottleFromSky(GameMap m, EntityType type) {
        super(type, new Vector2(MathUtils.random(0, maxX), 800), m);
        this.map = m;
    }

    public BottleFromSky(BottleFromSky temp){
        super(temp.type,temp.pos,temp.map);
    }

    public void resetPos(){
        this.pos = (new Vector2(MathUtils.random(0, maxX), 800));
    }

    public boolean isDeadly(){
        return isDeadly;
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
}
