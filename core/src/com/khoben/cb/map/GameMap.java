package com.khoben.cb.map;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.bottles.BigBottle;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.Entity;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.entities.bottles.SmallBottle;
import com.khoben.cb.patterns.Adapter.Adapter;
import com.khoben.cb.patterns.Composite.Composite;
import com.khoben.cb.patterns.Iterator.IArray;
import com.khoben.cb.patterns.Iterator.MyIterator;

import java.util.Random;


/**
 * Created by extle on 01.10.2017.
 */

public abstract class GameMap {

    protected IArray<Entity> entities;
    protected Player player;
    protected BigBottle bottle;
    float bottleX;
    Random rand;
    Composite bottleComposite;
    Composite mainComposite;


    public GameMap() {
        player = new Player();
        bottle = new BigBottle();

        //TODO: Adapter
        entities = new Adapter<Entity>();
        bottleComposite = new Composite();
        mainComposite = new Composite();
        rand = new Random(System.currentTimeMillis());
        resetEntities();
    }

    public void resetEntities() {
        mainComposite.components.clear();
        mainComposite.clear();
        entities.clear();
        player.create(EntityType.PLAYER,new Vector2(470,464),this);

        bottleX = rand.nextInt(1000-560)+560;
        bottle.create(new Vector2(bottleX,464),this);
        bottle.createPackBottles();

        bottleComposite.add(bottle.getPack());

        mainComposite.add(bottle);
        mainComposite.add(bottleComposite);

//        System.out.println(mainComposite.components.size());
//        System.out.println(bottleComposite.components.size());

        entities.add(player);
    }

    public void render (OrthographicCamera camera, SpriteBatch batch) {
        //TODO: Iterator

        MyIterator<Entity> it = entities.getIterator();
        while (it.hasNext())
        {
           it.next().render(batch);
        }
        mainComposite.render(batch);
    }

    public void update (float delta) {

        MyIterator<Entity> it = entities.getIterator();
        while (it.hasNext())
        {
            it.next().update(delta, -9.8f);
        }
        mainComposite.update(delta,-9.8f);
    }

    public void dispose () {

    }

    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), (int) (y / TileType.TILE_SIZE));
    }

    public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);

    public boolean doesRectCollideWithMap(float x, float y, int width, int height) {
        if (x < 0 || y < 0 || x + width > getPixelWidth() || y + height > getPixelHeight())
            return true;

        for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++) {
            for (int col = (int) (x / TileType.TILE_SIZE); col < Math.ceil((x + width) / TileType.TILE_SIZE); col++) {
                for (int layer = 0; layer < getLayers(); layer++) {
                    TileType type = getTileTypeByCoordinate(layer, col, row);
                    if (type != null && type.isCollidable())
                        return true;
                }
            }
        }

        return false;
    }

//    public boolean doesPlayerCollideWithBottle(Player player,Bottle bottle) {
//
//        Vector2 playerPos = player.getPos();
//        float playerW = player.getWidth();
//        float playerH = player.getHeight();
//
//        Vector2 bottlePos = bottle.getPos();
//        float bottleW = bottle.getWidth();
//        float bottleH = bottle.getHeight();
//
//        Rectangle playerRec = new Rectangle(playerPos.x,playerPos.y,playerW,playerH);
//        Rectangle bottleRec = new Rectangle(bottlePos.x,bottlePos.y,bottleW,bottleH);
//
//        if (Intersector.overlaps(playerRec,bottleRec))
//        {
//            return true;
//        }
//        return false;
//    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();

    public int getPixelWidth() {
        return this.getWidth() * TileType.TILE_SIZE;
    }

    public int getPixelHeight() {
        return this.getHeight() * TileType.TILE_SIZE;
    }

}
