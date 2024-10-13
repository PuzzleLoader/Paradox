package com.github.puzzle.paradox.game.command;

import com.github.puzzle.paradox.game.command.chat.Msg;
import com.github.puzzle.paradox.game.command.chat.SetName;
import com.github.puzzle.paradox.game.command.chat.Teleport;
import com.github.puzzle.paradox.game.command.console.*;
import com.github.puzzle.paradox.game.server.ParadoxServerSettings;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import finalforeach.cosmicreach.networking.netty.packets.MessagePacket;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import finalforeach.cosmicreach.networking.server.ServerZoneLoader;
import net.minecrell.terminalconsole.TerminalConsoleAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.github.puzzle.paradox.core.PuzzlePL.SERVER_ACCOUNT;

public class Commands {

    private static final Logger LOGGER  = LoggerFactory.getLogger("Paradox | command");

    public static void registerConsoleCommands(){

        CommandManager.consoledispatcher.register(CommandManager.literal("setrenderdistance").then(
                CommandManager.argument("size", IntegerArgumentType.integer(3,32)).executes(
                        context ->{
                            ServerZoneLoader.INSTANCE.serverLoadDistance = IntegerArgumentType.getInteger(context, "size");
                            return 0;
                        }
                )
        ));

        CommandManager.consoledispatcher.register(CommandManager.literal("kick").
        then(CommandManager.argument("id", StringArgumentType.greedyString())
         .executes(new Kick())));

        LiteralArgumentBuilder<CommandSource> op = CommandManager.literal("op");
        op.then(CommandManager.argument("id", StringArgumentType.greedyString())
                .executes(new Op()));
        CommandManager.consoledispatcher.register(op);

        LiteralArgumentBuilder<CommandSource> say = CommandManager.literal("say");
        say.then(CommandManager.argument("txt", StringArgumentType.greedyString())
                .executes(
                        new Say()
                ));

        CommandManager.consoledispatcher.register(say);

        LiteralArgumentBuilder<CommandSource> save = CommandManager.literal("save");
        save.executes(new Save.save());

        CommandManager.consoledispatcher.register(save);

        CommandManager.consoledispatcher.register(CommandManager.literal("c4grief").executes(context ->{
            ParadoxServerSettings.doesC4Explode  = !ParadoxServerSettings.doesC4Explode;
            System.out.println("Set doesC4Explode to: " + ParadoxServerSettings.doesC4Explode);
            return 0;
        }));

        CommandManager.consoledispatcher.register(CommandManager.literal("help").executes(context ->{
            if(context.getSource().getAccount() == null)
            {
                StringBuilder builder = new StringBuilder();
                builder.append("Operator Commands:\n");
                Map<CommandNode<CommandSource>, String> mapconsole = CommandManager.consoledispatcher.getSmartUsage(CommandManager.consoledispatcher.getRoot(), context.getSource());
                for(String s : mapconsole.values()) {
                    builder.append("\t" + s + "\n");
                }
                TerminalConsoleAppender.print(builder.toString());

            }

            return 0;
        }));

        LiteralArgumentBuilder<CommandSource> setSpawn = CommandManager.literal("setspawn");
        setSpawn.then(CommandManager.argument("x", FloatArgumentType.floatArg())
                .then(CommandManager.argument("y", FloatArgumentType.floatArg())
                        .then(CommandManager.argument("z", FloatArgumentType.floatArg())
                                .executes(new Spawn.SetSpawn()))));

        CommandManager.consoledispatcher.register(setSpawn);

        LiteralArgumentBuilder<CommandSource> getspawn = CommandManager.literal("getspawn");
        getspawn.executes(new Spawn.GetSpawn());

        CommandManager.consoledispatcher.register(getspawn);

        LiteralArgumentBuilder<CommandSource> stop = CommandManager.literal("stop");
        stop.executes(new StopServer.stop());

        CommandManager.consoledispatcher.register(stop);
    }
    public static void registerClientCommands(){
        LiteralArgumentBuilder<CommandSource> setname = CommandManager.literal("setname");
        setname.then(CommandManager.argument("name", StringArgumentType.word())
                //TODO parse some special chars e.g invis
                .executes(new SetName()));
        CommandManager.dispatcher.register(setname);

        LiteralArgumentBuilder<CommandSource> msg = CommandManager.literal("msg");
        msg.then(CommandManager.argument("name", StringArgumentType.word())
                        .then(CommandManager.argument("msg",StringArgumentType.greedyString())
                        .executes(new Msg())));
        CommandManager.dispatcher.register(msg);

        LiteralArgumentBuilder<CommandSource> playerlist = CommandManager.literal("playerlist");
        playerlist.executes(context -> {
            StringBuilder builder = new StringBuilder();
            builder.append("There are " + ServerSingletons.server.connections.size + " player(s) online\n");
            builder.append("players:\n");
            for (var id : ServerSingletons.server.connections) {
                var acc = ServerSingletons.server.getAccount(id.ctx);
                if(acc == context.getSource().getAccount()){
                    builder.append("\t" + acc.displayname + " <- you\n");
                }else {
                    builder.append("\t" + acc.displayname + "\n");
                }
            }

            var packet = new MessagePacket(builder.toString());
            packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            packet.setupAndSend(
                    ServerSingletons
                            .getIdentityByAccount(context.getSource().getAccount()));
            return 0;
        });

        CommandManager.dispatcher.register(playerlist);
        CommandManager.dispatcher.register(CommandManager.literal("help").executes(context ->{
            Map<CommandNode<CommandSource>, String> map = CommandManager.dispatcher.getSmartUsage(CommandManager.dispatcher.getRoot(), context.getSource());
            StringBuilder builder = new StringBuilder();
            builder.append("Server Commands:\n");
            for(String s : map.values()) {
                builder.append("\t" + s + "\n");
            }
            if(ServerSingletons.getIdentityByAccount(context.getSource().getAccount()).isOP)
            {
                builder.append("Operator Commands:\n");
                Map<CommandNode<CommandSource>, String> mapconsole = CommandManager.consoledispatcher.getSmartUsage(CommandManager.consoledispatcher.getRoot(), context.getSource());
                for(String s : mapconsole.values()) {
                    if(!s.startsWith("help"))
                        builder.append("\t" + s + "\n");
                }
            }

            var packet = new MessagePacket(builder.toString());
            packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            packet.setupAndSend(
                    ServerSingletons
                            .getIdentityByAccount(context.getSource().getAccount()));
            return 0;
        }));


        LiteralArgumentBuilder<CommandSource> tpr = CommandManager.literal("tpr");
        tpr.then(CommandManager.argument("name", StringArgumentType.greedyString())
                .executes(new Teleport.TPR()));

        CommandManager.dispatcher.register(tpr);

        LiteralArgumentBuilder<CommandSource> tpa = CommandManager.literal("tpa");
        tpa.executes(new Teleport.TPA());

        CommandManager.dispatcher.register(tpa);
    }
}
