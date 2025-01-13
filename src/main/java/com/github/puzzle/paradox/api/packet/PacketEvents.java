package com.github.puzzle.paradox.api.packet;

import com.github.puzzle.paradox.api.ParadoxNetworkIdentity;
import finalforeach.cosmicreach.networking.GamePacket;
import finalforeach.cosmicreach.networking.packets.entities.PlayerPositionPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public abstract class PacketEvents extends Event {

    GamePacket gamePacket;
    ChannelHandlerContext channel;
    ParadoxNetworkIdentity identity;

    public PacketEvents(GamePacket packet,ParadoxNetworkIdentity identity,ChannelHandlerContext channel) {
        this.gamePacket = packet;
        this.channel = channel;
        this.identity = identity;
    }

    public GamePacket getGamePacket() {
        return gamePacket;
    }
    public ChannelHandlerContext getCtx(){
        return channel;
    }

    public ParadoxNetworkIdentity getIdentity(){
        return identity;
    }

    public static class OnPlayerPositionPacket extends PacketEvents implements ICancellableEvent {

        public OnPlayerPositionPacket(GamePacket packet,ParadoxNetworkIdentity identity,ChannelHandlerContext channel) {
            super(packet,identity,channel);
        }

        public PlayerPositionPacket getPlayerPositionPacket(){
            return (PlayerPositionPacket)gamePacket;
        }
    }
}