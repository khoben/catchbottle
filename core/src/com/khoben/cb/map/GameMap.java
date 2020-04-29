package com.khoben.cb.map;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;
import com.khoben.cb.entities.bottles.BigBottle;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.Entity;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.entities.bottles.SmallBottle;
import com.khoben.cb.patterns.Adapter.Adapter;
import com.khoben.cb.patterns.Command.OpenGameCommand;
import com.khoben.cb.patterns.Command.SaveGameCommand;
import com.khoben.cb.patterns.Composite.Composite;
import com.khoben.cb.patterns.Facade.SetupEntities;
import com.khoben.cb.patterns.Iterator.IArray;
import com.khoben.cb.patterns.Iterator.MyIterator;
import com.khoben.cb.patterns.Memento.SaveHandler;

import java.io.IOException;
import java.util.List;
import java.util.Random;


/**
 * Created by extle on 01.10.2017.
 */

public abstract class GameMap {

    public SetupEntities sEntities;
    public List<BottleFromSky> bottlesFromSky;
    int collectedBottles;

    OpenGameCommand openGameCommand;
    SaveGameCommand saveGameCommand;

    public enum GameStatus {
        IN_PROGRESS,
        NEXT_LEVEL,
        ENDED
    }





    public GameStatus gameStatus;

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getLayers();


    public GameMap() {
        sEntities = new SetupEntities(this);
        resetEntities();
    }

    public void resetEntities() {
        sEntities.setup();
    }

    public void render(OrthographicCamera camera, SpriteBatch batch) throws InstantiationException, IllegalAccessException {
        //TODO: Iterator

        MyIterator<Entity> it = sEntities.entities.getIterator();
        while (it.hasNext()) {
            it.next().render(batch);
        }
        sEntities.mainComposite.render(batch);
    }

    public void update(float delta) {

        MyIterator<Entity> it = sEntities.entities.getIterator();
        while (it.hasNext()) {
            it.next().update(delta, -9.8f);
        }
        sEntities.mainComposite.update(delta, -9.8f);

    }

    public void dispose() {

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

    public int getPixelWidth() {
        return this.getWidth() * TileType.TILE_SIZE;
    }

    public int getPixelHeight() {
        return this.getHeight() * TileType.TILE_SIZE;
    }

    public void setPoints(int value){
        collectedBottles = value;
    }

    public void save(String name) throws IOException {
        SaveHandler.saveToFile(name,sEntities,bottlesFromSky, collectedBottles);
    }

    public void load(String name) throws IOException {
        SaveHandler.loadFromFile(name,sEntities,bottlesFromSky,this,collectedBottles);
    }

    public abstract int calculatePoints(int pointsForBottle);
}
