package com.khoben.cb.patterns.Bridge.FakeConsoleOutput;

/**
 * Created by extle on 19.11.2017.
 */

public class BottleConsole extends GameObjectConsole {
    public BottleConsole(char symbol) {
        setSymbol(symbol);
    }

    // Add food to random location inside the matrix limits
    public void addRandomFood(GameScreenConsole screen, BottleConsole bottle) {
        screen.setObjectOnLocation(bottle, (int) (Math.random() * (screen.getScreenWidth() - 1)),
                (int) (Math.random() * (screen.getScreenHeight() - 1)));
    }
}
