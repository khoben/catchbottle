package com.khoben.cb.patterns.Iterator;

/**
 * Created by extless on 22.10.2017.
 */

public interface Iterator<T> {
    T first();
    T next();
    T getCurrrentElement();
    boolean hasNext();
}
