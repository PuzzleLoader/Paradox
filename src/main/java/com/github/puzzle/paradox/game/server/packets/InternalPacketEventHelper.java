package com.github.puzzle.paradox.game.server.packets;

import com.github.puzzle.paradox.api.Paradox;
import com.github.puzzle.paradox.api.packet.PacketEvents;
import finalforeach.cosmicreach.networking.GamePacket;
import finalforeach.cosmicreach.networking.NetworkIdentity;
import finalforeach.cosmicreach.networking.packets.entities.PlayerPositionPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class InternalPacketEventHelper {

    public static boolean fireAssociatedPacketEvent(GamePacket packet, NetworkIdentity identity, ChannelHandlerContext ctx){
        Paradox.getInstance().getEventBus().post(new PacketEvents.OnPacketAct(packet,identity.getParadoxNetworkIdentity(),ctx));
        if(packet instanceof PlayerPositionPacket) {
            return !Paradox.getInstance().getEventBus().post(new PacketEvents.OnPlayerPositionPacket(packet, identity.getParadoxNetworkIdentity(), ctx)).isCanceled();
        }
        return true;
    }
}
