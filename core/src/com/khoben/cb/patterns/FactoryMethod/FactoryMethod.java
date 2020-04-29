package com.khoben.cb.patterns.FactoryMethod;

import com.khoben.cb.entities.bottles.BigBottle;
import com.khoben.cb.entities.bottles.IBottle;
import com.khoben.cb.entities.bottles.MidBottle;
import com.khoben.cb.entities.bottles.SmallBottle;

/**
 * Created by extle on 20.11.2017.
 */

public class FactoryMethod implements IFactoryMethodBottle {

    @Override
    public IBottle createBigBottle() {
        return new BigBottle();
    }

    @Override
    public IBottle createMidBottle() {
        return new MidBottle();
    }

    @Override
    public IBottle createSmallBottle() {
        return new SmallBottle();
    }
}
