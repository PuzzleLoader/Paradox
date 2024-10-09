package com.github.puzzle.paradox.game.command.console;

import com.github.puzzle.paradox.game.command.CommandSource;
import com.github.puzzle.paradox.game.server.Moderation;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

public class Kick implements Command<CommandSource> {
    public Kick(){}

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String id = StringArgumentType.getString(context, "id");
        var acc = ServerSingletons.getAccountByUniqueId(id);
        if(acc==null){
            TerminalConsoleAppender.print("Can't find player by id: " + id + "\n");
            return 0;
        }
        Moderation.kick(ServerSingletons.getIdentityByAccount(acc).ctx);
        return 0;
    }
}
