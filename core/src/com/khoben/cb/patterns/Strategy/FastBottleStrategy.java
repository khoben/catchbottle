package com.khoben.cb.patterns.Strategy;

import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;

import java.util.List;

/**
 * Created by extle on 21.12.2017.
 */

public class FastBottleStrategy extends Strategy {
    public FastBottleStrategy(List<BottleFromSky> bottleList) {
        super(bottleList);
        gravityScale = 2.0f;
    }
}
