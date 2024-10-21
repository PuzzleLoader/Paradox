package com.github.puzzle.paradox.game.command.console;

import com.github.puzzle.game.commands.CommandSource;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.networking.packets.MessagePacket;
import finalforeach.cosmicreach.networking.server.ServerSingletons;

import static com.github.puzzle.paradox.core.PuzzlePL.SERVER_ACCOUNT;

public class Say implements Command<CommandSource> {
    public Say(){}

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String message = StringArgumentType.getString(context, "txt");
        if(message.length() > MessagePacket.MAX_MESSAGE_LENGTH)
        {
            System.out.println("Message is grater than 256 chars");
            return 0;
        }
        var pack = new MessagePacket("[Server] "+ message);
        pack.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
        ServerSingletons.SERVER.broadcast(pack);
        return 0;
    }
}
