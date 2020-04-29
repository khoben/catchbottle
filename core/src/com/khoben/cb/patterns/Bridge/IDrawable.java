package com.khoben.cb.patterns.Bridge;

/**
 * Created by extle on 19.11.2017.
 */

public interface IDrawable {
    void render() throws IllegalAccessException, InstantiationException;
    void setState(IDrawable state);
    IDrawable getSomeState(Class classState);
}
