package com.github.puzzle.paradox.game.player;

import com.github.puzzle.paradox.api.player.ParadoxPlayer;
import com.github.puzzle.paradox.core.ClassConverter;
import finalforeach.cosmicreach.entities.player.Player;

public abstract class InternalParadoxPlayer {
    transient ParadoxPlayer paradoxPlayer = null;

    public ParadoxPlayer getParadoxPlayer(){
        if(paradoxPlayer == null){
            paradoxPlayer = ClassConverter.convertPlayer((Player)(Object)this);
        }
        return paradoxPlayer;
    }
}
