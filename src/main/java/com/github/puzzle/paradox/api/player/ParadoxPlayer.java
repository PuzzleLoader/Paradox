package com.github.puzzle.paradox.api.player;

import com.badlogic.gdx.math.Vector3;
import com.github.puzzle.paradox.api.entity.ParadoxPlayerEntity;
import com.github.puzzle.paradox.core.ClassConverter;
import com.github.puzzle.paradox.game.player.InternalParadoxAccount;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.networking.server.ServerIdentity;
import finalforeach.cosmicreach.world.Zone;

import java.util.UUID;

public class ParadoxPlayer {

    Player player;

    ParadoxPlayerEntity entity;
    ParadoxAccount account;
    public ParadoxPlayer(Player player){
        this.player = player;
        this.entity = (ParadoxPlayerEntity) ClassConverter.convertEntity(player.getEntity());
        this.account = ClassConverter.convertClass(player.getAccount());
    }
//TODO create ParadoxZone
//    public Zone getZone(){
//        return player.getZone();
//    }

    /**
     * Returns the associated ParadoxPlayerEntity of this Player
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see ParadoxPlayerEntity
     */
    public ParadoxPlayerEntity getEntity(){
        return entity;
    }

    /**
     * Returns a Vector3 of player's position from its entity
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see Vector3
     * @see finalforeach.cosmicreach.entities.player.PlayerEntity
     */
    public Vector3 getPosition(){
        return getEntity().getPosition();
    }


    /**
     * Returns the associated account of the player
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see ParadoxAccount
     */
    public ParadoxAccount getAccount(){
        return account;
    }

    /**
     * Avoid using this. Returns Cosmic Reach's internal player class
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see Player
     */
    public Player getInternalPlayer(){
        return player;
    }
}
