package com.github.puzzle.paradox.api.events;

import com.github.puzzle.paradox.api.entity.ParadoxEntity;
import finalforeach.cosmicreach.blockevents.BlockEventArgs;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public abstract class EntityEvents extends Event {

    public EntityEvents(ParadoxEntity entity){
        this.entity = entity;
    }

    private final ParadoxEntity entity;

    public ParadoxEntity getEntity() {
        return entity;
    }

    public static class OnEntitySpawn extends EntityEvents implements ICancellableEvent {


        public OnEntitySpawn(ParadoxEntity entity) {
            super(entity);
        }
    }

}
