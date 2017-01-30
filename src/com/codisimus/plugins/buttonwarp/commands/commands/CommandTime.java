package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpSetupMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.codisimus.plugins.buttonwarp.commands.ButtonWarpCommand.getWarp;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandTime implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 5:
                    try {
                        time((Player) sender, null, Integer.parseInt(args.get(1)), Integer.parseInt(args.get(2)),
                                Integer.parseInt(args.get(3)), Integer.parseInt(args.get(4)));
                        return true;
                    } catch (Exception notInt) {
                        new HelpSetupMenu().ShowMenu((Player) sender);
                        break;
                    }

                case 6:
                    try {
                        time((Player) sender, args.get(1), Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3)),
                                Integer.parseInt(args.get(4)), Integer.parseInt(args.get(5)));
                        return true;
                    } catch (Exception notInt) {
                        new HelpSetupMenu().ShowMenu((Player) sender);
                        break;
                    }

                default: break;
            }

            new HelpSetupMenu().ShowMenu((Player) sender);
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "time";
    }

    private static void time(Player player, String name, int days, int hours, int minutes, int seconds) {
        //Cancel if the Warp was not found
        Warp warp = getWarp(player, name);
        if (warp == null) {
            return;
        }

        warp.days = days;
        warp.hours = hours;
        warp.minutes = minutes;
        warp.seconds = seconds;
        player.sendMessage(Colorizer.normColor + "Reset time for Warp " + Colorizer.warpColor + warp.name
                + Colorizer.normColor + " has been set to " + Colorizer.warpColor + days + " days, " + hours
                + " hours, " + minutes + " minutes, and " + seconds
                + " seconds");

        warp.save();
    }
}
