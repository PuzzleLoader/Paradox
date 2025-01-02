package com.github.puzzle.paradox.core;

import com.github.puzzle.paradox.api.ParadoxNetworkIdentity;
import com.github.puzzle.paradox.api.block.blockentity.ParadoxBlockEntity;
import com.github.puzzle.paradox.api.entity.*;
import com.github.puzzle.paradox.api.ParadoxPlayer;
import com.github.puzzle.paradox.api.item.ParadoxItemStack;
import finalforeach.cosmicreach.entities.DroneEntity;
import finalforeach.cosmicreach.entities.DroneTrapEntity;
import finalforeach.cosmicreach.entities.ItemEntity;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.entities.player.PlayerEntity;
import finalforeach.cosmicreach.items.ItemStack;
import finalforeach.cosmicreach.networking.NetworkIdentity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@SuppressWarnings("unchecked")

public class ClassConverter {

    public record EntityInfo<E extends ParadoxEntity, C extends finalforeach.cosmicreach.entities.Entity>
            (String id, Class<E> pEntity, Function<C,E> converter)
    {

    }
    public record ClassInfo<E, C>(String className, Class<E> clazz, Function<C,E> converter)
    {

    }
    public record BlockEntityInfo<E extends ParadoxBlockEntity, C extends finalforeach.cosmicreach.blockentities.BlockEntity>
            (String id, Class<E> clazz, Function<C,E> converter)
    {

    }
    private static final Map<String,EntityInfo<?,?>> ENTITY_CONVERTERS = new HashMap<>();
    private static final Map<String,BlockEntityInfo<?,?>> BLOCK_ENTITY_CLASS_CONVERTERS = new HashMap<>();
    private static final Map<String,ClassInfo<?,?>> OTHER_CLASS_CONVERTERS = new HashMap<>();

    private static void registerEntityConverter(EntityInfo<?,?> info){
        ENTITY_CONVERTERS.put(info.id,info);
    }
    private static void registerBlockEntityConverter(BlockEntityInfo<?,?> info){
        BLOCK_ENTITY_CLASS_CONVERTERS.put(info.id,info);
    }
    private static void registerClassConverter(ClassInfo<?,?> info){
        OTHER_CLASS_CONVERTERS.put(info.className(),info);
    }


    public static<E extends finalforeach.cosmicreach.entities.Entity> ParadoxEntity convertEntity(E toConvert){
       EntityInfo<ParadoxEntity,E> c = (EntityInfo<ParadoxEntity, E>) ENTITY_CONVERTERS.get(toConvert.entityTypeId);
       if(c == null)
           throw new RuntimeException("Can not convert this entity");
      return c.converter().apply(toConvert);
    }
    public static<E extends finalforeach.cosmicreach.blockentities.BlockEntity> ParadoxBlockEntity convertEntity(E toConvert){
        BlockEntityInfo<ParadoxBlockEntity,E> c = (BlockEntityInfo<ParadoxBlockEntity, E>) BLOCK_ENTITY_CLASS_CONVERTERS.get(toConvert.getBlockEntityId());
        if(c == null)
            throw new RuntimeException("Can not convert this block entity");
        return c.converter().apply(toConvert);
    }

    public static ParadoxPlayer convertPlayer(Player toConvert){
        ClassInfo<ParadoxPlayer,Player> c = (ClassInfo<ParadoxPlayer,Player>) OTHER_CLASS_CONVERTERS.get(Player.class.getName());
        if(c == null)
            throw new RuntimeException("Can not convert player");
        return c.converter().apply(toConvert);
    }

    public static <E, R> R convertClass(E toConvert) {
        ClassInfo<R,E> c = (ClassInfo<R,E>) OTHER_CLASS_CONVERTERS.get(toConvert.getClass().getName());
        if(c == null)
            throw new RuntimeException("Can not convert class");
        return c.converter().apply(toConvert);
    }

    static {
        registerEntityConverter(new EntityInfo<>(PlayerEntity.ENTITY_TYPE_ID, ParadoxPlayerEntity.class, ParadoxPlayerEntity::new));
        registerEntityConverter(new EntityInfo<>(DroneEntity.ENTITY_TYPE_ID, ParadoxDroneEntity.class, ParadoxDroneEntity::new));
        registerEntityConverter(new EntityInfo<>(DroneTrapEntity.ENTITY_TYPE_ID, ParadoxDroneTrapEntity.class, ParadoxDroneTrapEntity::new));
        registerEntityConverter(new EntityInfo<>(ItemEntity.ENTITY_TYPE_ID, ParadoxItemEntity.class, ParadoxItemEntity::new));


//        registerBlockEntityConverter(); TODO

        registerClassConverter(new ClassInfo<>(NetworkIdentity.class.getName(), ParadoxNetworkIdentity.class, ParadoxNetworkIdentity::new));
        registerClassConverter(new ClassInfo<>(ItemStack.class.getName(), ParadoxItemStack.class, ParadoxItemStack::new));
        registerClassConverter(new ClassInfo<>(Player.class.getName(), ParadoxPlayer.class, ParadoxPlayer::new));
    }

}
