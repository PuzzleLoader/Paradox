package com.github.puzzle.game.commands;

import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.chat.Chat;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.world.World;

public class ParadoxClientCommandSource implements CommandSource {

    final World world;
    final Chat chat;
    final Account account;
    final Player player;
    public ParadoxClientCommandSource(Account account, Chat chat, World world, Player player) {
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
    public Chat getChat() {
        return chat;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
