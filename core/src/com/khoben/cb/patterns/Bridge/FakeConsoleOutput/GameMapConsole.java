package com.khoben.cb.patterns.Bridge.FakeConsoleOutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.khoben.cb.map.GameMap;

/**
 * Created by extle on 19.11.2017.
 */

public class GameMapConsole {
    // Constants
    final int SCREEN_WIDTH = 50; // Columns
    final int SCREEN_HEIGHT = 15; // Rows

    GameScreenConsole screen;
    BottleConsole bottle;
    BackgroundConsole background;
    PlayerConsole player;


    GameMap gameMap;
    OrthographicCamera camera;

    float ratio_y;
    float ratio_x;

    public GameMapConsole(GameMap gameMap, OrthographicCamera camera){
        this.gameMap = gameMap;
        this.camera = camera;
        screen = new GameScreenConsole(SCREEN_WIDTH,SCREEN_HEIGHT);
        screen.InitScreen();
        background = new BackgroundConsole('#');
        background.addWallsRow(screen,background,0);
        background.addWallsRow(screen,background,screen.getScreenHeight() - 1);

        background.addWallsColumn(screen,background,0);
        background.addWallsColumn(screen,background,screen.getScreenWidth() - 1);

        ratio_y = (float)SCREEN_HEIGHT / camera.viewportHeight;
        ratio_x = (float)SCREEN_WIDTH / camera.viewportWidth;

        player = new PlayerConsole('P', (int)(gameMap.sEntities.player.getX()*ratio_x), (int)(gameMap.sEntities.player.getY()*ratio_y));
        screen.setObjectOnLocation(player,player.getX(),player.getY());

//        bottle = new BottleConsole('B');
//        bottle.addRandomFood(screen,bottle);
    }

    public void update() {
        // Input from player

            // Get input from player and do something
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){

            screen.PrintScreen();
            //player.moveLeft(screen, player);
            screen.ClearScreenLocation(player.getX(), player.getY());
            player.setX((int)(gameMap.sEntities.player.getX()*ratio_x));
            player.setY((int)(gameMap.sEntities.player.getY()*ratio_y));
            screen.setObjectOnLocation(player,player.getX(),player.getY());



            System.out.println(gameMap.sEntities.player.getX()*ratio_x);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            screen.PrintScreen();
            //player.moveRight(screen, player);
            screen.ClearScreenLocation(player.getX(), player.getY());
            player.setX((int)(gameMap.sEntities.player.getX()*ratio_x));
            player.setY((int)(gameMap.sEntities.player.getY()*ratio_y));
            screen.setObjectOnLocation(player,player.getX(),player.getY());


        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            screen.PrintScreen();
            //player.moveUp(screen, player);
            screen.ClearScreenLocation(player.getX(), player.getY());
            player.setX((int)(gameMap.sEntities.player.getX()*ratio_x));
            player.setY((int)(gameMap.sEntities.player.getY()*ratio_y));
            screen.setObjectOnLocation(player,player.getX(),player.getY());

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            screen.PrintScreen();
            //player.moveDown(screen, player);
            screen.ClearScreenLocation(player.getX(), player.getY());
            screen.setObjectOnLocation(player,(int)(gameMap.sEntities.player.getX()*ratio_x),(int)(gameMap.sEntities.player.getY()*ratio_y));
        }
        }
}
