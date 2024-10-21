package com.github.puzzle.paradox.game.command.chat;

import com.github.puzzle.game.commands.CommandSource;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.networking.packets.MessagePacket;
import finalforeach.cosmicreach.networking.server.ServerSingletons;

import static com.github.puzzle.paradox.core.PuzzlePL.SERVER_ACCOUNT;

public class SetName implements Command<CommandSource> {

    public SetName() {}

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String name =  StringArgumentType.getString(context, "name");
        if(name.length() > 12) {

            var packet = new MessagePacket("Name can be a max of 12 chars");
            packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            packet.setupAndSend(
                    ServerSingletons
                            .getIdentityByAccount(context.getSource().getAccount()));
            return 0;
        }
        boolean isTaken = false;
        for (var id : ServerSingletons.SERVER.connections){
            if(ServerSingletons.SERVER.getAccount(id.ctx).displayname.equals(name)) {
                isTaken = true;
                break;
            }
        }
        if(isTaken){
            var packet = new MessagePacket("Name is already used");
            packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            packet.setupAndSend(
                    ServerSingletons
                            .getIdentityByAccount(context.getSource().getAccount()));
            return 0;
        }
        context.getSource().getAccount().setUsername(name);
        var packet = new MessagePacket("Set your name to: " + name);
        packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
        packet.setupAndSend(
                ServerSingletons
                        .getIdentityByAccount(context.getSource().getAccount()));
        return 0;
    }
}
