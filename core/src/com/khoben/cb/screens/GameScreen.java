package com.khoben.cb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.khoben.cb.CatchBottleGame;
import com.khoben.cb.map.CustomGameMap;


/**
 * Created by extle on 01.10.2017.
 */

public class GameScreen implements Screen {

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
    int countBottles;

    public enum State{
        Running,
        Paused
    }

    State state = State.Running;


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
    }

    @Override
    public void show() {

    }



    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
        {
            if (state == State.Paused)
            {
                state = State.Running;
            }
            else{
                state = State.Paused;
            }
        }
        if (state == state.Running) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            gameMap.update(Gdx.graphics.getDeltaTime());
            gameMap.render(camera, batch);
        }
        else {
            game.batch.begin();
            font.draw(game.batch,layout,(camera.viewportWidth - fontW )/2,h/2);
            game.batch.end();
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

    }

    public void dispose () {
        batch.dispose();
        gameMap.dispose();
    }


}
