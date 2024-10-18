package com.github.puzzle.paradox.game.command.console;

import com.github.puzzle.game.commands.CommandSource;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

public class Op implements Command<CommandSource> {
    public Op(){}

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String id = StringArgumentType.getString(context, "id");
        var acc = ServerSingletons.getAccountByUniqueId(id);
        if(acc==null){
            TerminalConsoleAppender.print("Can't find player by id: " + id + "\n");
            return 0;
        }
        var idt = ServerSingletons.getIdentityByAccount(acc);
        idt.isOP = !idt.isOP;
        TerminalConsoleAppender.print("Set op status to: " + idt.isOP + "\n");
        return 0;
    }
}
