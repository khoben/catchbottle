package com.khoben.cb.patterns.Bridge;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.khoben.cb.CatchBottleGame;
import com.khoben.cb.map.CustomGameMap;
import com.khoben.cb.patterns.State.DrawerDrawing;
import com.khoben.cb.patterns.State.DrawerPause;
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

    IDrawable drawerState;

    DrawerPause drawerPause;
    DrawerDrawing drawerDrawing;

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

        drawerPause = new DrawerPause(state,game,batch,camera,gameMap,font,fontW,layout,h,menuButton);
        drawerDrawing = new DrawerDrawing(state,game,batch,camera,gameMap,font,fontW,layout,h,menuButton);

        drawerState = drawerDrawing;
    }


    public IDrawable getSomeState(Class classState){
        switch (classState.getSimpleName()){
            case "DrawerPause":
                return drawerPause;
            case "DrawerDrawing":
                return drawerDrawing;
                default:
                    return null;
        }
    }

    public void setState(IDrawable state){
        drawerState = state;
    }

    @Override
    public void render() throws IllegalAccessException, InstantiationException {

        drawerState.render();
    }
}
