package com.khoben.cb.entities.bottles;

import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Composite.Component;

import java.util.List;

/**
 * Created by extle on 25.10.2017.
 */

public class SmallBottle extends Bottle {

    public void create(Vector2 p, GameMap m)
    {
        super.create(EntityType.SMALLBOTTLE,p,m);
        addPoints = 3;
    }
    public SmallBottle(Vector2 p, GameMap m)
    {
        create(EntityType.SMALLBOTTLE,p,m);
        addPoints = 3;
    }
    @Override
    public void createPackBottles() {

    }
    public SmallBottle(){};

    @Override
    public int getPointsForBottle() {
        return addPoints;
    }

    public Pair<Bottle,Boolean> doesCollisionWithPlayer(Player p) {
        return super.doesCollisionWhithPlayer(p);
    }

    @Override
    public Pair<Bottle,Boolean> operation(Player p) {
        return doesCollisionWithPlayer(p);
    }

    @Override
    public void clear() {

    }
}
