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
public class CommandGlobal implements CqCommand {

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 1: //Name is not provided
                    try {
                        global((Player) sender, null, Boolean.parseBoolean(args.get(0)));
                        return true;
                    } catch (Exception notBool) {
                        break;
                    }

                case 2: //Name is provided
                    try {
                        global((Player) sender, args.get(0), Boolean.parseBoolean(args.get(1)));
                        return true;
                    } catch (Exception notBool) {
                        break;
                    }

                default: break;
            }

            new HelpSetupMenu((Player) sender).ShowMenu((Player) sender);
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "global";
    }

    private static void global(Player player, String name, boolean global) {
        //Cancel if the Warp was not found
        Warp warp = getWarp(player, name);
        if (warp == null) {
            return;
        }

        warp.global = global;
        player.sendMessage(Colorizer.normColor + "Warp " + Colorizer.warpColor + name + Colorizer.normColor + " has been set to " + Colorizer.warpColor
                + (global ? "global" : "individual") + Colorizer.normColor + " reset!");

        warp.save();
    }
}
