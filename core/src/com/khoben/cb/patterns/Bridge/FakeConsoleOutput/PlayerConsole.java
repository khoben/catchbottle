package com.khoben.cb.patterns.Bridge.FakeConsoleOutput;

import com.khoben.cb.entities.players.Player;

/**
 * Created by extle on 19.11.2017.
 */

public class PlayerConsole extends GameObjectConsole {
    public PlayerConsole(char symbol, int xStartingLocation, int yStartingLocation) {
        setSymbol(symbol);
        setX(xStartingLocation);
        setY(yStartingLocation);
    }

    public void moveLeft(GameScreenConsole screen, PlayerConsole player) {
        player.setX(getX() - 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX() + 1, player.getY());
    }

    public void moveRight(GameScreenConsole screen, PlayerConsole player) {
        player.setX(getX() + 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX() - 1, player.getY());
    }

    public void moveUp(GameScreenConsole screen, PlayerConsole player) {
        player.setY(getY() - 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX(), player.getY() + 1);
    }

    public void moveDown(GameScreenConsole screen, PlayerConsole player) {
        player.setY(getY() + 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX(), player.getY() - 1);
    }
}
