package com.khoben.cb.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.EntitiesStorage.EntitySnapshot;
import com.khoben.cb.entities.behavour.IJumpAction;
import com.khoben.cb.entities.behavour.IMoveAction;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Composite.Component;


public abstract class Entity{

    protected Vector2 pos;
    protected EntityType type;
    protected float velocityY = 0;
    protected GameMap map;
    protected boolean grounded = false;

    protected IJumpAction jumpAction;
    protected IMoveAction moveAction;

    public abstract void render (SpriteBatch batch);

    public void create (EntityType type, Vector2 pos, GameMap map) {
        this.pos = pos;
        this.type = type;
        this.map = map;
    }

    public void create (EntitySnapshot snapshot, EntityType type, GameMap map) {
        this.pos = new Vector2(snapshot.getX(), snapshot.getY());
        this.type = type;
        this.map = map;
    }

    public EntitySnapshot getSaveSnapshot () {
        return new EntitySnapshot(type.getId(), pos.x, pos.y);
    }

    public void update (float deltaTime, float gravity) {
        float newY = pos.y;

        this.velocityY += gravity * deltaTime * getWeight();
        newY += this.velocityY * deltaTime;

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

    public void performMove(float amount){
        moveAction.move(this, map, amount);
    }

    public void performJump(float amount){
        jumpAction.jump(this, amount);
    }

    public void moveX (float amount)
    {
        float newX = pos.x + amount;
        if (!map.doesRectCollideWithMap(newX, pos.y, getWidth(), getHeight()))
            this.pos.x = newX;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {this.pos = pos;}

    public void setPosX(float x) {
        this.pos.x = x;
    }

    public float getX () {
        return pos.x;
    }

    public float getY () {
        return pos.y;
    }

    public EntityType getType() {
        return type;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public int getWidth() {
        return type.getWidth();
    }

    public int getHeight() {
        return type.getHeight();
    }

    public float getWeight() {
        return type.getWeight();
    }

    public float getVelocityY() {return velocityY;}
    public void setVelocityY(float velocityY) {this.velocityY = velocityY;}

}
