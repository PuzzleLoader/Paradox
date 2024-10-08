package com.github.puzzle.paradox.game.command;

import com.github.puzzle.paradox.game.server.Moderation;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import finalforeach.cosmicreach.networking.netty.packets.MessagePacket;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

import java.util.Map;

import static com.github.puzzle.paradox.core.PuzzlePL.SERVER_ACCOUNT;

public class Commands {

    public static void registerConsoleCommands(){

        LiteralArgumentBuilder<CommandSource> kick = CommandManager.literal("kick");
        kick.then(CommandManager.argument("id", StringArgumentType.greedyString())
         .executes(context -> {
             String id = StringArgumentType.getString(context, "id");
             var acc = ServerSingletons.getAccountByUniqueId(id);
             if(acc==null){
                 TerminalConsoleAppender.print("Can't find player by id: " + id + "\n");
                 return 0;
             }
             Moderation.kick(ServerSingletons.getIdentityByAccount(acc).ctx);
             return 0;
        }));
        CommandManager.consoledispatcher.register(kick);

        LiteralArgumentBuilder<CommandSource> op = CommandManager.literal("op");
        op.then(CommandManager.argument("id", StringArgumentType.greedyString())
                .executes(context -> {
                    String id = StringArgumentType.getString(context, "id");
                    var acc = ServerSingletons.getAccountByUniqueId(id);
                    if(acc==null){
                        TerminalConsoleAppender.print("Can't find player by id: " + id + "\n");
                        return 0;
                    }
                    ServerSingletons.getIdentityByAccount(acc).isOP = true;
                    TerminalConsoleAppender.print("Oped player");
                    return 0;
                }));
        CommandManager.consoledispatcher.register(op);

        LiteralArgumentBuilder<CommandSource> say = CommandManager.literal("say");
        say.then(CommandManager.argument("txt", StringArgumentType.greedyString())
                .executes(context -> {
                    String message = StringArgumentType.getString(context, "txt");
                    if(message.length() > MessagePacket.MAX_MESSAGE_LENGTH)
                    {
                        System.out.println("Message is grater than 256 chars");
                        return 0;
                    }
                    var pack = new MessagePacket("[Server] "+ message);
                    pack.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                    ServerSingletons.server.broadcast(pack);
                    return 0;
                }));

        CommandManager.consoledispatcher.register(say);

        LiteralArgumentBuilder<CommandSource> save = CommandManager.literal("save");
        save.executes(context -> {
            TerminalConsoleAppender.print("save"+ "\n");
            Moderation.save = true;
            return 0;
        });

        CommandManager.consoledispatcher.register(save);
    }
    public static void registerClientCommands(){
        LiteralArgumentBuilder<CommandSource> setname = CommandManager.literal("setname");
        setname.then(CommandManager.argument("name", StringArgumentType.word())
                //TODO parse some special chars e.g invis
                .executes(context -> {
                    String name =  StringArgumentType.getString(context, "name");
                    if(name.length() > 12) {

                        var packet = new MessagePacket("Name can be a max of 12 chars");
                        packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                        packet.setupAndSend(
                                ServerSingletons
                                        .getIdentityByAccount(context.getSource().getAccount()));
                        return 0;
                    }
                    boolean isTaken = false;
                    for (var id : ServerSingletons.server.connections){
                       if(ServerSingletons.server.getAccount(id.ctx).displayname.equals(name)) {
                           isTaken = true;
                           break;
                       }
                    }
                    if(isTaken){
                        var packet = new MessagePacket("Name is already used");
                        packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                        packet.setupAndSend(
                                ServerSingletons
                                        .getIdentityByAccount(context.getSource().getAccount()));
                        return 0;
                    }
                    context.getSource().getAccount().setUsername(name);
                    var packet = new MessagePacket("Set your name to: " + name);
                    packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                    packet.setupAndSend(
                            ServerSingletons
                                    .getIdentityByAccount(context.getSource().getAccount()));
                    return 0;
                }));
        CommandManager.dispatcher.register(setname);
        LiteralArgumentBuilder<CommandSource> msg = CommandManager.literal("msg");
        msg.then(CommandManager.argument("name", StringArgumentType.word())
                        .then(CommandManager.argument("msg",StringArgumentType.greedyString())
                        .executes(context -> {
                            String name =  StringArgumentType.getString(context, "name");
                            String message =  StringArgumentType.getString(context, "msg");
                            if(name.length() > 12) {

                                var packet = new MessagePacket("Name can only be a max of 12 chars");
                                packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                                packet.setupAndSend(
                                        ServerSingletons
                                                .getIdentityByAccount(context.getSource().getAccount()));
                                return 0;
                            }
                            if(message.length() > MessagePacket.MAX_MESSAGE_LENGTH){
                                var packet = new MessagePacket("Message can only be a max of "+MessagePacket.MAX_MESSAGE_LENGTH+" chars");
                                packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                                packet.setupAndSend(
                                        ServerSingletons
                                                .getIdentityByAccount(context.getSource().getAccount()));
                                return 0;
                            }

                            for (var id : ServerSingletons.server.connections){
                                var acc = ServerSingletons.server.getAccount(id.ctx);
                                if(acc.displayname.equals(name)) {
                                    var packet = new MessagePacket("["
                                            + context.getSource().getAccount().displayname
                                            + " -> " + acc.displayname + "] " +
                                            message);
                                    packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                                    packet.setupAndSend(id);
                                    packet = new MessagePacket("Sent message to: " + name);
                                    packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                                    packet.setupAndSend(
                                            ServerSingletons
                                                    .getIdentityByAccount(context.getSource().getAccount()));

                                    return 0;
                                }
                            }
                            var packet = new MessagePacket("Can't find player: " + name);
                            packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                            packet.setupAndSend(
                                    ServerSingletons
                                            .getIdentityByAccount(context.getSource().getAccount()));
                            return 0;
                        }

                )));
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
            var packet = new MessagePacket(builder.toString());
            packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            packet.setupAndSend(
                    ServerSingletons
                            .getIdentityByAccount(context.getSource().getAccount()));
            return 0;
        }));
    }
}
