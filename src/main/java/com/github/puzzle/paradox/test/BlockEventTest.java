package com.github.puzzle.paradox.test;

import com.github.puzzle.paradox.game.event.BlockEventEvents;
import net.neoforged.bus.api.SubscribeEvent;
import org.slf4j.LoggerFactory;

public class BlockEventTest {

    @SubscribeEvent
    public void onBlockEventAct(BlockEventEvents.OnEventAct event){
        event.setCanceled(false);
    }
}
