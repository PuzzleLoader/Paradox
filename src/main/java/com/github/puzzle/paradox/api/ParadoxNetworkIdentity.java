package com.github.puzzle.paradox.api;

import com.github.puzzle.paradox.core.ClassConverter;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.networking.NetworkIdentity;
import finalforeach.cosmicreach.networking.netty.NettyServer;
import finalforeach.cosmicreach.networking.server.ServerIdentity;
import io.netty.channel.ChannelHandlerContext;

public class ParadoxNetworkIdentity {
    ServerIdentity identity;
    ParadoxPlayer player;
    public ParadoxNetworkIdentity(ServerIdentity identity) {
        this.identity = identity;
        this.player = ClassConverter.convertPlayer(identity.getPlayer());
    }

    public ParadoxPlayer getPlayer(){
        return player;
    }
}
