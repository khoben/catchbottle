package com.khoben.cb.patterns.Iterator;

import com.khoben.cb.entities.Entity;

import java.util.ArrayList;

/**
 * Created by extless on 22.10.2017.
 */

public class MyArray<T> implements IArray {

    ArrayList<T> objects;

    public MyArray(ArrayList<T> objects) {
        objects = new ArrayList<T>();
        this.objects = objects;
    }

    public void add(T o){
        objects.add(o);
    }

    public void remove(T o){
        objects.remove(o);
    }

    @Override
    public Iterator getIterator() {
        return new MyIterator(objects);
    }


}
