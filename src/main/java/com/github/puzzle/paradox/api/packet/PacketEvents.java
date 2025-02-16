package com.github.puzzle.paradox.api.packet;

import com.github.puzzle.paradox.api.ParadoxNetworkIdentity;
import finalforeach.cosmicreach.blockentities.BlockEntity;
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

    /**
     * Avoid using this. Returns Cosmic Reach's internal game packet class
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see GamePacket
     */
    public GamePacket getInternalGamePacket() {
        return gamePacket;
    }
    /**
     * Avoid using this. Returns Cosmic Reach's internal channel handler context class
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see ChannelHandlerContext
     */
    public ChannelHandlerContext getInternalCtx(){
        return channel;
    }

    /**
     * Returns associated ParadoxNetworkIdentity with this event
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see ParadoxNetworkIdentity
     */
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

    public static class OnPacketAct extends PacketEvents  {

        public OnPacketAct(GamePacket packet,ParadoxNetworkIdentity identity,ChannelHandlerContext channel) {
            super(packet,identity,channel);
        }

        public GamePacket getPacket(){
            return gamePacket;
        }
    }
}
