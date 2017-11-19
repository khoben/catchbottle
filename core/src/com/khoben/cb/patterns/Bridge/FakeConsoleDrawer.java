package com.khoben.cb.patterns.Bridge;

import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Bridge.FakeConsoleOutput.GameMapConsole;
import com.khoben.cb.patterns.Bridge.FakeConsoleOutput.GameScreenConsole;

/**
 * Created by extle on 19.11.2017.
 */

public class FakeConsoleDrawer implements IDrawable{
    GameMapConsole gameMapConsole;
    public FakeConsoleDrawer(GameMap map)
    {
        gameMapConsole = new GameMapConsole(map);
    }
    @Override
    public void render() {
        gameMapConsole.update();
    }
}
