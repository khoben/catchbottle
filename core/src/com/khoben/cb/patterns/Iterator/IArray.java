package com.khoben.cb.patterns.Iterator;

/**
 * Created by extless on 22.10.2017.
 */

public interface IArray<T> {
    MyIterator<T> getIterator();
    void clear();
    void remove(int i);
    void remove (T o);
    void add (T o);
    int size();
}
