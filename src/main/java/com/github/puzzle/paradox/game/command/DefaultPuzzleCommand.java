package com.github.puzzle.paradox.game.command;

import com.github.puzzle.game.commands.CommandSource;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.function.Supplier;

public abstract class DefaultPuzzleCommand extends ParadoxBrigaderCommand {

    public void registerPermission(int value) {
        registerPermission("default.command",value);
    }

    public abstract int execute(CommandContext<CommandSource> context) throws CommandSyntaxException;
}
