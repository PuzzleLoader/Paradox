package com.github.puzzle.paradox.game.event;

import com.github.puzzle.paradox.core.event.EventFactory;
import finalforeach.cosmicreach.blockevents.BlockEventArgs;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

import java.util.Map;

public abstract class BlockEventEvents extends Event {

    public BlockEventEvents(BlockEventArgs eventArgs){
        blockEventArgs = eventArgs;
    }

    private final BlockEventArgs blockEventArgs;

    public BlockEventArgs getBlockEventArgs() {
        return blockEventArgs;
    }

    public static class OnEventAct extends BlockEventEvents implements ICancellableEvent {

        public OnEventAct(BlockEventArgs eventArgs) {
            super(eventArgs);
        }


    }

    //    public static final Event<OnBlockEventAct> ON_BLOCK_EVENT_ACT = EventFactory.createArrayBacked(OnBlockEventAct.class, callbacks -> (args) -> {
//        for (OnBlockEventAct callback : callbacks) {
//            if (callback != null)
//                callback.onBlockEvenAct(args);
//        }
//    });

//    @FunctionalInterface
//    public interface OnBlockEventAct {
//        void onBlockEvenAct(BlockEventArgs args);
//    }


}
