package com.github.puzzle.paradox.api.entity;


import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.entities.Entity;

import java.util.UUID;

public abstract class ParadoxEntity {
    public ParadoxEntity(Entity entity) {
        this.entity = entity;
        this.uuid = UUID.randomUUID();
    }
    Entity entity;
    UUID uuid;
    //Do you're best to use
    public Entity getInternalEntity() {
        return entity;
    }

    public Vector3 getPosition() {
        return entity.getPosition();
    }

    public String getUUIDAsString() {
        return uuid.toString();
    }
    public UUID getUUID(){
        return uuid;
    }

    public Vector3 getViewDirection(){
        return this.entity.viewDirection;
    }
}
