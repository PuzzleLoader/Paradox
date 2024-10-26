package com.github.puzzle.paradox.game.command.console;

import com.github.puzzle.game.commands.CommandSource;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.TickRunner;
import finalforeach.cosmicreach.ZoneLoaders;
import finalforeach.cosmicreach.io.ChunkSaver;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import finalforeach.cosmicreach.server.ServerLauncher;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

public class StopServer {

    public static class stop implements Command<CommandSource> {
        public stop(){}

        @Override
        public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
            for (var id : ServerSingletons.SERVER.connections){
                id.ctx.close();
            }
            Save.shouldSave = true;
            GameSingletons.unregisterAllPlayers();
            synchronized (ChunkSaver.class){
                while (ChunkSaver.isSaving){


                }
                ServerSingletons.puzzle.exit();
                ZoneLoaders.INSTANCE.worldGenThread.stopThread();
                TickRunner.INSTANCE.thread.stopThread();
                TerminalConsoleAppender.print("stopping"+ "\n");
                ServerLauncher.isRunning = false;
                ServerSingletons.SERVER.eventloopgroup.shutdownGracefully();
                ServerSingletons.SERVER.eventloopgroup1.shutdownGracefully();
                try {
                    ServerSingletons.SERVER.channelfuture.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return 0;
        }
    }
}
