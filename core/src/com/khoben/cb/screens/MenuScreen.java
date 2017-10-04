package com.khoben.cb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.khoben.cb.CatchBottleGame;


/**
 * Created by extle on 01.10.2017.
 */

public class MenuScreen implements Screen {

    CatchBottleGame game;
    OrthographicCamera camera;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;

    Texture backgroundTexture;

    final static float w = Gdx.graphics.getWidth();
    final static float h = Gdx.graphics.getHeight();
    final static String TAP_PLAY = "TAP TO PLAY";
    GlyphLayout layout;
    float fontW;
    int sourceX;
    int sourceY;

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
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();
        sourceX+=2;
        sourceY+=2;

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture,0,0,sourceX,sourceY,(int)w,(int)h);
        font.draw(game.batch,layout,(camera.viewportWidth - fontW )/2,h/2);
        game.batch.end();

        if (Gdx.input.isTouched())
        {
            game.setScreen(new GameScreen(game));
            dispose();
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

    @Override
    public void dispose() {

    }
}
