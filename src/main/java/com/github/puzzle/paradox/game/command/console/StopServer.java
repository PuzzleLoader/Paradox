package com.github.puzzle.paradox.game.command.console;

import com.github.puzzle.paradox.game.command.CommandSource;
import com.github.puzzle.paradox.game.server.Moderation;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.io.ChunkSaver;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

public class StopServer {

    public static class stop implements Command<CommandSource> {
        public stop(){}

        @Override
        public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
            for (var id : ServerSingletons.server.connections){
                Moderation.kick(id.ctx);
            }
            Save.save = true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            synchronized (ChunkSaver.class){
                while (!ChunkSaver.isSaving){
                    System.exit(0);
                }
            }
            return 0;
        }
    }
}
