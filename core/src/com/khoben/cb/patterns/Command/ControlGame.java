package com.khoben.cb.patterns.Command;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Invoker
public class ControlGame {
    List<ICommand> commands;
    String filename;

    public ControlGame(String filename) {
        commands = new ArrayList<>();
        this.filename = filename;
    }

    public void addCommand(ICommand c) {
        commands.add(c);
    }

    public void removeCommand(ICommand c) {
        commands.remove(c);
    }

    public void run() throws IOException {
        for (ICommand c : commands) {
            c.command(filename);
        }
        commands.clear();
    }
}
