package com.github.puzzle.paradox.game.player;

import com.github.puzzle.paradox.game.server.ParadoxServerSettings;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.networking.NetworkIdentity;

public class PlayerChecks {

    public static boolean BlockRangeCheck(NetworkIdentity identity, BlockPosition blockPosition){
        var plrPos = identity.getPlayer().getPosition();
        float distance = plrPos.dst(blockPosition.getGlobalX(),blockPosition.getGlobalY(),blockPosition.getGlobalZ());
        return ((!(distance > 6.45f)) && ParadoxServerSettings.anticheat); // a little higher than what i saw as the max, but it was with a small test;
    }
}
