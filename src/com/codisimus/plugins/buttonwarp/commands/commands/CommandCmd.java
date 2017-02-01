package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpSetupMenu;
import com.codisimus.plugins.buttonwarp.menu.MainMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.codisimus.plugins.buttonwarp.Warp.getWarp;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandCmd implements CqCommand {

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            if (args.size() < 2) {
                new HelpSetupMenu(new MainMenu()).ShowMenu((Player) sender);
                return true;
            }

            if (args.get(0).equalsIgnoreCase("add") || args.get(0).equalsIgnoreCase("remove")) {
                setCommand((Player) sender, null, args.get(0).equalsIgnoreCase("add"), concatArgs(args, 1));
            } else {
                setCommand((Player) sender, args.get(0), args.get(1).equalsIgnoreCase("add"), concatArgs(args, 2));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "cmd";
    }

    public static void setCommand(Player player, String name, boolean add, String cmd) {
        if (cmd.startsWith("/")) {
            cmd = cmd.substring(1);
        }

        Warp warp = getWarp(player, name);
        if (warp == null) {
            return;
        }

        for (String string: warp.commands) {
            if (cmd.equals(string)) {
                /* The command was found */
                if (!add) {
                    warp.commands.remove(cmd);
                    player.sendMessage(Colorizer.warpColor + cmd + Colorizer.normColor + " removed as a command "
                            + "for Warp " + Colorizer.warpColor + warp.name);
                    warp.save();
                } else {
                    player.sendMessage(Colorizer.warpColor + cmd + Colorizer.normColor + " is already a command "
                            + "for Warp " + Colorizer.warpColor + warp.name);
                }
                return;
            }
        }

        /* The command was not found */
        if (add) {
            warp.commands.add(cmd);
            player.sendMessage(Colorizer.warpColor + cmd + Colorizer.normColor + " added as a command "
                    + "for Warp " + Colorizer.warpColor + warp.name);
            warp.save();
        } else {
            player.sendMessage(Colorizer.warpColor + cmd + Colorizer.normColor + " was not found as a command "
                    + "for Warp " + Colorizer.warpColor + warp.name);
        }
    }

    public static String concatArgs(ArrayList<String> args, int first) {
        return concatArgs(args, first, args.size() - 1);
    }

    public static String concatArgs(ArrayList<String> args, int first, int last) {
        String string = "";
        for (int i = first; i <= last; i++) {
            string += " " + args.get(i);
        }
        return string.isEmpty() ? string : string.substring(1);
    }
}
