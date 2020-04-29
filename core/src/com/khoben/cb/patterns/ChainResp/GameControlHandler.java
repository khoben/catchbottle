package com.khoben.cb.patterns.ChainResp;

import com.badlogic.gdx.Input;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Command.ControlGame;

import java.io.IOException;

/**
 * Created by extle on 20.12.2017.
 */

public class GameControlHandler extends KeyHandler {

    final String filename = "save_1";


    public GameControlHandler() {
        super();
    }

    public GameControlHandler(KeyHandler successor) {
        super(successor);
    }

    @Override
    public void handleRequest(int keycode, GameMap map) throws IOException {
        switch (keycode) {
            case Input.Keys.S:
                map.save(filename);
                break;
            case Input.Keys.L:
                map.load(filename);
                break;
            default:
                if (successor != null)
                    successor.handleRequest(keycode, map);
        }
    }
}
