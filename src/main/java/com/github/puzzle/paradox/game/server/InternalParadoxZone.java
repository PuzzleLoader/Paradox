package com.github.puzzle.paradox.game.server;

import com.github.puzzle.paradox.api.ParadoxZone;
import com.github.puzzle.paradox.core.ClassConverter;
import finalforeach.cosmicreach.world.Zone;

public class InternalParadoxZone {
    ParadoxZone paradoxZone;

    public ParadoxZone getParadoxZone(){
        if(paradoxZone == null){
            paradoxZone = ClassConverter.convertClass((Zone)(Object)this);
        }
        return paradoxZone;
    }
}
