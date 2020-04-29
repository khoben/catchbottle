package com.khoben.cb.patterns.Mediator;

import com.khoben.cb.entities.Entity;
import com.khoben.cb.entities.bottles.Bottle;

/**
 * Created by extle on 21.12.2017.
 */

public interface Mediator {
    void send(Colleague e);
}
