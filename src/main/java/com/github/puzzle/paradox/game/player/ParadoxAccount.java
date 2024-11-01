package com.github.puzzle.paradox.game.player;

import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.networking.server.ServerIdentity;
import finalforeach.cosmicreach.networking.server.ServerSingletons;


public abstract class ParadoxAccount {
    public String username;
    public String uniqueId;
    public String displayname;
    public boolean isValid = true;
    public boolean tpRequst = false;
    private Player tprPlayer;
    private Player tprToPlayer;

    public void addTpr(ServerIdentity id, Player playerToTp){
        this.tpRequst = true;
        this.tprPlayer = ServerSingletons.getPlayer(id);
        this.tprToPlayer = playerToTp;
    }

    public Player getTprPlayer() {
        return tprPlayer;
    }

    public Player getTprToPlayer() {
        return tprToPlayer;
    }
    public abstract String getPrefix();
}
