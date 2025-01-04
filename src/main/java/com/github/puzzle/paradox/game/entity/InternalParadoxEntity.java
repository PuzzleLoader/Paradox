package com.github.puzzle.paradox.game.entity;

import com.github.puzzle.paradox.api.entity.ParadoxEntity;
import com.github.puzzle.paradox.core.ClassConverter;
import finalforeach.cosmicreach.entities.Entity;

public class InternalParadoxEntity {
    transient ParadoxEntity entity = null;

    public ParadoxEntity getParadoxEntity(){
        if(entity == null){
            entity = ClassConverter.convertEntity((Entity)(Object)this);
        }
        return entity;
    }
}
