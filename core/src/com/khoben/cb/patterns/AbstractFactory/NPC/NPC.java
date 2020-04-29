package com.khoben.cb.patterns.AbstractFactory.NPC;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.khoben.cb.entities.players.Player;

public interface NPC {
    void doActions();
    void render(SpriteBatch batch);
    boolean doesIntersects(Player p);
}
