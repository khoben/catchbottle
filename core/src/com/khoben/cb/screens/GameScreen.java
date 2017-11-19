package com.khoben.cb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.khoben.cb.CatchBottleGame;
import com.khoben.cb.map.CustomGameMap;
import com.khoben.cb.patterns.Bridge.Drawer;
import com.khoben.cb.patterns.Bridge.FakeConsoleDrawer;
import com.khoben.cb.patterns.Bridge.IDrawable;
import com.khoben.cb.patterns.Bridge.NormalDrawer;
import com.khoben.cb.screens.Button.MyButton;



/**
 * Created by extle on 01.10.2017.
 */

public class GameScreen implements Screen, InputProcessor {

    final CatchBottleGame game;
    SpriteBatch batch;
    OrthographicCamera camera;
    CustomGameMap gameMap;

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;
    float fontW;

    final static String TAP_PLAY = "PAUSED";
    GlyphLayout layout;

    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();

    Drawer drawer;


    public enum State{
        Running,
        Paused
    }

    State state = State.Running;

    MyButton menuButton;

    IDrawable normalDrawable;
    IDrawable fakeConsoleDrawable;
    boolean selectDrawer;

    public GameScreen (CatchBottleGame game) {

        this.game = game;
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);

        camera.update();

        gameMap = new CustomGameMap();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        layout = new  GlyphLayout(font,TAP_PLAY);
        fontW = layout.width;

        menuButton = new MyButton("menu.png",w/2 - 175/2, h-78, 175,75);
        //TODO: Bridge here
        fakeConsoleDrawable = new FakeConsoleDrawer();
        normalDrawable = new NormalDrawer(state,game,batch,camera,gameMap,font,fontW,layout,h,menuButton);
        drawer = new Drawer(normalDrawable);
        selectDrawer = true;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }



    @Override
    public void render(float delta) {
        //TODO: Bridge here
//        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
//        {
//            if (state == State.Paused)
//            {
//                state = State.Running;
//            }
//            else{
//                state = State.Paused;
//            }
//        }
//        if (state == state.Running) {
//            Gdx.gl.glClearColor(1, 0, 0, 1);
//            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//            camera.update();
//            gameMap.update(Gdx.graphics.getDeltaTime());
//            gameMap.render(camera, batch);
//            game.batch.begin();
//            menuButton.draw(game.batch);
//            game.batch.end();
//        }
//        else {
//            game.batch.begin();
//            font.draw(game.batch,layout,(camera.viewportWidth - fontW )/2,h/2);
//            game.batch.end();
//        }
        camera.update();
        gameMap.update(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)){
            selectDrawer = !selectDrawer;
        }
        if (selectDrawer){
            drawer.setDrawable(normalDrawable);
            drawer.render();
        }
        else {
            drawer.setDrawable(fakeConsoleDrawable);
            drawer.render();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public void dispose () {
        batch.dispose();
        gameMap.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 tmp = new Vector3(screenX, screenY, 0);
        camera.unproject(tmp);

        if (menuButton.wasClicked(tmp.x,tmp.y))
        {

            game.setScreen(game.menuScreen);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 tmp = new Vector3(screenX, screenY, 0);
        camera.unproject(tmp);
        if (menuButton.wasClicked(tmp.x,tmp.y))
        {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        }
        else{
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
