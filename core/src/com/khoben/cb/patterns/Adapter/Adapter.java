package com.khoben.cb.patterns.Adapter;

import com.khoben.cb.patterns.Iterator.IArray;
import com.khoben.cb.patterns.Iterator.MyArray;
import com.khoben.cb.patterns.Iterator.MyIterator;

/**
 * Created by extless on 22.10.2017.
 */

public class Adapter<T> extends MyArray<T> implements IArray<T> {
    @Override
    public MyIterator<T> getIterator() {
       return getMyIterator();
    }
    //TODO: class Adapter extends SomeClass impelements UnsupportedInterfaceForClass
}
