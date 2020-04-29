package com.khoben.cb.patterns.Memento;

import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;
import com.khoben.cb.patterns.Facade.SetupEntities;

import java.util.ArrayList;
import java.util.List;

public class Save {

    private int score;

    public Save(int score){
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
