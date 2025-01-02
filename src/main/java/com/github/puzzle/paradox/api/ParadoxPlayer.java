package com.github.puzzle.paradox.api;

import com.badlogic.gdx.math.Vector3;
import com.github.puzzle.paradox.api.entity.ParadoxPlayerEntity;
import com.github.puzzle.paradox.core.ClassConverter;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.world.Zone;

public class ParadoxPlayer {

    Player player;

    ParadoxPlayerEntity entity;
    public ParadoxPlayer(Player player){
        this.player = player;
        entity = (ParadoxPlayerEntity) ClassConverter.convertEntity(player.getEntity());
    }

    public Zone getZone(){
        return player.getZone();
    }
    public ParadoxPlayerEntity getEntity(){
        return entity;
    }

    public Vector3 getPosition(){
        return this.player.getPosition();
    }
}
