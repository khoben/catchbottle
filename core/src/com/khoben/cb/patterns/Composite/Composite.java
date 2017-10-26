package com.khoben.cb.patterns.Composite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.bottles.Pair;
import com.khoben.cb.entities.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by extless on 22.10.2017.
 */
//TODO: multi-level container
public class Composite implements Component {
    public List<Component> components;

    public Composite(){
        components = new ArrayList<Component>();
    }
    public Composite(List<Component> c){
        components = c;
    }

    public void add(Component c){
        components.add(c);
    }

    public void remove(Component c){
        components.remove(c);
    }

    @Override
    public void clear(){
        for(Component c: components){
            c.clear();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        for(Component c: components){
            c.render(batch);
        }
    }

    @Override
    public void update(float delta, float gravity) {
        for(Component c: components){
            c.update(delta, gravity);
        }
    }

    @Override
    public Pair<Bottle,Boolean> operation(Player p) {
        Pair<Bottle,Boolean> f = new Pair<Bottle, Boolean>(null,false);
        for(Component c: components){
            f = c.operation(p);
            if (f.getRight() == true)
                break;
        }
        return f;
    }

}
