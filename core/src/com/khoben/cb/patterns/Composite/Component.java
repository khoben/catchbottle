package com.khoben.cb.patterns.Composite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.bottles.Pair;
import com.khoben.cb.entities.players.Player;

/**
 * Created by extless on 22.10.2017.
 */

public interface Component {
    public void render(SpriteBatch batch);
    public void update(float delta, float gravity);
    public Pair<Bottle,Boolean> operation(Player p);
    public void clear();
}
