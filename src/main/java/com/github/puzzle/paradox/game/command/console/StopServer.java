package com.github.puzzle.paradox.game.command.console;

import com.github.puzzle.paradox.game.command.CommandSource;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.Threads;
import finalforeach.cosmicreach.WorldLoaders;
import finalforeach.cosmicreach.io.ChunkSaver;
import finalforeach.cosmicreach.networking.netty.NettyServer;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

import java.util.concurrent.TimeUnit;

public class StopServer {

    public static class stop implements Command<CommandSource> {
        public stop(){}

        @Override
        public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
            for (var id : ServerSingletons.server.connections){
                id.ctx.close();
            }
            Save.shouldSave = true;
            GameSingletons.unregisterAllPlayers();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {
            }
            synchronized (ChunkSaver.class){
                while (ChunkSaver.isSaving){


                }
                WorldLoaders.INSTANCE.worldGenThread.stopThread();
                TerminalConsoleAppender.print("stopping"+ "\n");
                ServerSingletons.puzzle.exit();
                System.exit(0);

            }
            return 0;
        }
    }
}
