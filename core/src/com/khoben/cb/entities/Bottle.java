package com.khoben.cb.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.map.GameMap;

/**
 * Created by extle on 01.10.2017.
 */

public class Bottle extends Entity {

    Texture image;

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

    @Override
    public void operation() {

    }
}
