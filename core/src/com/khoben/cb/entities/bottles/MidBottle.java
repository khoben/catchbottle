package com.khoben.cb.entities.bottles;

import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Composite.Component;
import com.khoben.cb.patterns.Composite.Composite;
import com.khoben.cb.patterns.Mediator.Colleague;
import com.khoben.cb.patterns.Mediator.Mediator;

import java.util.Collection;
import java.util.Random;

/**
 * Created by extle on 25.10.2017.
 */

public class MidBottle extends Bottle implements Colleague {

    Composite packForMidBottle;
    Random rand;
    Mediator mediator;

    public boolean isVisible = true;

    public void create(Vector2 p, GameMap m)
    {
        packForMidBottle = new Composite();
        super.create(EntityType.MIDBOTTLE,p,m);
        rand = new Random(System.currentTimeMillis());
        addPoints = 2;
    }

    public MidBottle(Vector2 p, GameMap m)
    {
        addPoints = 2;
        super.create(EntityType.MIDBOTTLE,p,m);
    }

    public MidBottle(Vector2 p, GameMap m, Mediator mediator, boolean isVisible)
    {
        addPoints = 2;
        this.mediator  = mediator;
        this.isVisible = isVisible;
        super.create(EntityType.MIDBOTTLE,p,m);
    }

    public MidBottle(){};
    @Override
    public void createPackBottles() {
        packForMidBottle.components.clear();

        for(int i=0; i<2; i++){
            packForMidBottle.add(new SmallBottle(new Vector2(rand.nextInt(1000-560)+560,464),this.map));
        }
    }

    @Override
    public int getPointsForBottle() {
        return addPoints;
    }

    public Component getPack(){
        return packForMidBottle;
    }


    public Pair<Bottle,Boolean> doesCollisionWithPlayer(Player p) {
        return super.doesCollisionWithPlayer(p);
    }

    @Override
    public Pair<Bottle,Boolean> operation(Player p) {
        return doesCollisionWithPlayer(p);
    }

    @Override
    public void clear() {
    }


    @Override
    public void receive(Colleague c) {
        isVisible = false;
    }

    @Override
    public void send() {
        isVisible  = true;
        mediator.send(this);
    }
}
