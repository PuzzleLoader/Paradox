package com.github.puzzle.paradox.game.server;

import finalforeach.cosmicreach.networking.server.ServerSingletons;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;


public class Moderation {
    private static final Logger LOGGER  = LoggerFactory.getLogger("Paradox | Moderation");
    public static void kick(ChannelHandlerContext ctx) {
        var addy = ((InetSocketAddress)ctx.channel().remoteAddress());
        ServerSingletons.server.contextToIdentity.get(ctx).waskicked = true;

        LOGGER.info( "Player uid '{}' was kicked | {}:{}", ServerSingletons.server.getAccount(ctx).getUniqueId(),addy.getAddress().getHostAddress() ,addy.getPort());
        ctx.close();



    }
}
