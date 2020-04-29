package com.khoben.cb.patterns.Mediator;

/**
 * Created by extle on 25.12.2017.
 */

public interface Colleague {
    void receive(Colleague c);
    void send();
}
