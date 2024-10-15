package com.github.puzzle.paradox.game.event;

import com.github.puzzle.paradox.core.event.Event;
import com.github.puzzle.paradox.core.event.EventFactory;
import finalforeach.cosmicreach.blockevents.BlockEventArgs;
import finalforeach.cosmicreach.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.world.Zone;

import java.util.Map;

public class BlockEventEvents {

    public static final Event<OnBlockEventAct> ON_BLOCK_EVENT_ACT = EventFactory.createArrayBacked(OnBlockEventAct.class, callbacks -> (args) -> {
        for (OnBlockEventAct callback : callbacks) {
            if (callback != null)
                callback.onBlockEvenAct(args);
        }
    });

    @FunctionalInterface
    public interface OnBlockEventAct {
        void onBlockEvenAct(BlockEventArgs args);
    }
}
