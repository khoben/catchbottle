package com.khoben.cb.patterns.Bridge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.khoben.cb.CatchBottleGame;
import com.khoben.cb.map.CustomGameMap;
import com.khoben.cb.screens.Button.MyButton;
import com.khoben.cb.screens.GameScreen;

/**
 * Created by extle on 19.11.2017.
 */

public class NormalDrawer implements IDrawable {

    GameScreen.State state;
    CatchBottleGame game;
    SpriteBatch batch;
    OrthographicCamera camera;
    CustomGameMap gameMap;
    BitmapFont font;
    float fontW;
    GlyphLayout layout;
    float h;

    MyButton menuButton;

    public NormalDrawer(GameScreen.State state, CatchBottleGame game, SpriteBatch batch, OrthographicCamera camera, CustomGameMap gameMap, BitmapFont font, float fontW, GlyphLayout layout, float h, MyButton menuButton) {
        this.state = state;
        this.game = game;
        this.batch = batch;
        this.camera = camera;
        this.gameMap = gameMap;
        this.font = font;
        this.fontW = fontW;
        this.layout = layout;
        this.h = h;
        this.menuButton = menuButton;
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
        {
            if (state == GameScreen.State.Paused)
            {
                state = GameScreen.State.Running;
            }
            else{
                state = GameScreen.State.Paused;
            }
        }
        if (state == state.Running) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            gameMap.render(camera, batch);
            game.batch.begin();
            menuButton.draw(game.batch);
            game.batch.end();
        }
        else {
            game.batch.begin();
            font.draw(game.batch,layout,(camera.viewportWidth - fontW )/2,h/2);
            game.batch.end();
        }
    }
}
