package com.khoben.cb.patterns.Strategy;

import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;
import com.khoben.cb.entities.bottles.Bottle;

import java.util.List;

/**
 * Created by extle on 21.12.2017.
 */

public class NormalBottleStrategy extends Strategy {
    public NormalBottleStrategy(List<BottleFromSky> bottleList) {
        super(bottleList);
        gravityScale = 1.0f;
    }

    @Override
    public void perform() {
        for (Bottle b : bottles) {
            b.setVelocityY(-3.0f);
        }
    }
}
