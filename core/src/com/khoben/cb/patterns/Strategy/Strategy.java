package com.khoben.cb.patterns.Strategy;

import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;
import com.khoben.cb.entities.bottles.Bottle;

import java.util.List;

/**
 * Created by extle on 20.12.2017.
 */

public abstract class Strategy implements Strategiable {
    float gravityScale;
    List<BottleFromSky> bottles;

    public Strategy(List<BottleFromSky> bottleList){
        this.bottles = bottleList;
    }

    @Override
    public void perform() {
        for (Bottle b : bottles) {
            b.setVelocityY(b.getVelocityY() * gravityScale);
        }
    }
}
