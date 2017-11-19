package com.khoben.cb.patterns.Bridge.FakeConsoleOutput;

/**
 * Created by extle on 19.11.2017.
 */

public class GameScreenConsole {
    private int width, height;
    private char[][] screenMatrix;

    public GameScreenConsole(int width, int height) {
        this.width = width;
        this.height = height;
        this.screenMatrix = new char[this.height][this.width];
    }

    // Fill array with dots
    public void InitScreen() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.screenMatrix[i][j] = '.';
            }
        }
    }

    // Print the screen to console
    public void PrintScreen() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(this.screenMatrix[i][j]);
            }
            System.out.println();
        }
    }

    //Screen clear location
    public void ClearScreenLocation(int x, int y) {
        this.screenMatrix[y][x] = '.';
    }

    // Getters
    public int getScreenWidth() {
        return this.width;
    }

    public int getScreenHeight() {
        return this.height;
    }

    public char getObjectOnLocation(int x, int y) {
        return this.screenMatrix[y][x];
    }

    // Setters
    public void setObjectOnLocation(GameObjectConsole object, int x, int y) {
        this.screenMatrix[y][x] = object.getSymbol();
    }
}
