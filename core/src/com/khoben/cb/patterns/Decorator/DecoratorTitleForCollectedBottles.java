package com.khoben.cb.patterns.Decorator;

/**
 * Created by extle on 23.10.2017.
 */

public class DecoratorTitleForCollectedBottles extends Decorator {
    public DecoratorTitleForCollectedBottles(IAmountCollectedBottles c) {
        super(c);
    }

    @Override
    public String getFinalString() {
        return "Bottles Scored: "+ super.getFinalString();
    }
}
