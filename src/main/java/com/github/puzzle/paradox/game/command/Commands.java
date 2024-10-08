package com.github.puzzle.paradox.game.command;

import com.github.puzzle.paradox.game.server.Moderation;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.chat.Chat;
import finalforeach.cosmicreach.networking.netty.packets.MessagePacket;
import finalforeach.cosmicreach.networking.server.ServerIdentity;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import net.minecrell.terminalconsole.TerminalConsoleAppender;
import org.slf4j.LoggerFactory;

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
        setname.then(CommandManager.argument("name", StringArgumentType.greedyString())
                .executes(context -> {
                    String name =  StringArgumentType.getString(context, "name");
                    if(name.length() > 12) {

                        var packet = new MessagePacket("Name can be a max of 12 chars");
                        packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                        packet.setupAndSend(
                                ServerSingletons
                                        .getIdentityByAccount(context.getSource().getAccount()));
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
    }
}
