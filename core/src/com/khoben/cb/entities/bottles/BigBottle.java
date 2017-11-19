package com.khoben.cb.entities.bottles;

import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Composite.Component;
import com.khoben.cb.patterns.Composite.Composite;
import com.khoben.cb.patterns.Flyweight.FlyweightFactory;

import java.util.List;
import java.util.Random;


/**
 * Created by extle on 25.10.2017.
 */

public class BigBottle extends Bottle {

    Composite packForBigBottle;
    Random rand;
    FlyweightFactory factoryBottles;
    public void create(Vector2 p, GameMap m)
    {
        packForBigBottle = new Composite();
        factoryBottles = new FlyweightFactory(m);
        super.create(EntityType.BIGBOTTLE,p,m);
        rand = new Random(System.currentTimeMillis());
        addPoints = 1;
    }

    public BigBottle(){};
    @Override
    public void createPackBottles() {
        //create 3 smalls bottles in 1 big
        packForBigBottle.components.clear();
//        packForBigBottle.add(new MidBottle(this.getPos(),this.map));
//        packForBigBottle.add(new SmallBottle(this.getPos(),this.map));

//        packForBigBottle.add(new MidBottle(new Vector2(rand.nextInt(1000-560)+560,464),this.map));
//        packForBigBottle.add(new SmallBottle(new Vector2(rand.nextInt(1000-560)+560,464),this.map));

        packForBigBottle.add(factoryBottles.getConcreteBottle("MidBottle").setParam(new Vector2(rand.nextInt(1000-560)+560,464)));
        packForBigBottle.add(factoryBottles.getConcreteBottle("SmallBottle").setParam(new Vector2(rand.nextInt(1000-560)+560,464)));
    }

    @Override
    public int getPointsForBottle() {
        return addPoints;
    }

    public Component getPack(){
        return packForBigBottle;
    }


    public Pair<Bottle, Boolean> doesCollisionWithPlayer(Player p) {
       return super.doesCollisionWhithPlayer(p);
    }

    @Override
    public Pair<Bottle, Boolean> operation(Player p) {
        return doesCollisionWithPlayer(p);
    }

    @Override
    public void clear() {
        packForBigBottle.components.clear();
    }
}
