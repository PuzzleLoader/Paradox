package com.github.puzzle.paradox.game.player;

import com.github.puzzle.paradox.api.packet.PacketEvents;
import com.github.puzzle.paradox.api.player.ParadoxPlayer;
import com.github.puzzle.paradox.game.event.BlockEventEvents;
import com.github.puzzle.paradox.game.server.ParadoxServerSettings;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.networking.NetworkIdentity;
import net.neoforged.bus.api.SubscribeEvent;
import org.slf4j.LoggerFactory;

public class PlayerChecks {

    public static boolean blockChecks(NetworkIdentity identity, BlockPosition blockPosition){
//        playerRaycastBlockHitCheck(identity.getPlayer().getParadoxPlayer(),blockPosition);
        return blockRangeCheck(identity.getPlayer().getParadoxPlayer(),blockPosition);
    }

    public static boolean blockRangeCheck(ParadoxPlayer player, BlockPosition blockPosition){
        var plrPos = player.getPosition();
        float distance = plrPos.dst(blockPosition.getGlobalX(),blockPosition.getGlobalY(),blockPosition.getGlobalZ());
        return ((!(distance > 6.45f)) && ParadoxServerSettings.anticheat); // a little higher than what i saw as the max, but it was with a small test;
    }
    public static boolean playerRaycastBlockHitCheck(ParadoxPlayer player, BlockPosition blockPosition){
        //TODO: check what block player is looking at
        var plrPos = player.getPosition();
        var plrLookDir = player.getEntity().getViewDirection();
        return false;
    }

    public static class PositionChecks {
        @SubscribeEvent
        public void OnPlayerPosition(PacketEvents.OnPlayerPositionPacket event){
            //TODO
            event.setCanceled(false);
        }

    }
}
