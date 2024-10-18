package com.github.puzzle.paradox.game.command.console;

import com.github.puzzle.game.commands.CommandSource;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.GameSingletons;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

public class Save {

    public static class save implements Command<CommandSource> {

        public save() {}

        @Override
        public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
            TerminalConsoleAppender.print("save"+ "\n");
            Save.shouldSave = true;
            return 0;
        }
    }

    public static boolean shouldSave;
    public static boolean isSaveRequested() {
        if (!GameSingletons.isHost) {
            return false;
        } else {
            return shouldSave;
        }
    }
}
