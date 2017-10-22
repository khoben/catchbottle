package com.khoben.cb.patterns.Composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by extless on 22.10.2017.
 */

public class Composite implements Component {
    private List<Component> components;

    Composite(){
        components = new ArrayList<Component>();
    }

    @Override
    public void operation() {
        for(Component c: components){
            c.operation();
        }
    }

    public void add(Component c){
        components.add(c);
    }

    public void remove(Component c){
        components.remove(c);
    }
}
