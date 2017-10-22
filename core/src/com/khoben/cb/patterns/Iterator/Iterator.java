package com.khoben.cb.patterns.Iterator;

/**
 * Created by extless on 22.10.2017.
 */

public interface Iterator {
    Object first();
    Object next();
    Object getCurrrentElement();
    boolean hasNext();
}
