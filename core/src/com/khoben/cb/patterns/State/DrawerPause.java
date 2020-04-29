package com.khoben.cb.patterns.State;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.khoben.cb.CatchBottleGame;
import com.khoben.cb.map.CustomGameMap;
import com.khoben.cb.patterns.Bridge.IDrawable;
import com.khoben.cb.screens.Button.MyButton;
import com.khoben.cb.screens.GameScreen;

public class DrawerPause implements IDrawable{

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

    public DrawerPause(GameScreen.State state, CatchBottleGame game, SpriteBatch batch, OrthographicCamera camera, CustomGameMap gameMap, BitmapFont font, float fontW, GlyphLayout layout, float h, MyButton menuButton) {
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
    public void render() throws IllegalAccessException, InstantiationException {

            game.batch.begin();
            font.draw(game.batch,layout,(camera.viewportWidth - fontW )/2,h/2);
            game.batch.end();


    }

    @Override
    public void setState(IDrawable state) {

    }

    @Override
    public IDrawable getSomeState(Class classState) {
        return null;
    }
}
