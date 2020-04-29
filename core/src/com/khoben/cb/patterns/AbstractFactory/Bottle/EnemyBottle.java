package com.khoben.cb.patterns.AbstractFactory.Bottle;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.bottles.IBottle;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Composite.Component;

import java.util.Random;

public class EnemyBottle extends Bottle implements IBottle {
    @Override
    public void createPackBottles() {

    }

    public EnemyBottle(GameMap map) {
        this.create(EntityType.BIGBOTTLE, new Vector2(600, 300), map);
    }

    @Override
    public Component getPack() {
        return null;
    }

    @Override
    public void create(Vector2 pos, GameMap map) {

    }

    @Override
    public boolean doesIntersects(Player p) {
        Vector2 playerPos = p.getPos();
        float playerW = p.getWidth();
        float playerH = p.getHeight();

        Vector2 npcPos = this.getPos();
        float npcW = this.getWidth();
        float npcH = this.getHeight();

        Rectangle playerRec = new Rectangle(playerPos.x, playerPos.y, playerW, playerH);
        Rectangle bottleRec = new Rectangle(npcPos.x, npcPos.y, npcW, npcH);

        if (Intersector.overlaps(playerRec, bottleRec)) {
            return true;
        }
        return false;
    }

    @Override
    public void doActions() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int move = random.nextInt(30);
        int jump = random.nextInt(8);

        if (move % 2 == 0)
            move *= -1;

        if (jump % 2 == 0)
            jump *= -1;

        this.setPos(new Vector2(this.getX() + move, this.getY() + jump));
    }
}
