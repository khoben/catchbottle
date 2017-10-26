package com.khoben.cb.map;

/**
 * Created by extle on 01.10.2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.bottles.Pair;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.patterns.Decorator.AmountCollectedBottles;
import com.khoben.cb.patterns.Decorator.Decorator;
import com.khoben.cb.patterns.Decorator.DecoratorTitleForCollectedBottles;

public class CustomGameMap extends GameMap{
    String id;
    String name;
    int[][][] map;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;
    int collectedBottles;

    Decorator decoratorForTitleCountBottles;
    AmountCollectedBottles amountCollectedBottles;

    public enum GameStatus{
        IN_PROGRESS,
        NEXT_LEVEL,
        ENDED
    }

    GameStatus gameStatus;
    GlyphLayout layout;

    private TextureRegion[][] tiles;

    public CustomGameMap () {

        CustomGameMapData data = CustomGameMapLoader.loadMap("basic", "My Grass Lands!");
        this.id = data.id;
        this.name = data.name;
        this.map = data.map;
        tiles = TextureRegion.split(new Texture("tiles.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        font = generator.generateFont(parameter);
        collectedBottles = 0;
        gameStatus = GameStatus.IN_PROGRESS;
        this.player.playerState = Player.PlayerState.STAND_R;
        amountCollectedBottles = new AmountCollectedBottles();
        decoratorForTitleCountBottles = new DecoratorTitleForCollectedBottles(amountCollectedBottles);
    }

    @Override
    public void render(OrthographicCamera camera, SpriteBatch batch) {

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
        //TODO: Decorator
        amountCollectedBottles.SetAmount(collectedBottles);
        layout = new GlyphLayout(font,decoratorForTitleCountBottles.getFinalString());
        font.draw(batch,layout,camera.viewportWidth - layout.width ,camera.viewportHeight - layout.height);
        super.render(camera, batch);

        batch.end();
    }

    @Override
    public void update(float delta) {

        if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP))) {
            float velocityY = player.getVelocityY();
            velocityY += player.JUMP_VELOCITY * player.getWeight();
            player.performJump(velocityY);
        }
        else if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP) ) && !player.isGrounded() && player.getVelocityY() > 0) {
            float velocityY = player.getVelocityY();
            velocityY += player.JUMP_VELOCITY * player.getWeight() * delta;
            player.performJump(velocityY);
        }

        if (player.getVelocityY() > 0) {
            player.playerState = Player.PlayerState.JUMP;
        }else
        if (player.getVelocityY() == 0) {
            if (player.direction == true)
                player.playerState = Player.PlayerState.STAND_R;
            else
                player.playerState = Player.PlayerState.STAND_L;
        }

        super.update(delta);

        checkGameState();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.performMove(-player.SPEED * delta);
            if (player.isGrounded()) {
                player.playerState = Player.PlayerState.MOVE_LEFT;
                player.direction = false;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.performMove(player.SPEED * delta);
            if (player.isGrounded()) {
                player.playerState = Player.PlayerState.MOVE_RIGHT;
                player.direction = true;
            }
        }

    }

    public void checkGameState()
    {
        if (gameStatus == GameStatus.NEXT_LEVEL)
        {
            this.player.playerState = Player.PlayerState.ACHIEVED;
            gameStatus = GameStatus.IN_PROGRESS;
            this.mainComposite.clear();
            this.resetEntities();
        }else if(gameStatus == GameStatus.ENDED)
        {
            this.collectedBottles = 0;
            gameStatus = GameStatus.IN_PROGRESS;
            this.mainComposite.clear();
            this.resetEntities();
        }

        // in air
        if (!this.player.isGrounded()&&this.player.playerState!= Player.PlayerState.ACHIEVED && this.player.getVelocityY()<0) {
            this.player.playerState = Player.PlayerState.FALL;
        }

        if(this.player.playerState == Player.PlayerState.ACHIEVED)
        {
            player.playerState = Player.PlayerState.STAND_R;
        }

        //before grounded should catch bottle
        if (this.player.playerState == Player.PlayerState.FALL)
        {
            // on bottle
            if (this.mainComposite.components.size()>1)
            {
                Pair<Bottle,Boolean> check =  this.mainComposite.operation(this.player);
                if (check.getRight()){
                    collectedBottles+=check.getLeft().addPoints;
                    gameStatus = GameStatus.NEXT_LEVEL;
                    this.player.playerState = Player.PlayerState.ACHIEVED;
                }
                else if(this.player.isGrounded())
                {
                    this.player.playerState = Player.PlayerState.STAND_R;
                    gameStatus = GameStatus.ENDED;
                }
            }

        }
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
