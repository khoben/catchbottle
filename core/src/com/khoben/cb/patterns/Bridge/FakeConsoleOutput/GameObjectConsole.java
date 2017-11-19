package com.khoben.cb.patterns.Bridge.FakeConsoleOutput;

/**
 * Created by extle on 19.11.2017.
 */

public class GameObjectConsole {
    private int x, y;
    private char symbol;

    // Getters
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public char getSymbol() {
        return symbol;
    }

    // Setters
    public void setX(int newLocation) {
        this.x = newLocation;
    }

    public void setY(int newLocation) {
        this.y = newLocation;
    }

    public void setSymbol(char newSymbol) {
        this.symbol = newSymbol;
    }
}
