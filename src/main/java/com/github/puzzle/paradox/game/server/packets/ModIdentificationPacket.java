package com.github.puzzle.paradox.game.server.packets;

import finalforeach.cosmicreach.networking.GamePacket;
import finalforeach.cosmicreach.networking.NetworkIdentity;
import finalforeach.cosmicreach.networking.NetworkSide;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

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
    public void handle(NetworkIdentity identity, ChannelHandlerContext ctx) {
        if (identity.getSide() == NetworkSide.SERVER) {
            ServerSingletons.SERVER.contextToIdentity.get(ctx).usingModdedClient = true;
            ServerSingletons.SERVER.contextToIdentity.get(ctx).clientName = clientString;
//            LoggerFactory.getLogger("test").info("Account {} has joined as a modded client, the client being used is identified as {}", ServerSingletons.getAccount(identity).getDisplayName(), this.clientString);
        }
    }
}
