package com.khoben.cb.entities.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.khoben.cb.entities.EntitiesStorage.EntitySnapshot;
import com.khoben.cb.entities.Entity;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.behavour.CanJump;
import com.khoben.cb.entities.behavour.CanMove;
import com.khoben.cb.entities.behavour.CannotJump;
import com.khoben.cb.entities.behavour.CannotMove;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.bottles.MidBottle;
import com.khoben.cb.entities.bottles.Pair;
import com.khoben.cb.map.CustomGameMap;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Mediator.Colleague;
import com.khoben.cb.patterns.Mediator.Mediator;
import com.khoben.cb.patterns.Visitor.ItemOnScreen;
import com.khoben.cb.patterns.Visitor.MakeSoundByEntity;
import com.khoben.cb.patterns.Visitor.Visitor;

import java.util.HashMap;


/**
 * Created by extle on 01.10.2017.
 */

public class Player extends Entity implements ItemOnScreen,  Colleague{

    private static volatile Player singletonPlayer = null;

    public static final float SPEED = 65;
    public static final float JUMP_VELOCITY = 1;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    MakeSoundByEntity sound;


    @Override
    public void receive(Colleague c) {
        MidBottle mb  = (MidBottle)c;
        this.setPos(new Vector2(mb.getX() - mb.getWeight() / 2.0f, mb.getY() + 150));
    }

    @Override
    public void send() {
        mediator.send(this);
    }

    public enum PlayerStateAnime {
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

    public enum PlayerStateMoving{
        MOVE_R,
        MOVE_L,
        JUMP,
        DEFAULT
    }

    public boolean direction;

    public PlayerStateAnime playerStateAnime = PlayerStateAnime.STAND_R;

    public PlayerStateMoving playerStateMoving = PlayerStateMoving.DEFAULT;

    transient Texture image;

    transient HashMap<PlayerStateAnime, Texture> playerTextures;

    transient Animation walkAnimationL;
    transient Animation walkAnimationR;

    transient Texture[] textureFramesL;
    transient Texture[] textureFramesR;

    float curStateR;
    float curStateL;

    public int getJumps() {
        return jumps/2;
    }

    public void setJumps(int jumps) {
        this.jumps = jumps;
    }

    int jumps;

    Mediator mediator;

    public Player() {
        jumps = 0;
    }

    public Player(Mediator mediator){
        this.mediator = mediator;
    }

    boolean isInvalid = false;

    public void makeInvalidPlayer(){
        isInvalid = true;
    }

    @Override
    public void create(EntityType type, Vector2 pos, GameMap map) {
        playerTextures = new HashMap<PlayerStateAnime, Texture>();
        super.create(type, pos, map);
        image = new Texture("player/player_standR.png");
        if (isInvalid == false) {
            moveAction = new CanMove();
            jumpAction = new CanJump();
        }
        else {
            moveAction = new CannotMove();
            jumpAction = new CannotJump();
        }
        curStateR = curStateL = 0;
        direction = true;

        playerTextures.put(PlayerStateAnime.STAND_R, new Texture("player/player_standR.png"));
        playerTextures.put(PlayerStateAnime.STAND_L, new Texture("player/player_standL.png"));
        playerTextures.put(PlayerStateAnime.MOVE_LEFT, new Texture("player/player_walkL1.png"));
        playerTextures.put(PlayerStateAnime.MOVE_RIGHT, new Texture("player/player_walkR1.png"));
        playerTextures.put(PlayerStateAnime.MOVE_LEFT2, new Texture("player/player_walkL2.png"));
        playerTextures.put(PlayerStateAnime.MOVE_RIGHT2, new Texture("player/player_walkR2.png"));
        playerTextures.put(PlayerStateAnime.JUMP, new Texture("player/player_jump.png"));
        playerTextures.put(PlayerStateAnime.FALL, new Texture("player/player_falling.png"));
        playerTextures.put(PlayerStateAnime.ACHIEVED, new Texture("player/player_cheer.png"));

        sound = new MakeSoundByEntity();

        textureFramesL = new Texture[2];
        textureFramesL[0] = playerTextures.get(PlayerStateAnime.MOVE_LEFT);
        textureFramesL[1] = playerTextures.get(PlayerStateAnime.MOVE_LEFT2);
        walkAnimationL = new Animation(SPEED, textureFramesL);

        textureFramesR = new Texture[2];
        textureFramesR[0] = playerTextures.get(PlayerStateAnime.MOVE_RIGHT);
        textureFramesR[1] = playerTextures.get(PlayerStateAnime.MOVE_RIGHT2);

        walkAnimationR = new Animation(SPEED, textureFramesR);

        jumps = 0;
    }


    @Override
    public void update(float deltaTime, float gravity) {

        if (playerStateMoving == PlayerStateMoving.JUMP || Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded)  {
            float velocityY = this.getVelocityY();
            velocityY += this.JUMP_VELOCITY * this.getWeight();
            this.performJump(velocityY);
            jumps++;
            playerStateMoving = PlayerStateMoving.DEFAULT;
            //System.out.println("jump1");
            playerStateMoving = PlayerStateMoving.DEFAULT;
        } else if (playerStateMoving == PlayerStateMoving.JUMP || Gdx.input.isKeyPressed(Input.Keys.SPACE) && !grounded && this.velocityY > 0) {
            float velocityY = this.getVelocityY();
            velocityY += this.JUMP_VELOCITY * this.getWeight() * deltaTime;
            this.performJump(velocityY);
            jumps++;

            //System.out.println("jump2");
            playerStateMoving = PlayerStateMoving.DEFAULT;
        }

        if (this.getVelocityY() < 0) {
            this.playerStateAnime = PlayerStateAnime.JUMP;
        } else if (this.getVelocityY() == 0) {
            if (this.direction == true)
                this.playerStateAnime = PlayerStateAnime.STAND_R;
            else
                this.playerStateAnime = PlayerStateAnime.STAND_L;
        }

        super.update(deltaTime, gravity);

        if (playerStateMoving == PlayerStateMoving.MOVE_L || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.performMove(-this.SPEED * deltaTime);
            if (this.isGrounded()) {
                this.playerStateAnime = PlayerStateAnime.MOVE_LEFT;
                this.direction = false;
            }
            playerStateMoving = PlayerStateMoving.DEFAULT;
        }

        if (playerStateMoving == PlayerStateMoving.MOVE_R || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.performMove(this.SPEED * deltaTime);
            if (this.isGrounded()) {
                this.playerStateAnime = PlayerStateAnime.MOVE_RIGHT;
                this.direction = true;
            }
            playerStateMoving = PlayerStateMoving.DEFAULT;

        }



        if (!this.isGrounded() && this.playerStateAnime != PlayerStateAnime.ACHIEVED && this.getVelocityY() < 0) {
            this.playerStateAnime = PlayerStateAnime.FALL;
        }

        if (this.playerStateAnime == PlayerStateAnime.ACHIEVED) {
            this.playerStateAnime = PlayerStateAnime.STAND_R;
        }

        if (this.playerStateAnime == PlayerStateAnime.FALL) {
            // on bottle
            if (map.sEntities.mainComposite.components.size() > 1) {
                Pair<Bottle, Boolean> check = map.sEntities.mainComposite.operation(this);
                if (check.getRight()) {
                    //collectedBottles+=check.getLeft().getPointsForBottle();
                    map.calculatePoints(check.getLeft().getPointsForBottle());

                    map.gameStatus = CustomGameMap.GameStatus.NEXT_LEVEL;
                    this.playerStateAnime = PlayerStateAnime.ACHIEVED;
                    map.bottlesFromSky.clear();
                    this.accept(sound);

                } else if (this.isGrounded()) {
                    this.accept(sound);
                    this.playerStateAnime = PlayerStateAnime.STAND_R;
                    map.gameStatus = CustomGameMap.GameStatus.ENDED;
                }
            }

        }


    }

    public void checkStatePlayer() {
        switch (playerStateMoving) {
            case MOVE_L:
                curStateL += 7;
                image = (Texture) walkAnimationL.getKeyFrame(curStateL, true);
                break;
            case MOVE_R:
                curStateR += 7;
                image = (Texture) walkAnimationR.getKeyFrame(curStateR, true);
                break;
            default:
                curStateL = 0;
                curStateR = 0;
                image = playerTextures.get(this.playerStateAnime);
                break;

        }

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    @Override
    public EntitySnapshot getSaveSnapshot(){
        EntitySnapshot snapshot = super.getSaveSnapshot();
        return snapshot;
    }
}
