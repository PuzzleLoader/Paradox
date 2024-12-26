package com.github.puzzle.game.commands;

import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.chat.IChat;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.networking.NetworkIdentity;
import finalforeach.cosmicreach.world.World;

public class ParadoxCommandSource implements CommandSource {

    final World world;
    final IChat chat;
    final Account account;
    final Player player;
    public ParadoxCommandSource(Account account, IChat chat, World world, Player player) {
        this.account = account;
        this.chat = chat;
        this.world = world;
        this.player = player;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public IChat getChat() {
        return chat;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public NetworkIdentity getIdentity() {
        return null;
    }

    @Override
    public Player getPlayer() {
        return account.getPlayer();
    }

}
