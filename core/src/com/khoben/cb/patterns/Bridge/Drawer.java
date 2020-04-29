package com.khoben.cb.patterns.Bridge;

/**
 * Created by extle on 19.11.2017.
 */

public class Drawer {
    IDrawable drawable;
    public Drawer(IDrawable drawable){
        this.drawable = drawable;
    }
    public void render() throws InstantiationException, IllegalAccessException {
        drawable.render();
    }
    public void setDrawable(IDrawable drawable){
        this.drawable = drawable;
    }
}
