package com.khoben.cb.patterns.Bridge.FakeConsoleOutput;

import com.khoben.cb.entities.bottles.Bottle;

import java.util.Scanner;

/**
 * Created by extle on 19.11.2017.
 */

public class GameMapConsole {
    // Constants
    final int SCREEN_WIDTH = 20; // Columns
    final int SCREEN_HEIGHT = 10; // Rows
    final int SNAKE_STARTING_X = SCREEN_WIDTH / 2;
    final int SNAKE_STARTING_Y = SCREEN_HEIGHT / 2;

    GameScreenConsole screen;
    BottleConsole bottle;
    BackgroundConsole background;
    PlayerConsole player;

    public GameMapConsole(){
        screen = new GameScreenConsole(SCREEN_WIDTH,SCREEN_HEIGHT);
        screen.InitScreen();
        background = new BackgroundConsole('#');
        background.addWallsRow(screen,background,0);
        background.addWallsRow(screen,background,screen.getScreenHeight() - 1);

        background.addWallsColumn(screen,background,0);
        background.addWallsColumn(screen,background,screen.getScreenWidth() - 1);

        player = new PlayerConsole('X', SNAKE_STARTING_X,SNAKE_STARTING_Y);
        screen.setObjectOnLocation(player,player.getX(),player.getY());

        bottle = new BottleConsole('@');
        bottle.addRandomFood(screen,bottle);
    }

    public void update() {
        // Input from player
        Scanner scanner = new Scanner(System.in);
        char input;

        // The game logic starts here
        boolean isRunning = true;

        while (isRunning) {
            screen.PrintScreen();
            // Get input from player and do something
            switch (input = scanner.nextLine().charAt(0)) {
                case 'a':
                    player.moveLeft(screen, player);
                    break;
                case 'd':
                    player.moveRight(screen, player);
                    break;
                case 'w':
                    player.moveUp(screen, player);
                    break;
                case 's':
                    player.moveDown(screen, player);
                    break;
            }
        }
    }
}
