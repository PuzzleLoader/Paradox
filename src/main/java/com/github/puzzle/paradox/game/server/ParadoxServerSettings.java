package com.github.puzzle.paradox.game.server;

import finalforeach.cosmicreach.networking.server.ServerSingletons;
import finalforeach.cosmicreach.networking.server.ServerZoneLoader;
import finalforeach.cosmicreach.settings.Difficulty;
import finalforeach.cosmicreach.settings.DifficultySettings;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ParadoxServerSettings {

    public static boolean doesC4Explode = true;
    public static boolean executeChatCommands = true;
    public static boolean canBreakBlock = true;
    public static boolean canPlaceBlock = true;
    public static String joinMessage = "";
    public static boolean canChat = true;
    public static int RCONport = 47138;
    public static boolean RCONenabled = false;
    public static String RCONpassword = "";
    public static boolean anticheat = true;
    public static boolean isOffline = false;
    public static String itAPIkey = "";

    public static boolean getBool(String name){
      return getBool(name,true);
    }
    public static boolean getBool(String name,boolean def){
        return ServerSingletons.puzzle.serverConfig.get(Boolean.class,name,def);
    }
    public static int getInt(String name){
        return ServerSingletons.puzzle.serverConfig.get(Integer.class,name,0);
    }
    public static void writeSetting(){
        PropertiesConfiguration config = ServerSingletons.puzzle.serverConfig;
        config.addProperty("iteraction.shouldexplodec4",doesC4Explode);
        config.addProperty("iteraction.canbreakblock",canBreakBlock);
        config.addProperty("iteraction.canplaceblock",canPlaceBlock);
        config.addProperty("server.canchat",canChat);
        config.addProperty("server.isoffline",false);
        config.addProperty("commands.enabled",executeChatCommands);
        var rd = ServerZoneLoader.INSTANCE.serverLoadDistance;
        config.addProperty("server.renderdistance",rd < 3 || rd > 32 ? 10 : rd);
        try {
            ServerSingletons.puzzle.configBuilder.save();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    public static void initSetting(){
        PropertiesConfiguration config = ServerSingletons.puzzle.serverConfig;

        doesC4Explode = getBool("iteraction.shouldexplodec4");
        canBreakBlock =  getBool("iteraction.canbreakblock");
        canPlaceBlock =  getBool("iteraction.canplaceblock");
        RCONpassword =  Objects.requireNonNullElse(config.getString("rcon.password"),"");
        RCONport =  config.getInt("rcon.port",47138);
        RCONenabled =  getBool("rcon.enabled",false);
        canChat = getBool("server.canchat");
        anticheat = getBool("server.anticheat");
        isOffline = getBool("server.s", false);
        executeChatCommands =  getBool("commands.enabled");
        joinMessage = Objects.requireNonNullElse(config.getString("server.joinmessage"),"");
        itAPIkey = Objects.requireNonNullElse(config.getString("itch.apikey"),"");
        if(itAPIkey.isEmpty() && itAPIkey.isBlank()){
            LoggerFactory.getLogger("Paradox").warn("No itch api key in server.properties, cannot verify accounts. Setting server to an offline server");
            isOffline = true;
        }
        var rd = getInt("server.renderdistance");
        ServerZoneLoader.INSTANCE.serverLoadDistance = rd < 3 || rd > 32 ? 10 : rd;
        if (Objects.requireNonNullElse(config.getString("world.difficulty"),"normal") == "peaceful"){
            DifficultySettings.difficultyLevel.setValue(Difficulty.PEACEFUL);
        }else {
            DifficultySettings.difficultyLevel.setValue(Difficulty.NORMAL);
        }
    }

}
