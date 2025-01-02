package com.github.puzzle.paradox.game.player;

import com.github.puzzle.paradox.api.ParadoxPlayer;
import com.github.puzzle.paradox.core.ClassConverter;
import finalforeach.cosmicreach.entities.player.Player;

public abstract class InternalParadoxPlayer {
    ParadoxPlayer paradoxPlayer = null;

    public ParadoxPlayer getParadoxPlayer(){
        if(paradoxPlayer == null){
            paradoxPlayer = ClassConverter.convertPlayer((Player)(Object)this);
        }
        return paradoxPlayer;
    }
}