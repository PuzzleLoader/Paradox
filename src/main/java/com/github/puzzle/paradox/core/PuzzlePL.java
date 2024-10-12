package com.github.puzzle.paradox.core;

import com.github.puzzle.paradox.core.terminal.PPLTerminalConsole;
import com.github.puzzle.paradox.game.server.ParadoxServerSettings;
import com.github.puzzle.paradox.loader.Version;
import com.github.puzzle.paradox.game.command.Commands;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.accounts.AccountOffline;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.combined.CombinedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.core.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;

public class PuzzlePL {
    private static final Logger LOGGER = LoggerFactory.getLogger("Puzzle Paradox");
    public static final Version VERSION = new Version(1,0,0, Version.VersionType.ALPHA);

    public final PropertiesConfiguration serverConfig;
    public final FileBasedConfigurationBuilder<PropertiesConfiguration> configBuilder;

    public static Account SERVER_ACCOUNT = null;

    public Thread consoleThread;
    public PuzzlePL(){
        SERVER_ACCOUNT =  new AccountOffline();
        SERVER_ACCOUNT.setUsername("Server");
        SERVER_ACCOUNT.setUniqueId("Server");
        LOGGER.info("Loading Paradox");

        try {
            boolean propExists = true;
            if(!Files.exists(Path.of("server.properties"))) {
                propExists = false;
                Files.createFile(Path.of("server.properties"));
            }
            Parameters params = new Parameters();
            configBuilder =
                    new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("server.properties")
                                    .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
            serverConfig = configBuilder.getConfiguration();
            if(!propExists){
                writeToNewConfig();
                configBuilder.save();
            }
        } catch (ConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToNewConfig(){
        serverConfig.addProperty("server.port",47137);
        serverConfig.addProperty("commands.enabled",true);
        serverConfig.addProperty("iteraction.canplaceblock",true);
        serverConfig.addProperty("iteraction.canbreakblock",true);
        serverConfig.addProperty("iteraction.shouldexplodec4",true);
        serverConfig.addProperty("server.joinmessage","");
        serverConfig.addProperty("server.canchat", true);
    }
    public void init(){
        ParadoxServerSettings.initSetting();
        Commands.registerConsoleCommands();
        Commands.registerClientCommands();
    }

    public void exit() {
        ParadoxServerSettings.writeSetting();
    }

    public void initConsoleListener(){
        consoleThread = new Thread("Console Handler"){
            public void run() {
                try {
                  System.in.available();
                } catch (IOException e) {
                    return;
                }
                new PPLTerminalConsole(ServerSingletons.server).start();

            }

        };
        consoleThread.setDaemon(true);
        consoleThread.start();

    }
}
