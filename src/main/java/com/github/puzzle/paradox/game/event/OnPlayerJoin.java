package com.github.puzzle.paradox.game.event;

import com.github.puzzle.paradox.core.event.Event;
import com.github.puzzle.paradox.core.event.EventFactory;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.networking.common.NetworkIdentity;
import io.netty.channel.ChannelHandlerContext;

public final class OnPlayerJoin {

    public static final Event<OnPlayerJoinTrigger> EVENT = EventFactory.createArrayBacked(OnPlayerJoinTrigger.class, callbacks -> (account, identity, ctx) -> {
        for (OnPlayerJoinTrigger callback : callbacks) {
            if (callback != null)
                callback.onPlayerJoin(account, identity, ctx);
        }
    });

    @FunctionalInterface
    public interface OnPlayerJoinTrigger {
        void onPlayerJoin(Account account, NetworkIdentity identity, ChannelHandlerContext ctx);
    }
}

