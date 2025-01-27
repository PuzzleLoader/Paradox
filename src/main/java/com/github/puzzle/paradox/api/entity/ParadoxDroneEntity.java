package com.github.puzzle.paradox.api.entity;

import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.entities.DroneEntity;
import finalforeach.cosmicreach.entities.Entity;

public class ParadoxDroneEntity extends ParadoxEntity {

    DroneEntity droneEntity;
    public ParadoxDroneEntity(DroneEntity entity) {
        super(entity);
        droneEntity = entity;
    }

    /**
     * Returns a bool of this drone's friend status
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see Vector3
     */
    boolean isFriendly(){
        return this.droneEntity.isFriendly();
    }
    /**
     * Avoid using this. Returns Cosmic Reach's internal drone entity class
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see DroneEntity
     * @see Entity
     */
    public DroneEntity getInternalDroneEntity() {
        return droneEntity;
    }
}
