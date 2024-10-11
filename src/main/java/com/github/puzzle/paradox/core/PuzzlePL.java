package com.github.puzzle.paradox.core;

import com.github.puzzle.paradox.core.terminal.PPLTerminalConsole;
import com.github.puzzle.paradox.loader.Version;
import com.github.puzzle.paradox.game.command.Commands;
import com.ibasco.agql.protocols.valve.source.query.rcon.RconAuthenticator;
import com.ibasco.agql.protocols.valve.source.query.rcon.SourceRconClient;
import com.ibasco.agql.protocols.valve.source.query.rcon.SourceRconOptions;
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

    public final Configuration serverConfig;

    public static Account SERVER_ACCOUNT = null;
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
            FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                    new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("server.properties")
                                    .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
            serverConfig = builder.getConfiguration();
            if(!propExists){
                serverConfig.setProperty("server.port",47137);
                builder.save();
            }
        } catch (ConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
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
