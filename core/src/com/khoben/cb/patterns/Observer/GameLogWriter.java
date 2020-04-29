package com.khoben.cb.patterns.Observer;

import java.util.List;

public class GameLogWriter implements Observer {

    @Override
    public void handleEvent(long aliveTime, int score, int jumps) {
        System.out.println(String.format(">> [TIME ALIVE]: %d.0s\t[BOTTLES CATCHED]: %d\t[AMOUNT JUMPS]: %d <<", aliveTime,score,jumps));
    }
}
