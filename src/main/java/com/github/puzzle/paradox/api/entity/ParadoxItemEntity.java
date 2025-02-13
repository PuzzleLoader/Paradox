package com.github.puzzle.paradox.api.entity;

import finalforeach.cosmicreach.entities.DroneTrapEntity;
import finalforeach.cosmicreach.entities.ItemEntity;
import finalforeach.cosmicreach.items.ItemStack;

public class ParadoxItemEntity extends ParadoxEntity {
    public ParadoxItemEntity(ItemEntity entity) {
        super(entity);
    }

    public ItemStack getItemStack(){
        return ((ItemEntity)entity).itemStack;
    }


    /**
     * Avoid using this. Returns Cosmic Reach's internal item entity class
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see ItemEntity
     */
    public ItemEntity getInternalItemEntity(){
        return (ItemEntity) entity;
    }
}
