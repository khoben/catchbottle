package com.khoben.cb.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.Bottle;
import com.khoben.cb.entities.Entity;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.Player;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by extle on 01.10.2017.
 */

public abstract class GameMap {

    protected ArrayList<Entity> entities;
    protected Player player;
    protected Bottle bottle;

    float bottleX;
    Random rand;


    public GameMap() {
        player = new Player();
        bottle = new Bottle();

        entities = new ArrayList<Entity>();

        rand = new Random(System.currentTimeMillis());
        //TODO: ADD ENTITIES
        resetEntities();
    }

    public void resetEntities() {

        entities.clear();
        player.create(EntityType.PLAYER,new Vector2(505,464),this);

        bottleX = rand.nextInt(750-560)+560;
        bottle.create(EntityType.BOTTLE,new Vector2(bottleX,464),this);

        entities.add(player);
        entities.add(bottle);
    }

    public void render (OrthographicCamera camera, SpriteBatch batch) {

        for( Entity e : entities)
        {
            e.render(batch);
        }
    }

    public void update (float delta) {
        for( Entity e : entities)
        {
            e.update(delta, -9.8f);
        }
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

    public boolean doesPlayerCollideWithBottle(Player player,Bottle bottle) {
        Vector2 playerPos = player.getPos();
        float playerW = player.getWidth();
        float playerH = player.getHeight();

        Vector2 bottlePos = bottle.getPos();
        float bottleW = bottle.getWidth();
        float bottleH = bottle.getHeight();

        Rectangle playerRec = new Rectangle(playerPos.x,playerPos.y,playerW,playerH);
        Rectangle bottleRec = new Rectangle(bottlePos.x,bottlePos.y,bottleW,bottleH);

        if (Intersector.overlaps(playerRec,bottleRec))
        {
            return true;
        }
        return false;



    }

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
