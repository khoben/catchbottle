package com.khoben.cb.patterns.Observer;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
    void setParams(long time, int score, int jumps);
}
