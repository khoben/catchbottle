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


/**
 * Created by extle on 01.10.2017.
 */

public abstract class Bottle extends Entity implements Component {

    private Texture image;
    protected int addPoints;

    public Bottle(EntityType t, Vector2 p, GameMap m)
    {
        super.create(t,p,m);
        image = new Texture("bottle.png");
    }

    public Bottle (){};

    @Override
    public void create(EntityType type, Vector2 pos, GameMap map) {
        super.create(type, pos, map);
        image = new Texture("bottle.png");
    }

    @Override
    public void update(float deltaTime, float gravity) {
        super.update(deltaTime, gravity);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    abstract public void createPackBottles();

    abstract public int getPointsForBottle();

    public Pair<Bottle,Boolean> doesCollisionWhithPlayer(Player p){
        Vector2 playerPos = p.getPos();
        float playerW = p.getWidth();
        float playerH = p.getHeight();

        Vector2 bottlePos = this.getPos();
        float bottleW = this.getWidth();
        float bottleH = this.getHeight();

        Rectangle playerRec = new Rectangle(playerPos.x,playerPos.y,playerW,playerH);
        Rectangle bottleRec = new Rectangle(bottlePos.x,bottlePos.y,bottleW,bottleH);

        if (Intersector.overlaps(playerRec,bottleRec))
        {
            return new Pair<Bottle, Boolean>(this,true);
        }
        return new Pair<Bottle, Boolean>(this,false);
    }

    @Override
    public Pair<Bottle,Boolean> operation(Player p) {
        return doesCollisionWhithPlayer(p);
    }

    Bottle setParam(Vector2 p){
        this.pos = p;
        return this;
    }
}
