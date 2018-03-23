package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.ButtonWarpMessages;
import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.listeners.ButtonWarpDelayListener;
import com.codisimus.plugins.buttonwarp.listeners.ButtonWarpListener;
import com.codisimus.plugins.buttonwarp.Menu.MainMenu;
import com.codisimus.plugins.buttonwarp.utils.Econ;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

import static com.codisimus.plugins.buttonwarp.Warp.getWarp;

/**
 * Created by Spearhartt on 2/1/2017.
 */
public class CommandWarp implements CqCommand {
    public static int multiplier;
    static final ButtonWarpMessages message = new ButtonWarpMessages();

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (args.size() == 0) {
                new MainMenu().ShowMenu(player);
            }

            Action action;

            try {
                action = Action.valueOf(args.get(0).toUpperCase());
            } catch (IllegalArgumentException notEnum) {
                if (args.size() != 1) {
                    new MainMenu().ShowMenu(player);
                }
            }

            if (!ButtonWarp.hasPermission(player, "commandwarp")) {
                player.sendMessage(message.permission);
                return true;
            }

            final Warp warp = getWarp(player, args.get(0));
            if (warp == null) {
                new MainMenu().ShowMenu(player);
                return true;
            }

            if (ButtonWarpDelayListener.warpers.containsKey(player)) {
                player.sendMessage("§4You are already in the process of warping!");
                return true;
            }

            if (warp.world != null && ButtonWarp.server.getWorld(warp.world) == null) {
                player.sendMessage(ButtonWarpMessages.worldMissing.replace("<world>", warp.world));
                return true;
            }

            if (warp.amount < 0 && !ButtonWarp.hasPermission(player, "freewarp")
                    && !Econ.charge(player, warp.source, Math.abs(warp.amount) * multiplier)) {
                return true;
            }

            //Delay Teleporting
            BukkitTask teleTask = ButtonWarp.server.getScheduler().runTaskLater(ButtonWarp.plugin, new Runnable() {
                @Override
                public void run() {
                    warp.teleport(player);
                    if (ButtonWarpListener.delay > 0) {
                        ButtonWarpDelayListener.warpers.remove(player);
                    }
                }
            }, 20L * ButtonWarpListener.delay);

            if (ButtonWarpListener.delay > 0) {
                ButtonWarpDelayListener.warpers.put(player, teleTask);
                if (!ButtonWarpMessages.delay.isEmpty()) {
                    player.sendMessage(ButtonWarpMessages.delay);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "warp";
    }
}
