package com.khoben.cb.patterns.Observer;


import java.util.ArrayList;
import java.util.List;

public class GameLogHandler implements Observable {

    long aliveTime;
    int score;
    int countJumps;

    List<Observer> observers;

    public GameLogHandler(){
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: observers){
            o.handleEvent(aliveTime,score,countJumps);
        }
    }

    @Override
    public void setParams(long time, int score, int jumps) {
        aliveTime = time;
        this.countJumps = jumps;
        this.score = score;
        notifyObservers();
    }
}
