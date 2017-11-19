package com.khoben.cb.patterns.Bridge.FakeConsoleOutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.khoben.cb.map.GameMap;

/**
 * Created by extle on 19.11.2017.
 */

public class GameMapConsole {
    // Constants
    final int SCREEN_WIDTH = 50; // Columns
    final int SCREEN_HEIGHT = 15; // Rows
    final int PLAYER_X = SCREEN_WIDTH / 2;
    final int PLAYER_Y = SCREEN_HEIGHT / 2;

    GameScreenConsole screen;
    BottleConsole bottle;
    BackgroundConsole background;
    PlayerConsole player;

    GameMap gameMap;

    public GameMapConsole(GameMap gameMap){
        gameMap = gameMap;
        screen = new GameScreenConsole(SCREEN_WIDTH,SCREEN_HEIGHT);
        screen.InitScreen();
        background = new BackgroundConsole('#');
        background.addWallsRow(screen,background,0);
        background.addWallsRow(screen,background,screen.getScreenHeight() - 1);

        background.addWallsColumn(screen,background,0);
        background.addWallsColumn(screen,background,screen.getScreenWidth() - 1);

        player = new PlayerConsole('X', PLAYER_X, PLAYER_Y);
        screen.setObjectOnLocation(player,player.getX(),player.getY());

        bottle = new BottleConsole('@');
        bottle.addRandomFood(screen,bottle);
    }

    public void update() {
        // Input from player

            // Get input from player and do something
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            screen.PrintScreen();
            player.moveLeft(screen, player);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            screen.PrintScreen();
            player.moveRight(screen, player);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            screen.PrintScreen();
            player.moveUp(screen, player);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            screen.PrintScreen();
            player.moveDown(screen, player);
        }
        }
}
