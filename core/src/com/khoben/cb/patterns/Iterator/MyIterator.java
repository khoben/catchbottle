package com.khoben.cb.patterns.Iterator;

import java.util.ArrayList;


/**
 * Created by extless on 22.10.2017.
 */

public class MyIterator<T> implements Iterator<T> {

    int idx = 0;
    ArrayList<T> objects;

    public MyIterator(ArrayList<T> objects) {
        this.objects = objects;
    }


    @Override
    public T first() {
        idx = 0;
        return objects.get(idx);
    }

    @Override
    public T next() {
        return objects.get(idx++);
    }

    @Override
    public T getCurrrentElement() {
        return objects.get(idx);
    }

    @Override
    public boolean hasNext() {
        return idx < objects.size();
    }
}
