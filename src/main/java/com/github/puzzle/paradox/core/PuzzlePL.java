package com.github.puzzle.paradox.core;

import com.github.puzzle.game.commands.CommandSource;
import com.github.puzzle.paradox.core.permissions.Permission;
import com.github.puzzle.paradox.core.permissions.PermissionGroup;
import com.github.puzzle.paradox.core.terminal.PPLTerminalConsole;
import com.github.puzzle.paradox.game.command.Commands;
import com.github.puzzle.paradox.game.server.ParadoxServerSettings;
import com.github.puzzle.paradox.loader.Version;
import com.mojang.brigadier.CommandDispatcher;
import finalforeach.cosmicreach.TickRunner;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.accounts.AccountOffline;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzlePL {

    Map<String,Permission> allPermissions;

    private static final Logger LOGGER = LoggerFactory.getLogger("Puzzle Paradox");
    public static final Version VERSION = new Version(1,1,10, Version.VersionType.ALPHA);

    public final PropertiesConfiguration serverConfig;
    public final FileBasedConfigurationBuilder<PropertiesConfiguration> configBuilder;

    public static CommandDispatcher<CommandSource> clientDispatcher = new CommandDispatcher<>(); //to separate server side chat commands

    public static Account SERVER_ACCOUNT = null;

    public Thread consoleThread;
    public PuzzlePL(){
        SERVER_ACCOUNT =  new AccountOffline();
        SERVER_ACCOUNT.setUsername("Server");
        SERVER_ACCOUNT.setUniqueId("Server");
        LOGGER.info("Loading Paradox");
        allPermissions = new HashMap<>();
        try {
            boolean propExists = true;
            Path path = Path.of("server.properties");
            if(!Files.exists(path)) {
                propExists = false;
                Files.createFile(path);
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
    public Permission getPermission(String permissionName){
        return allPermissions.get(permissionName);
    }
    public void addPermission(Permission permission){
        assert(permission != null);
        allPermissions.putIfAbsent(permission.getPermissionVarString(), permission);
    }

    private void writeToNewConfig(){
        serverConfig.addProperty("server.port",47137);
        serverConfig.addProperty("commands.enabled",true);
        serverConfig.addProperty("server.renderdistance", 10);
        serverConfig.addProperty("iteraction.canplaceblock",true);
        serverConfig.addProperty("iteraction.canbreakblock",true);
        serverConfig.addProperty("iteraction.shouldexplodec4",true);
        serverConfig.addProperty("server.joinmessage","");
        serverConfig.addProperty("server.canchat", true);
        serverConfig.addProperty("server.anticheat", true);
        serverConfig.addProperty("server.isoffline", false);
        serverConfig.addProperty("rcon.enabled", false);
        serverConfig.addProperty("rcon.port", 47138);
        serverConfig.addProperty("rcon.password", RandomStringUtils.randomAlphanumeric(8));
        serverConfig.addProperty("world.worldtype","base:earth");
        serverConfig.addProperty("itch.apikey","");
    }
    public void init(){
        ParadoxServerSettings.initSetting();
        Commands.registerConsoleCommands();
        Commands.registerClientCommands();
        PermissionGroup.DEFAULT_GROUP = new PermissionGroup("default", "default.command.msg","default.command.setname","default.command.tpr","default.command.tpa");
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
                new PPLTerminalConsole(ServerSingletons.SERVER).start();
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        };
        consoleThread.setDaemon(true);
        consoleThread.start();

    }
}
