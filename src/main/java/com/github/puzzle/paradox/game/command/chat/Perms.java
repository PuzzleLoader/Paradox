package com.github.puzzle.paradox.game.command.chat;

import com.github.puzzle.game.commands.CommandSource;
import com.github.puzzle.paradox.core.permissions.GlobalPermissions;
import com.github.puzzle.paradox.core.permissions.PermissionGroup;
import com.github.puzzle.paradox.game.command.DefaultPuzzleCommand;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class Perms extends DefaultPuzzleCommand {
    @Override
    public String getName() {
        return "perms";
    }
    public Perms() {
        registerPermission(100_000_000);
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        /*TODO:
            this command layout example

            perm group add <perm>
            perm group create <group>
            perm group remove <perm>
            perm group delete <group>
            perm player <player> add perm <perm>
            perm player <player> add group <group>

            dont allow deleting of 'default' group
         */
        return 0;
    }
}
