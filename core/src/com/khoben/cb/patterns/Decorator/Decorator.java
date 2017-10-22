package com.khoben.cb.patterns.Decorator;

import com.khoben.cb.patterns.Composite.Component;

/**
 * Created by extless on 22.10.2017.
 */

public class Decorator implements Component{

    Component component;

    public Decorator(Component c) {
        this.component = c;
    }

    @Override
    public void operation() {
        //decore
        component.operation();
        //
    }
}
