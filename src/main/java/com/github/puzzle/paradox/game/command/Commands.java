package com.github.puzzle.paradox.game.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

public class Commands {

    public static void register(){

        LiteralArgumentBuilder<CommandSource> kick = CommandManager.literal("kick");
        kick.then(CommandManager.argument("id", StringArgumentType.greedyString())
         .executes(context -> {
             String id = StringArgumentType.getString(context, "id");
             var acc = ServerSingletons.getAccountByUniqueId(id);
             if(acc==null){
                 TerminalConsoleAppender.print("Can't find player by id: " + id);
                 return 0;
             }
             ServerSingletons.server.removeContextByKick(ServerSingletons.getIdentityByAccount(acc).ctx);
            return 0;
        }));

        CommandManager.dispatcher.register(kick);
    }
}
