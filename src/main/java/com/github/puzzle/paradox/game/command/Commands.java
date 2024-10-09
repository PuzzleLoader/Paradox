package com.github.puzzle.paradox.game.command;

import com.badlogic.gdx.math.Vector3;
import com.github.puzzle.paradox.game.command.chat.Msg;
import com.github.puzzle.paradox.game.command.chat.SetName;
import com.github.puzzle.paradox.game.command.chat.Teleport;
import com.github.puzzle.paradox.game.command.console.Kick;
import com.github.puzzle.paradox.game.command.console.Op;
import com.github.puzzle.paradox.game.command.console.Say;
import com.github.puzzle.paradox.game.server.Moderation;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.networking.netty.packets.MessagePacket;
import finalforeach.cosmicreach.networking.netty.packets.PlayerPositionPacket;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import net.minecrell.terminalconsole.TerminalConsoleAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

import static com.github.puzzle.paradox.core.PuzzlePL.SERVER_ACCOUNT;

public class Commands {

    private static final Logger LOGGER  = LoggerFactory.getLogger("Paradox | command");

    public static void registerConsoleCommands(){

        CommandManager.consoledispatcher.register(CommandManager.literal("kick").
        then(CommandManager.argument("id", StringArgumentType.greedyString())
         .executes(new Kick())));

        LiteralArgumentBuilder<CommandSource> op = CommandManager.literal("op");
        op.then(CommandManager.argument("id", StringArgumentType.greedyString())
                .executes(new Op()));
        CommandManager.consoledispatcher.register(op);

        LiteralArgumentBuilder<CommandSource> say = CommandManager.literal("say");
        say.then(CommandManager.argument("txt", StringArgumentType.greedyString())
                .executes(
                        new Say()
                ));

        CommandManager.consoledispatcher.register(say);

        LiteralArgumentBuilder<CommandSource> save = CommandManager.literal("save");
        save.executes(context -> {
            TerminalConsoleAppender.print("save"+ "\n");
            Moderation.save = true;
            return 0;
        });

        CommandManager.consoledispatcher.register(save);
    }
    public static void registerClientCommands(){
        LiteralArgumentBuilder<CommandSource> setname = CommandManager.literal("setname");
        setname.then(CommandManager.argument("name", StringArgumentType.word())
                //TODO parse some special chars e.g invis
                .executes(new SetName()));
        CommandManager.dispatcher.register(setname);
        LiteralArgumentBuilder<CommandSource> msg = CommandManager.literal("msg");
        msg.then(CommandManager.argument("name", StringArgumentType.word())
                        .then(CommandManager.argument("msg",StringArgumentType.greedyString())
                        .executes(new Msg())));
        CommandManager.dispatcher.register(msg);
        LiteralArgumentBuilder<CommandSource> playerlist = CommandManager.literal("playerlist");

        playerlist.executes(context -> {
            StringBuilder builder = new StringBuilder();
            builder.append("There are " + ServerSingletons.server.connections.size + " player(s) online\n");
            builder.append("players:\n");
            for (var id : ServerSingletons.server.connections) {
                var acc = ServerSingletons.server.getAccount(id.ctx);
                if(acc == context.getSource().getAccount()){
                    builder.append("\t" + acc.displayname + " <- you\n");
                }else {
                    builder.append("\t" + acc.displayname + "\n");
                }
            }

            var packet = new MessagePacket(builder.toString());
            packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            packet.setupAndSend(
                    ServerSingletons
                            .getIdentityByAccount(context.getSource().getAccount()));
            return 0;
        });

        CommandManager.dispatcher.register(playerlist);
        CommandManager.dispatcher.register(CommandManager.literal("help").executes(context ->{
            Map<CommandNode<CommandSource>, String> map = CommandManager.dispatcher.getSmartUsage(CommandManager.dispatcher.getRoot(), context.getSource());
            StringBuilder builder = new StringBuilder();
            builder.append("Server Commands:\n");
            for(String s : map.values()) {
                builder.append("\t" + s + "\n");
            }
            var packet = new MessagePacket(builder.toString());
            packet.playerUniqueId = SERVER_ACCOUNT.getUniqueId();
            packet.setupAndSend(
                    ServerSingletons
                            .getIdentityByAccount(context.getSource().getAccount()));
            return 0;
        }));

        LiteralArgumentBuilder<CommandSource> tpr = CommandManager.literal("tpr");
        tpr.then(CommandManager.argument("name", StringArgumentType.greedyString())
                .executes(new Teleport.TPR()));

        CommandManager.dispatcher.register(tpr);

        LiteralArgumentBuilder<CommandSource> tpa = CommandManager.literal("tpa");
        tpa.executes(new Teleport.TPA());

        CommandManager.dispatcher.register(tpa);
    }
}
