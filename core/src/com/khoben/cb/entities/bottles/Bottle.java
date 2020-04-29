package com.khoben.cb.entities.bottles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.Entity;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Composite.Component;
import com.khoben.cb.patterns.Visitor.ItemOnScreen;
import com.khoben.cb.patterns.Visitor.Visitor;

import java.io.Serializable;


/**
 * Created by extle on 01.10.2017.
 */

public class Bottle extends Entity implements Component, IBottle, ItemOnScreen{

    private Texture image;
    protected int addPoints;

    final static float vel = -3.0f;

    public Bottle(EntityType t, Vector2 p, GameMap m) {
        super.create(t, p, m);
        image = new Texture("bottle.png");
        this.velocityY = vel;
    }

    public Bottle() {
    }

    @Override
    public void create(EntityType type, Vector2 pos, GameMap map) {
        super.create(type, pos, map);
        image = new Texture("bottle.png");
        this.velocityY = vel;
    }

    @Override
    public void update(float deltaTime, float gravity) {
        //super.update(deltaTime, gravity);
        float newY = pos.y;

        //this.velocityY += gravity * deltaTime * getWeight();
        newY += this.velocityY;

        if (map.doesRectCollideWithMap(pos.x, newY, getWidth(), getHeight())) {
            if (velocityY < 0) {
                this.pos.y = (float) Math.floor(pos.y);
                grounded = true;
            }
            this.velocityY = 0;
        } else {
            this.pos.y = newY;
            grounded = false;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
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

    }

    public void createPackBottles(){}

    @Override
    public Component getPack() {
        return null;
    }

    @Override
    public void create(Vector2 pos, GameMap map) {

    }

    public int getPointsForBottle(){return 0;}

    public Pair<Bottle, Boolean> doesCollisionWithPlayer(Player p) {
        Vector2 playerPos = p.getPos();
        float playerW = p.getWidth();
        float playerH = p.getHeight();

        Vector2 bottlePos = this.getPos();
        float bottleW = this.getWidth();
        float bottleH = this.getHeight();

        Rectangle playerRec = new Rectangle(playerPos.x, playerPos.y, playerW, playerH);
        Rectangle bottleRec = new Rectangle(bottlePos.x, bottlePos.y, bottleW, bottleH);

        if (Intersector.overlaps(playerRec, bottleRec)) {
            return new Pair<Bottle, Boolean>(this, true);
        }
        return new Pair<Bottle, Boolean>(this, false);
    }

    @Override
    public Pair<Bottle, Boolean> operation(Player p) {
        return doesCollisionWithPlayer(p);
    }

    @Override
    public void clear() {

    }

    Bottle setParam(Vector2 p) {
        this.pos = p;
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
