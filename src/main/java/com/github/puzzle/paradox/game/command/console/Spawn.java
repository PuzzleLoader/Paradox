package com.github.puzzle.paradox.game.command.console;

import com.badlogic.gdx.math.Vector3;
import com.github.puzzle.game.commands.CommandSource;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

import static finalforeach.cosmicreach.GameSingletons.world;

public class Spawn {

    public static class SetSpawn implements Command<CommandSource> {
        public SetSpawn(){}

        @Override
        public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
            float x = FloatArgumentType.getFloat(context, "x");
            float y = FloatArgumentType.getFloat(context, "y");
            float z = FloatArgumentType.getFloat(context, "z");

            Vector3 spawnPoint = new Vector3();
            spawnPoint.set(x,y,z);
            world.getDefaultZone().spawnPoint = spawnPoint;
            TerminalConsoleAppender.print("set spawn point to " + spawnPoint + "\n");
            return 0;
        }
    }

    public static class GetSpawn implements Command<CommandSource> {
        public GetSpawn(){}

        @Override
        public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
            TerminalConsoleAppender.print("spawn point: " + world.getDefaultZone().spawnPoint + "\n");
            return 0;
        }
    }
}
