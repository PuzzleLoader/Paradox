package com.github.puzzle.paradox.game.server.packets;

import finalforeach.cosmicreach.networking.common.NetworkIdentity;
import finalforeach.cosmicreach.networking.common.NetworkSide;
import finalforeach.cosmicreach.networking.netty.GamePacket;
import finalforeach.cosmicreach.networking.server.ServerIdentity;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.LoggerFactory;

public class ModIdentificationPacket extends GamePacket {
    public ModIdentificationPacket(){}
    String clientString = "unknown";
    @Override
    public void receive(ByteBuf var1) {
        var s = readString(var1);
        if(s == null)
            return;
        if( !s.isBlank() || !s.isEmpty())
            clientString = s;
    }

    @Override
    public void write() {
    }

    @Override
    protected void handle(NetworkIdentity identity, ChannelHandlerContext ctx) {
        if (identity.getSide() == NetworkSide.SERVER) {
            ServerSingletons.server.contextToIdentity.get(ctx).usingModdedClient = true;
            ServerSingletons.server.contextToIdentity.get(ctx).clientName = clientString;
//            LoggerFactory.getLogger("test").info("Account {} has joined as a modded client, the client being used is identified as {}", ServerSingletons.getAccount(identity).getDisplayName(), this.clientString);
        }
    }
}
