package com.github.puzzle.paradox.game.command;

import com.badlogic.gdx.Game;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import finalforeach.cosmicreach.networking.common.NetworkIdentity;
import finalforeach.cosmicreach.networking.netty.GamePacket;
import finalforeach.cosmicreach.networking.netty.packets.MessagePacket;
import finalforeach.cosmicreach.networking.server.ServerIdentity;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import io.netty.channel.ChannelHandlerContext;

import static com.github.puzzle.paradox.core.PuzzlePL.SERVER_ACCOUNT;
import static finalforeach.cosmicreach.GameSingletons.world;

public class CommandParsing {
    public static void parse(GamePacket packet, String message, NetworkIdentity identity, ChannelHandlerContext ctx){
        try {
            ParseResults<CommandSource> results = CommandManager.dispatcher.parse(message.substring(1),new ParadoxClientCommandSource(ServerSingletons.getAccount(identity),null,world,null));
            CommandSyntaxException e;
            if(results.getReader().canRead()) {
                if(results.getExceptions().size() == 1)
                    e = results.getExceptions().values().iterator().next();
                else
                    e = results.getContext().getRange().isEmpty() ? CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(results.getReader()) : CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(results.getReader());

                throw e;
            }
            CommandManager.dispatcher.execute(message.substring(1), new ParadoxClientCommandSource(ServerSingletons.getAccount(identity), null, world, null));
        } catch (CommandSyntaxException e) {
//            packet
//            ServerSingletons.server.broadcastAsServerExcept(packet,identity);
            var pack = new MessagePacket("[Server] "+ e.getRawMessage().getString() + ": " + message.substring(1));
            pack.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            pack.setupAndSend(ServerSingletons.server.contextToIdentity.get(ctx));
        } catch (IllegalArgumentException e) {
            var pack = new MessagePacket("[Server] "+ e.getMessage());
            pack.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            pack.setupAndSend(ServerSingletons.server.contextToIdentity.get(ctx));
        }
    }
}
