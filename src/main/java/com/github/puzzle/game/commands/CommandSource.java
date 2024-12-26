package com.github.puzzle.game.commands;

import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.chat.Chat;
import finalforeach.cosmicreach.chat.IChat;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.networking.NetworkIdentity;
import finalforeach.cosmicreach.world.World;

public interface CommandSource {
    Account getAccount();
    IChat getChat();
    World getWorld();
    NetworkIdentity getIdentity();
    Player getPlayer();
}
