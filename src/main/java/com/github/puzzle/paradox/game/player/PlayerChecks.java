package com.github.puzzle.paradox.game.player;

import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.networking.common.NetworkIdentity;

public class PlayerChecks {

    public static boolean BlockRangeCheck(NetworkIdentity identity, BlockPosition blockPosition){
        var plrPos = identity.getPlayer().getPosition();
        float distance = plrPos.dst(blockPosition.getGlobalX(),blockPosition.getGlobalY(),blockPosition.getGlobalZ());
        return !(distance > 6.45f); // a little higher than what i saw as the max, but it was with a small test;
    }
}
