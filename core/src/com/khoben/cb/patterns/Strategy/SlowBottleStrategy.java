package com.khoben.cb.patterns.Strategy;

import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;

import java.util.List;

/**
 * Created by extle on 21.12.2017.
 */

public class SlowBottleStrategy extends Strategy {
    public SlowBottleStrategy(List<BottleFromSky> bottleList) {
        super(bottleList);
        gravityScale = 0.1f;
    }
}
