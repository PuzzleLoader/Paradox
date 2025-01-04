package com.github.puzzle.paradox.api.entity;


import com.badlogic.gdx.math.Vector3;
import com.github.puzzle.paradox.api.ParadoxZone;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.entities.*;
import finalforeach.cosmicreach.entities.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unchecked")
public abstract class ParadoxEntity {

    private static Map<Class<? extends ParadoxEntity>,String> classtoidentity = new HashMap<>();

    public static String getEntityIDByClass(@NotNull Class<? extends ParadoxEntity> e){
        return classtoidentity.get(e);
    }
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

    /**
     * Returns a spawned instance of the proved paradox entity
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @param eClass
     * @param position
     * @param zone
     * @see ParadoxEntity
     */
    public static<E extends ParadoxEntity> E spawnEntity(@NotNull Class<E> eClass,@NotNull Vector3 position,@NotNull ParadoxZone zone){
        if(classtoidentity.get(eClass) == null)
            return null;
        var spawned =  EntityCreator.get(classtoidentity.get(eClass));
        spawned.setPosition(position);
        var pe = spawned.getParadoxEntity();
        zone.addEntity(pe);
        zone.getInternalZone().mobSpawner.countMobs(zone.getInternalZone());
        return (E) pe;
    }

    static {
        classtoidentity.put(ParadoxPlayerEntity.class, PlayerEntity.ENTITY_TYPE_ID);
        classtoidentity.put(ParadoxDroneEntity.class, DroneEntity.ENTITY_TYPE_ID);
        classtoidentity.put(ParadoxDroneTrapEntity.class, DroneTrapEntity.ENTITY_TYPE_ID);
        classtoidentity.put(ParadoxItemEntity.class, ItemEntity.ENTITY_TYPE_ID);
    }
}
