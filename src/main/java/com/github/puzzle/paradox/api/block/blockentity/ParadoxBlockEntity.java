package com.github.puzzle.paradox.api.block.blockentity;

import finalforeach.cosmicreach.blockentities.BlockEntity;

public abstract class ParadoxBlockEntity {

    BlockEntity blockEntity;
    public ParadoxBlockEntity(BlockEntity blockEntity){
        this.blockEntity = blockEntity;
    }

    public BlockEntity getInternalBlockEntity() {
        return blockEntity;
    }
}
