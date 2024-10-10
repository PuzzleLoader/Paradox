package com.github.puzzle.paradox.game.command.console;

import com.github.puzzle.paradox.game.command.CommandSource;
import com.github.puzzle.paradox.game.server.Moderation;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.networking.netty.packets.MessagePacket;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import net.minecrell.terminalconsole.TerminalConsoleAppender;

import java.util.Objects;

import static com.github.puzzle.paradox.core.PuzzlePL.SERVER_ACCOUNT;

public class Save {

    public static class save implements Command<CommandSource> {

        public save() {}

        @Override
        public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
            TerminalConsoleAppender.print("save"+ "\n");
            Save.save = true;
            return 0;
        }
    }

    public static boolean save;
    public static boolean isSaveRequested() {
        if (!GameSingletons.isHost) {
            return false;
        } else {
            long i = System.currentTimeMillis();
            return save;
        }
    }
}
