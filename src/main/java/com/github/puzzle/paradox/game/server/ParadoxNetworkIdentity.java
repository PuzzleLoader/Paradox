package com.github.puzzle.paradox.game.server;

public abstract class ParadoxNetworkIdentity {

    public boolean waskicked = false;
    public boolean shouldIgnore = false;
    public boolean isOP = false;
    public boolean usingModdedClient = false;
    public String clientName = "unknown";
}
