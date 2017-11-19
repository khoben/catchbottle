package com.khoben.cb.patterns.Facade;

import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.Entity;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.bottles.BigBottle;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Adapter.Adapter;
import com.khoben.cb.patterns.Composite.Composite;
import com.khoben.cb.patterns.Iterator.IArray;

import java.util.Random;

/**
 * Created by extle on 19.11.2017.
 */

public class SetupEntities {
    private GameMap map;

    public IArray<Entity> entities = new Adapter<Entity>();
    public Composite mainComposite = new Composite();

    public Player player = new Player();
    private BigBottle bottle = new BigBottle();
    private Random rand = new Random(System.currentTimeMillis());
    private Composite bottleComposite = new Composite();
    private float bottleX;

    public SetupEntities(GameMap map){
        this.map = map;
    }

    public void setup(){
        mainComposite.components.clear();
        mainComposite.clear();
        entities.clear();
        player.create(EntityType.PLAYER,new Vector2(470,464),map);

        bottleX = rand.nextInt(1000-560)+560;
        bottle.create(new Vector2(bottleX,464),map);
        bottle.createPackBottles();

        bottleComposite.add(bottle.getPack());

        mainComposite.add(bottle);
        mainComposite.add(bottleComposite);

        entities.add(player);
    }
}
