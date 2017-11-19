package com.khoben.cb.patterns.Bridge;

import com.khoben.cb.patterns.Bridge.FakeConsoleOutput.GameMapConsole;
import com.khoben.cb.patterns.Bridge.FakeConsoleOutput.GameScreenConsole;

/**
 * Created by extle on 19.11.2017.
 */

public class FakeConsoleDrawer implements IDrawable{
    GameMapConsole gameMapConsole = new GameMapConsole();
    @Override
    public void render() {
        gameMapConsole.update();
    }
}
