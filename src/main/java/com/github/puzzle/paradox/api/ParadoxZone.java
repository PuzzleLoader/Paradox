package com.github.puzzle.paradox.api;

import com.github.puzzle.paradox.api.entity.ParadoxEntity;
import finalforeach.cosmicreach.entities.Entity;
import finalforeach.cosmicreach.world.Zone;
import org.jetbrains.annotations.NotNull;

public class ParadoxZone {

    Zone zone;
    public ParadoxZone(Zone zone) {
        this.zone = zone;
    }

    /**
     * Avoid using this. Returns Cosmic Reach's internal zone class
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see Entity
     */
    public Zone getInternalZone(){
        return zone;
    }

    /**
     * Add the provided entity to the zone
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see ParadoxEntity
     */
    public void addEntity(@NotNull ParadoxEntity entity){
        zone.addEntity(entity.getInternalEntity());
    }
}
