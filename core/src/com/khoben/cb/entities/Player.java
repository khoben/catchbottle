package com.khoben.cb.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.khoben.cb.entities.behavour.CanJump;
import com.khoben.cb.entities.behavour.CanMove;
import com.khoben.cb.map.GameMap;

import java.util.HashMap;


/**
 * Created by extle on 01.10.2017.
 */

public class Player extends Entity {

    public static final float SPEED = 80;
    public static final float JUMP_VELOCITY = 5;

    public enum PlayerState{
        STAND_R,
        STAND_L,
        MOVE_LEFT,
        MOVE_RIGHT,
        MOVE_LEFT2,
        MOVE_RIGHT2,
        JUMP,
        FALL,
        ACHIEVED
    }

    public boolean direction;

    public PlayerState playerState = PlayerState.STAND_R;

    Texture image;
    
    HashMap<PlayerState, Texture> playerTextures;

    Animation walkAnimationL;
    Animation walkAnimationR;

    float curStateR;
    float curStateL;
    

    @Override
    public void create (EntityType type, Vector2 pos, GameMap map) {
        playerTextures = new HashMap<PlayerState, Texture>();
        super.create(type, pos, map);
        image = new Texture("player/player_standR.png");
        moveAction = new CanMove();
        jumpAction = new CanJump();

        curStateR = curStateL = 0;
        direction = true;

        playerTextures.put(PlayerState.STAND_R,new Texture("player/player_standR.png"));
        playerTextures.put(PlayerState.STAND_L,new Texture("player/player_standL.png"));
        playerTextures.put(PlayerState.MOVE_LEFT,new Texture("player/player_walkL1.png"));
        playerTextures.put(PlayerState.MOVE_RIGHT,new Texture("player/player_walkR1.png"));
        playerTextures.put(PlayerState.MOVE_LEFT2,new Texture("player/player_walkL2.png"));
        playerTextures.put(PlayerState.MOVE_RIGHT2,new Texture("player/player_walkR2.png"));
        playerTextures.put(PlayerState.JUMP,new Texture("player/player_jump.png"));
        playerTextures.put(PlayerState.FALL, new Texture("player/player_falling.png"));
        playerTextures.put(PlayerState.ACHIEVED, new Texture("player/player_cheer.png"));

        Texture[] textureFramesL = new Texture[2];
        textureFramesL[0] = playerTextures.get(PlayerState.MOVE_LEFT);
        textureFramesL[1] = playerTextures.get(PlayerState.MOVE_LEFT2);
        walkAnimationL = new Animation(SPEED,textureFramesL);

        Texture[] textureFramesR = new Texture[2];
        textureFramesR[0] = playerTextures.get(PlayerState.MOVE_RIGHT);
        textureFramesR[1] = playerTextures.get(PlayerState.MOVE_RIGHT2);

        walkAnimationR = new Animation(SPEED,textureFramesR);
    }


    @Override
    public void update(float deltaTime, float gravity) {

        super.update(deltaTime, gravity);

    }

    public void checkStatePlayer()
    {
        switch (playerState){
            case MOVE_LEFT:
                curStateL+=7;
                image = (Texture) walkAnimationL.getKeyFrame(curStateL,true);
                break;
            case MOVE_RIGHT:
                curStateR+=7;
                image = (Texture) walkAnimationR.getKeyFrame(curStateR,true);
                break;
            default:
                curStateL = 0;
                curStateR = 0;
                image = playerTextures.get(this.playerState);
                break;

        }
    }

    @Override
    public void render(SpriteBatch batch) {
        checkStatePlayer();
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }


}
