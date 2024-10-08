package com.github.puzzle.paradox.core;

import com.github.puzzle.paradox.core.terminal.PPLTerminalConsole;
import com.github.puzzle.paradox.loader.Version;
import com.github.puzzle.paradox.game.command.Commands;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.accounts.AccountOffline;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class PuzzlePL {
    private static final Logger LOGGER = LoggerFactory.getLogger("Puzzle Paradox");
    public static final Version VERSION = new Version(1,0,0, Version.VersionType.ALPHA);

    public static Account SERVER_ACCOUNT = null;
    public PuzzlePL(){
        SERVER_ACCOUNT =  new AccountOffline();
        SERVER_ACCOUNT.setUsername("Server");
        SERVER_ACCOUNT.setUniqueId("Server");
        LOGGER.info("Loading Paradox");
    }
    public void init(){
        Commands.registerConsoleCommands();
        Commands.registerClientCommands();
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
