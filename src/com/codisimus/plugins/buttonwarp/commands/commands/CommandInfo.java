package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.MainMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import com.codisimus.plugins.buttonwarp.utils.Econ;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.codisimus.plugins.buttonwarp.commands.ButtonWarpCommand.getWarp;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandInfo implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 0: info((Player) sender, null); return true;
                case 1: info((Player) sender, args.get(0)); return true;
                default: new MainMenu().ShowMenu((Player) sender); return true;
            }
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "info";
    }

    private static void info(Player player, String name) {
        //Cancel if the Warp was not found
        Warp warp = getWarp(player, name);
        if (warp == null) {
            return;
        }

        String type = "Player";
        if (warp.global) {
            type = "global";
        }

        String line = Colorizer.descColor + "Name: " + Colorizer.infoColor + warp.name;
        if (Econ.economy != null) {
            line += Colorizer.descColor + " Amount: " + Colorizer.infoColor + Econ.format(warp.amount)
                    + Colorizer.descColor + " Money Source: " + Colorizer.infoColor + warp.source;
        }

        player.sendMessage(line);
        player.sendMessage(Colorizer.descColor + "Warp Location: " + Colorizer.infoColor + warp.world + ", "
                + (int) warp.x + ", " + (int) warp.y + ", "
                + (int) warp.z + Colorizer.descColor + " Reset Type: " + Colorizer.infoColor + type);
        player.sendMessage(Colorizer.descColor + "Reset Time: " + Colorizer.infoColor + warp.days + " days, "
                + warp.hours + " hours, " + warp.minutes
                + " minutes, and " + warp.seconds + " seconds.");
        player.sendMessage(Colorizer.infoColor + "Commands: " + Colorizer.infoColor + warp.commands);
        player.sendMessage(Colorizer.infoColor + "Restricted: " + Colorizer.infoColor + warp.restricted);
        player.sendMessage(Colorizer.infoColor + "Item Cost Type: " + Colorizer.infoColor + warp.itemType);
        player.sendMessage(Colorizer.infoColor + "Item Name: " + Colorizer.infoColor + warp.itemName);
        player.sendMessage(Colorizer.infoColor + "Item Cost Amount: " + Colorizer.infoColor + warp.itemAmount);
    }
}
