package com.khoben.cb.patterns.Command;

import com.khoben.cb.map.GameMap;

import java.io.IOException;

//Concrete commnd

public class OpenGameCommand implements ICommand {
    private GameMap map;

    public OpenGameCommand(GameMap map){
        this.map = map;
    }

    @Override
    public void command(String filename) throws IOException {
        map.load(filename);
    }
}
