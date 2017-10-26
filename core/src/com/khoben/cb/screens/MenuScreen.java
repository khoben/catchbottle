package com.khoben.cb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.khoben.cb.CatchBottleGame;
import com.khoben.cb.screens.Button.MyButton;



/**
 * Created by extle on 01.10.2017.
 */

public class MenuScreen implements Screen, InputProcessor {

    CatchBottleGame game;
    OrthographicCamera camera;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;

    Texture backgroundTexture;

    final static float w = Gdx.graphics.getWidth();
    final static float h = Gdx.graphics.getHeight();
    final static String TAP_PLAY = "CATCH BOTTLE";
    GlyphLayout layout;
    float fontW;
    int sourceX;
    int sourceY;

    float fontdeltaY = 0;
    boolean upFont = true;

    MyButton startButton;

    public MenuScreen(CatchBottleGame game) {

        this.game = game;

        backgroundTexture = new Texture(Gdx.files.internal("back.png"));
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        layout = new  GlyphLayout(font,TAP_PLAY);
        fontW = layout.width;
        sourceX = 0;
        sourceY = 0;

        startButton = new MyButton("start.png", w/2 - 350/2, h/2 - 150/2, 350,150);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();
        sourceX+=2;
        sourceY+=2;

        if (fontdeltaY>=5)
            upFont = false;
        else
            if (fontdeltaY<=-5)
                upFont = true;

        if (upFont == true)
            fontdeltaY+=0.75;
        else
            fontdeltaY-=0.75;

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture,0,0,sourceX,sourceY,(int)w,(int)h);
        font.draw(game.batch,layout,(camera.viewportWidth - fontW )/2,h - layout.height + fontdeltaY);
        startButton.draw(game.batch);
        game.batch.end();
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

    @Override
    public void dispose() {

    }

    Vector3 tp = new Vector3();
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
        if (button != Input.Buttons.LEFT)
            return false;
        Vector3 tmp = new Vector3(screenX, screenY, 0);
        camera.unproject(tmp);
        if (startButton.wasClicked(tmp.x,tmp.y))
        {
            game.setScreen(game.gameScreen);
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
        if (startButton.wasClicked(tmp.x,tmp.y))
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
