package com.github.puzzle.paradox.test;

import com.github.puzzle.paradox.api.Paradox;

public class Test {

    public static void enableTests(){
        Paradox.getInstance().getEventBus().register(new BlockEventTest());
    }
}
