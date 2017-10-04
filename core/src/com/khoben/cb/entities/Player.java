package com.khoben.cb.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.khoben.cb.entities.behavour.CanJump;
import com.khoben.cb.entities.behavour.CanMove;
import com.khoben.cb.map.GameMap;

/**
 * Created by extle on 01.10.2017.
 */

public class Player extends Entity {

    public static final int SPEED = 80;
    public static final int JUMP_VELOCITY = 5;

    Texture image;

    @Override
    public void create (EntityType type, Vector2 pos, GameMap map) {
        super.create(type, pos, map);
        image = new Texture("player1.png");
        moveAction = new CanMove();
        jumpAction = new CanJump();
    }


    @Override
    public void update(float deltaTime, float gravity) {

//        if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP) && grounded)) {
//            this.velocityY += JUMP_VELOCITY * getWeight();
//            this.isJumped = true;
//        }
//        else if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP) ) && !grounded && this.velocityY > 0) {
//            this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
//            this.isJumped = true;
//        }

        super.update(deltaTime, gravity);

//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
//            moveX(-SPEED * deltaTime);
//
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//            moveX(SPEED * deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }


}
