package com.khoben.cb.patterns.Iterator;

import com.khoben.cb.entities.Entity;

import java.util.ArrayList;

/**
 * Created by extless on 22.10.2017.
 */

public class MyArray<T>  {

    ArrayList<T> objects;

    public MyArray() {
        objects = new ArrayList<T>();
    }

    public void add(T o){
        objects.add(o);
    }

    public void remove(T o){
        objects.remove(o);
    }

    public void clear(){objects.clear();}

    public int size(){
        return objects.size();
    }

    public void remove(int idx){
        objects.remove(idx);
    }

    public MyIterator<T> getMyIterator() {
        return new MyIterator<T>(objects);
    }


}
