package com.github.puzzle.paradox.api.entity;


import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.entities.Entity;

import java.util.UUID;

public abstract class ParadoxEntity {
    public ParadoxEntity(Entity entity) {
        this.entity = entity;
        this.uuid = UUID.randomUUID();
    }
    Entity entity;
    UUID uuid;
    /**
     * Avoid using this. Returns Cosmic Reach's internal entity class
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see Entity
     */
    public Entity getInternalEntity() {
        return entity;
    }

    /**
     * Returns a Vector3 of entity's position
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see Vector3
     */
    public Vector3 getPosition() {
        return entity.getPosition();
    }


    /**
     * Returns the UUID of the entity as a String
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see UUID
     * @see String
     */
    public String getUUIDAsString() {
        return uuid.toString();
    }

    /**
     * Returns the UUID of the entity
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see UUID
     */
    public UUID getUUID(){
        return uuid;
    }

    /**
     * Returns a Vector3 of entity's direction it is looking
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see Vector3
     */
    public Vector3 getViewDirection(){
        return this.entity.viewDirection;
    }
}
