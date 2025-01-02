package com.github.puzzle.paradox.api.entity;

import finalforeach.cosmicreach.entities.ItemEntity;
import finalforeach.cosmicreach.items.ItemStack;

public class ParadoxItemEntity extends ParadoxEntity {
    public ParadoxItemEntity(ItemEntity entity) {
        super(entity);
    }

    public ItemStack getItemStack(){
        return ((ItemEntity)entity).itemStack;
    }
}
