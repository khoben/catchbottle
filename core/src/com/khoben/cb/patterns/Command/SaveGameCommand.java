package com.khoben.cb.patterns.Command;
import com.badlogic.gdx.Game;
import com.khoben.cb.map.GameMap;
import java.io.IOException;

//Concrete commnd

public class SaveGameCommand  implements ICommand{

    private GameMap map;

    public SaveGameCommand(GameMap map){
        this.map = map;
    }

    @Override
    public void command(String filename) throws IOException {
        map.save(filename);
    }
}
