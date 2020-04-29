package com.khoben.cb.patterns.Command;

import java.io.IOException;

//Command
public interface ICommand {
    void command(String filename) throws IOException;
}
