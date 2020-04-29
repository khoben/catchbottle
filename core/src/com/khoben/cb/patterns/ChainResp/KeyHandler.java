package com.khoben.cb.patterns.ChainResp;

import com.khoben.cb.map.GameMap;

import java.io.IOException;

/**
 * Created by extle on 20.12.2017.
 */

public abstract class KeyHandler {
    public KeyHandler successor;


    public abstract void handleRequest(int keycode, GameMap map) throws IOException;

    public KeyHandler(KeyHandler successor){
        this.successor = successor;
    }

    public KeyHandler(){

    }

    public KeyHandler getSuccessor() {
        return successor;
    }

    public void setSuccessor(KeyHandler successor) {
        this.successor = successor;
    }
}
