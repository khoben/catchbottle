package com.khoben.cb.map;

/**
 * Created by extle on 01.10.2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.khoben.cb.entities.Bottle;
import com.khoben.cb.entities.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.print.DocFlavor;

public class CustomGameMap extends GameMap{

    String id;
    String name;
    int[][][] map;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;
    int collectedBottles;

    float deltaTime;

    public enum GameStatus{
        IN_PROGRESS,
        NEXT_LEVEL,
        ENDED
    }

    public enum PlayerStatus{
        ON_GROUND,
        IN_AIR,
        ACHIEVED
    }

    GameStatus gameStatus;
    PlayerStatus playerStatus;

    private TextureRegion[][] tiles;

    public CustomGameMap () {

        CustomGameMapData data = CustomGameMapLoader.loadMap("basic", "My Grass Lands!");
        this.id = data.id;
        this.name = data.name;
        this.map = data.map;

        tiles = TextureRegion.split(new Texture("tiles.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        collectedBottles = 0;
        gameStatus = GameStatus.IN_PROGRESS;
        playerStatus = PlayerStatus.ON_GROUND;
    }

    @Override
    public void render(OrthographicCamera camera, SpriteBatch batch) {

        if (gameStatus == GameStatus.NEXT_LEVEL)
        {
            gameStatus = GameStatus.IN_PROGRESS;
            this.resetEntities();
        }else if(gameStatus == GameStatus.ENDED)
        {
            this.collectedBottles = 0;
            gameStatus = GameStatus.IN_PROGRESS;
            this.resetEntities();
        }

        // in air
        if (!this.player.isGrounded()&&this.player.isJumped()&&playerStatus!=PlayerStatus.ACHIEVED)
        {
            playerStatus = PlayerStatus.IN_AIR;
            System.out.println("air");
        }

        if(playerStatus == PlayerStatus.ACHIEVED)
        {
            playerStatus = PlayerStatus.ON_GROUND;
        }

        //before grounded should catch bottle
        if (playerStatus == PlayerStatus.IN_AIR)
        {
            // on bottle
            if (this.entities.size()>1)
            {
                if (this.doesPlayerCollideWithBottle(this.player, this.bottle)){
                    collectedBottles++;
                    gameStatus = GameStatus.NEXT_LEVEL;
                    playerStatus = PlayerStatus.ACHIEVED;
                    this.entities.remove(1);
                }
                else if(this.player.isGrounded())
                {
                    System.out.println("ground");
                    playerStatus = PlayerStatus.ON_GROUND;
                    gameStatus = GameStatus.ENDED;
                }
            }

        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int layer = 0; layer < getLayers(); layer++) {
            for (int row = 0; row < getHeight(); row++) {
                for (int col = 0; col < getWidth(); col++) {
                    TileType type = this.getTileTypeByCoordinate(layer, col, row);
                    if (type != null)
                        batch.draw(tiles[0][type.getId() - 1], col * TileType.TILE_SIZE, row * TileType.TILE_SIZE);
                }
            }
        }

        font.draw(batch,"Bottles collected: "+collectedBottles,200,camera.viewportHeight - parameter.size);
        super.render(camera, batch);

        batch.end();
    }

    @Override
    public void update(float delta) {

        if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.isGrounded())) {
            float velocityY = player.getVelocityY();
            velocityY += player.JUMP_VELOCITY * player.getWeight();
            player.performJump(velocityY);
            player.isJumped = true;
        }
        else if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP) ) && !player.isGrounded() && player.getVelocityY() > 0) {
            float velocityY = player.getVelocityY();
            velocityY += player.JUMP_VELOCITY * player.getWeight() * delta;
            player.performJump(velocityY);
            player.isJumped = true;
        }

        super.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player.performMove(-player.SPEED * delta);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player.performMove(player.SPEED * delta);
    }

    @Override
    public void dispose() {}

    @Override
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), getHeight() - (int) (y / TileType.TILE_SIZE) - 1);
    }

    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        if (col < 0 || col >= getWidth() || row < 0 || row >= getHeight())
            return null;

        return TileType.getTileTypeById(map[layer][getHeight() - row - 1][col]);
    }

    @Override
    public int getWidth() {
        return map[0][0].length;
    }

    @Override
    public int getHeight() {
        return map[0].length;
    }

    @Override
    public int getLayers() {
        return map.length;
    }

}
