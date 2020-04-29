package com.khoben.cb.patterns.FactoryMethod;

import com.khoben.cb.entities.bottles.IBottle;
import com.khoben.cb.patterns.Composite.Component;

/**
 * Created by extle on 20.11.2017.
 */

public interface IFactoryMethodBottle {
    IBottle createBigBottle();
    IBottle createMidBottle();
    IBottle createSmallBottle();
}
