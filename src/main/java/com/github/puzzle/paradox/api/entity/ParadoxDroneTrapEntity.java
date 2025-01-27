package com.github.puzzle.paradox.api.entity;

import finalforeach.cosmicreach.entities.DroneTrapEntity;

public class ParadoxDroneTrapEntity extends ParadoxEntity {

    DroneTrapEntity trapEntity;

    public ParadoxDroneTrapEntity(DroneTrapEntity entity) {
        super(entity);
        trapEntity = entity;
    }
}
