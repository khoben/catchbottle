package com.khoben.cb.patterns.Visitor;

import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.players.Player;

public interface Visitor {
    void visit(Bottle bottle);
    void visit(Player player);
}


