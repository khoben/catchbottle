package com.khoben.cb.patterns.Bridge;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Bridge.FakeConsoleOutput.GameMapConsole;

/**
 * Created by extle on 19.11.2017.
 */

public class FakeConsoleDrawer implements IDrawable {
    GameMapConsole gameMapConsole;

    public FakeConsoleDrawer(GameMap map, OrthographicCamera camera) {
        gameMapConsole = new GameMapConsole(map, camera);
    }

    @Override
    public void render() {
        gameMapConsole.update();
    }

    @Override
    public void setState(IDrawable state) {

    }

    @Override
    public IDrawable getSomeState(Class classState) {
        return null;
    }
}
