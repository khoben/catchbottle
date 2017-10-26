package com.khoben.cb.patterns.Decorator;

/**
 * Created by extle on 23.10.2017.
 */

public class AmountCollectedBottles implements IAmountCollectedBottles{
    int countBottles;

    public AmountCollectedBottles() {
        countBottles = 0;
    }

    public void SetAmount(int c){
        countBottles = c;
    }


    @Override
    public String getFinalString() {
        return String.valueOf(countBottles);
    }
}
