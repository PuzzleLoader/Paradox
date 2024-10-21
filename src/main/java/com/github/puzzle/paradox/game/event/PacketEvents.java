package com.github.puzzle.paradox.game.event;

import com.badlogic.gdx.utils.Array;
import com.github.puzzle.paradox.core.event.Event;
import com.github.puzzle.paradox.core.event.EventFactory;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.networking.GamePacket;
import finalforeach.cosmicreach.networking.NetworkIdentity;
import io.netty.channel.ChannelHandlerContext;

public class PacketEvents {
    public static final Event<OnPlayerJoinTrigger> ON_PLAYER_JOIN_EVENT = EventFactory.createArrayBacked(OnPlayerJoinTrigger.class, callbacks -> (account, identity, ctx) -> {
        for (OnPlayerJoinTrigger callback : callbacks) {
            if (callback != null)
                callback.onPlayerJoin(account, identity, ctx);
        }
    });

    @FunctionalInterface
    public interface OnPlayerJoinTrigger {
        void onPlayerJoin(Account account, NetworkIdentity identity, ChannelHandlerContext ctx);
    }
    public static final Event<OnPacketHandleTrigger> ON_PACKET_HANDLE_EVENT = EventFactory.createArrayBacked(OnPacketHandleTrigger.class, callbacks -> (packet, identity, ctx) -> {
        for (OnPacketHandleTrigger callback : callbacks) {
            if (callback != null)
                callback.onPacketHandle(packet, identity, ctx);
        }
    });

    @FunctionalInterface
    public interface OnPacketHandleTrigger {
        void onPacketHandle(GamePacket packet, NetworkIdentity identity, ChannelHandlerContext ctx);
    }

    public static final Event<OnPacketBundleHandleTrigger> ON_BUNDLE_PACKET_HANDLE_EVENT = EventFactory.createArrayBacked(OnPacketBundleHandleTrigger.class, callbacks -> (packets, identity, ctx) -> {
        for (OnPacketBundleHandleTrigger callback : callbacks) {
            if (callback != null)
                callback.onPacketBundleHandle(packets, identity, ctx);
        }
    });


    @FunctionalInterface
    public interface OnPacketBundleHandleTrigger {
        void onPacketBundleHandle(Array<GamePacket> packets, NetworkIdentity identity, ChannelHandlerContext ctx);
    }

    public static final Event<OnRegisterPackets> ON_REGISTER_PACKETS = EventFactory.createArrayBacked(OnRegisterPackets.class, callbacks -> () -> {
        for (OnRegisterPackets callback : callbacks) {
            if (callback != null)
                callback.onRegisterPackets();
        }
    });


    @FunctionalInterface
    public interface OnRegisterPackets {
        void onRegisterPackets();
    }
}
