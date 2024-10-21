package com.github.puzzle.paradox.game.command.chat;

import com.github.puzzle.game.commands.CommandSource;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.networking.packets.MessagePacket;
import finalforeach.cosmicreach.networking.server.ServerSingletons;

import static com.github.puzzle.paradox.core.PuzzlePL.SERVER_ACCOUNT;

public class Msg implements Command<CommandSource> {
    public Msg() {}

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String name =  StringArgumentType.getString(context, "name");
        String message =  StringArgumentType.getString(context, "msg");
        if(name.length() > 12) {

            var packet = new MessagePacket("Name can only be a max of 12 chars");
            packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            packet.setupAndSend(
                    ServerSingletons
                            .getIdentityByAccount(context.getSource().getAccount()));
            return 0;
        }
        if(message.length() > MessagePacket.MAX_MESSAGE_LENGTH){
            var packet = new MessagePacket("Message can only be a max of "+MessagePacket.MAX_MESSAGE_LENGTH+" chars");
            packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            packet.setupAndSend(
                    ServerSingletons
                            .getIdentityByAccount(context.getSource().getAccount()));
            return 0;
        }

        for (var id : ServerSingletons.SERVER.connections){
            var acc = ServerSingletons.SERVER.getAccount(id.ctx);
            if(acc.displayname.equals(name)) {
                var packet = new MessagePacket("["
                        + context.getSource().getAccount().displayname
                        + " -> " + acc.displayname + "] " +
                        message);
                packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                packet.setupAndSend(id);
                packet = new MessagePacket("Sent message to: " + name);
                packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
                packet.setupAndSend(
                        ServerSingletons
                                .getIdentityByAccount(context.getSource().getAccount()));

                return 0;
            }
        }
        var packet = new MessagePacket("Can't find player: " + name);
        packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
        packet.setupAndSend(
                ServerSingletons
                        .getIdentityByAccount(context.getSource().getAccount()));
        return 0;
    }

}
