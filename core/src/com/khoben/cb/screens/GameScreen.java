package com.khoben.cb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.khoben.cb.CatchBottleGame;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.CustomGameMap;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Bridge.Drawer;
import com.khoben.cb.patterns.Bridge.FakeConsoleDrawer;
import com.khoben.cb.patterns.Bridge.IDrawable;
import com.khoben.cb.patterns.Bridge.NormalDrawer;
import com.khoben.cb.patterns.ChainResp.GameControlHandler;
import com.khoben.cb.patterns.ChainResp.KeyHandler;
import com.khoben.cb.patterns.ChainResp.PlayerMovementHandler;
import com.khoben.cb.patterns.Observer.GameLogHandler;
import com.khoben.cb.patterns.Observer.GameLogWriter;
import com.khoben.cb.patterns.State.DrawerDrawing;
import com.khoben.cb.patterns.State.DrawerPause;
import com.khoben.cb.screens.Button.MyButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javax.swing.Timer;


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

    final String saveFileName = "save_1";

    Drawer drawer;


    public enum State {
        Running,
        Paused
    }

    State state = State.Running;

    MyButton menuButton;

    IDrawable normalDrawable;
    IDrawable fakeConsoleDrawable;
    boolean selectDrawer;

    Timer timer;


    public static int roundTime;
    public static int fullRoundTime;

    GameLogHandler logHandler;


    KeyHandler gameControlHandler;
    KeyHandler playerControlHandler;
    int keyCode;


    public GameScreen(CatchBottleGame game) throws IllegalAccessException, InstantiationException {

        this.game = game;
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        camera.update();

        //gameMap = new CustomGameMap();

        gameMap = new CustomGameMap.Builder()
                .setFontSize(30)
                .setTimeToGame(0)
                .finalBuild();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        layout = new GlyphLayout(font, TAP_PLAY);
        fontW = layout.width;

        menuButton = new MyButton("menu.png", w / 2 - 175 / 2, h - 78, 175, 75);
        //TODO: Bridge here
        fakeConsoleDrawable = new FakeConsoleDrawer(gameMap, camera);
        normalDrawable = new NormalDrawer(state, game, batch, camera, gameMap, font, fontW, layout, h, menuButton);
        drawer = new Drawer(normalDrawable);


        roundTime = 0;
        fullRoundTime = 0;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roundTime++;
                fullRoundTime++;
                if (gameMap.getTimeToGame() != 0 && roundTime > gameMap.getTimeToGame()) {
                    gameMap.gameStatus = GameMap.GameStatus.ENDED;
                }
            }
        });


        logHandler = new GameLogHandler();
        logHandler.addObserver(new GameLogWriter());

        state = State.Running;

        selectDrawer = true;


        gameControlHandler = new GameControlHandler();
        playerControlHandler = new PlayerMovementHandler();
        gameControlHandler.setSuccessor(playerControlHandler);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        timer.start();
    }


    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {

            try {
                gameControlHandler.handleRequest(keyCode, gameMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(Input.Keys.toString(keyCode));
        }



//        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
//            //TODO: SAVE GAME
//            controlGame.addCommand(new SaveGameCommand(gameMap));
//        }
//
//        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
//            //TODO: LOAD GAME
//            controlGame.addCommand(new OpenGameCommand(gameMap));
//        }
//
//        try {
//            controlGame.run();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //TODO: Bridge here
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            if (state == State.Paused) {
                state = State.Running;
                normalDrawable.setState(normalDrawable.getSomeState(DrawerDrawing.class));
                timer.start();
            } else {
                state = State.Paused;
                normalDrawable.setState(normalDrawable.getSomeState(DrawerPause.class));
                timer.stop();
            }
        }

        if (state == State.Running ) {

            camera.update();
            gameMap.update(Gdx.graphics.getDeltaTime());

            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                selectDrawer = !selectDrawer;
            }
            if (selectDrawer) {
                drawer.setDrawable(normalDrawable);
                try {
                    drawer.render();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                drawer.setDrawable(fakeConsoleDrawable);
                try {
                    drawer.render();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (gameMap.gameStatus == GameMap.GameStatus.NEXT_LEVEL) {
                gameMap.sEntities.player.playerStateAnime = Player.PlayerStateAnime.ACHIEVED;
                gameMap.gameStatus = GameMap.GameStatus.IN_PROGRESS;
                gameMap.sEntities.mainComposite.clear();
                gameMap.sEntities.mainComposite.components.clear();
                gameMap.resetEntities();
                gameMap.bottlesFromSky.clear();
                roundTime = 0;
            } else if (gameMap.gameStatus == GameMap.GameStatus.ENDED) {

                logHandler.setParams(fullRoundTime, gameMap.getPoints(), gameMap.sEntities.player.getJumps());
                gameMap.sEntities.player.setJumps(0);
                game.setScreen(game.gameOverScreen);
                roundTime = 0;
                fullRoundTime = 0;

                gameMap.setPoints(0);
                //gameStatus = GameStatus.IN_PROGRESS;
                gameMap.sEntities.mainComposite.clear();
                gameMap.sEntities.mainComposite.components.clear();
                gameMap.sEntities.entities.clear();
                gameMap.bottlesFromSky.clear();
                //this.resetEntities();
                //gameMap.resetNPC();
            }

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
        timer.stop();
    }

    public void dispose() {
        batch.dispose();
        gameMap.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        this.keyCode = keycode;
        return true;
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

        if (menuButton.wasClicked(tmp.x, tmp.y)) {

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
        if (menuButton.wasClicked(tmp.x, tmp.y)) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        } else {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
