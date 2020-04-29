package com.khoben.cb.entities.bottles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Composite.Component;

/**
 * Created by extle on 20.11.2017.
 */

public interface IBottle {
    void createPackBottles();
    Component getPack();
    void create(Vector2 pos, GameMap map);
    void render(SpriteBatch batch);
    boolean doesIntersects(Player p);
    void doActions();
}
