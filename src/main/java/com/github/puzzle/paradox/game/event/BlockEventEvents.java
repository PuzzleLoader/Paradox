package com.github.puzzle.paradox.game.event;

import com.github.puzzle.paradox.core.event.Event;
import com.github.puzzle.paradox.core.event.EventFactory;
import finalforeach.cosmicreach.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.world.Zone;

import java.util.Map;

public class BlockEventEvents {

    public static final Event<OnBlockEventAct> ON_BLOCK_EVENT_ACT = EventFactory.createArrayBacked(OnBlockEventAct.class, callbacks -> (trigger,srcBlockState, zone, args) -> {
        for (OnBlockEventAct callback : callbacks) {
            if (callback != null)
                callback.onBlockEvenAct(trigger, srcBlockState, zone, args);
        }
    });

    @FunctionalInterface
    public interface OnBlockEventAct {
        void onBlockEvenAct(BlockEventTrigger trigger, BlockState srcBlockState, Zone zone, Map<String, Object> args);
    }
}
