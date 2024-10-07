package com.github.puzzle.paradox.core;

import com.github.puzzle.paradox.core.terminal.PPLTerminalConsole;
import com.github.puzzle.paradox.loader.Version;
import com.github.puzzle.paradox.game.command.Commands;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import finalforeach.cosmicreach.util.logging.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class PuzzlePL {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("Puzzle PL");
    public static final Version VERSION = new Version(1,0,0, Version.VersionType.ALPHA);
    public PuzzlePL(){
        Logger.info("Loading Paradox");
    }
    public void init(){
        Commands.register();
    }

    public void initConsoleListener(){
        Thread thread = new Thread("Console Handler"){
            public void run() {
                try {
                  System.in.available();
                } catch (IOException e) {
                    return;
                }
                new PPLTerminalConsole(ServerSingletons.server).start();

            }

        };
        thread.setDaemon(true);
        thread.start();

    }
}
