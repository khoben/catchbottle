package com.khoben.cb.patterns.Decorator;

/**
 * Created by extless on 22.10.2017.
 */

 public abstract class Decorator implements IAmountCollectedBottles{

    protected IAmountCollectedBottles component;

    public Decorator(IAmountCollectedBottles c) {
        this.component = c;
    }

    @Override
    public String getFinalString() {
        //TODO: Do decore bottle score
        return component.getFinalString();
        //
    }
}
