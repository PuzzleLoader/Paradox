package com.github.puzzle.paradox.game.command.console;

import com.github.puzzle.paradox.game.command.CommandSource;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.io.ChunkSaver;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

public class StopServer {

    public static class stop implements Command<CommandSource> {
        public stop(){}

        @Override
        public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
            if (!ChunkSaver.isSaving){
                System.exit(0);
            }else {
                TerminalConsoleAppender.print("world is saving wait for it to finish " + "\n");
            }
            return 0;
        }
    }
}
