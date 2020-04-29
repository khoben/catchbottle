package com.khoben.cb.patterns.AbstractFactory.NPC;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.bottles.Pair;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.CustomGameMap;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.AbstractFactory.Iterfaces.IEnemy;

import java.util.Random;


public class EnemyNPC extends Player implements NPC {
    public EnemyNPC(GameMap map){
        this.create(EntityType.PLAYER,new Vector2(600,300),map);
    }

    @Override
    public void doActions() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int move = random.nextInt(100);
        int jump = random.nextInt(50);

        if (move%2==0)
            move*=-1;


        this.performJump(jump);
        this.performMove(move);
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
}
