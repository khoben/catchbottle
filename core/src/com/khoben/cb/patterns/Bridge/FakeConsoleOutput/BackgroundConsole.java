package com.khoben.cb.patterns.Bridge.FakeConsoleOutput;

/**
 * Created by extle on 19.11.2017.
 */

public class BackgroundConsole extends GameObjectConsole {
    public BackgroundConsole() {
        setSymbol('#');
    }

    public BackgroundConsole(char symbol) {
        setSymbol(symbol);
    }

    // Add horizontal line of walls
    public void addWallsRow(GameScreenConsole screen, BackgroundConsole wall, int rowNumber) {
        for (int i = 0; i < screen.getScreenWidth(); i++) {
            screen.setObjectOnLocation(wall, i, rowNumber);
        }
    }

    // Add vertical line of walls
    public void addWallsColumn(GameScreenConsole screen, BackgroundConsole wall, int columnNumber) {
        for (int i = 0; i < screen.getScreenHeight(); i++) {
            screen.setObjectOnLocation(wall, columnNumber, i);
        }
    }
}
