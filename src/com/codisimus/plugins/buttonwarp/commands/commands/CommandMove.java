package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpCreateMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandMove implements CqCommand {

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 2:
                    move((Player) sender, args.get(1), false);
                    return true;
                case 3:
                    if (args.get(2).equalsIgnoreCase("nowhere")) {
                        move((Player) sender, args.get(1), true);
                        return true;
                    }
                    break;
                default:
                    break;
            }

            new HelpCreateMenu().ShowMenu((Player) sender);
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "move";
    }

    private static void move(Player player, String name, boolean noWhere) {
        //Cancel if the Warp with the given name does not exist
        Warp warp = ButtonWarp.findWarp(name);
        if (warp == null ) {
            player.sendMessage(Colorizer.normColor + "Warp " + Colorizer.warpColor + name + Colorizer.normColor + " does not exsist.");
            return;
        }

        if (noWhere) {
            //Set the Warp to a null Location
            warp.world = null;
            player.sendMessage(Colorizer.normColor + "Warp " + Colorizer.warpColor + name + Colorizer.normColor + " moved to nowhere");
        } else {
            //Set the Warp to the Player's Location
            warp.world = player.getWorld().getName();
            Location location = player.getLocation();
            warp.x = location.getX();
            warp.y = location.getY();
            warp.z = location.getZ();
            warp.pitch = location.getPitch();
            warp.yaw = location.getYaw();
            player.sendMessage(Colorizer.normColor + "Warp " + Colorizer.warpColor + name + Colorizer.normColor + " moved to current location");
        }

        warp.save();
    }
}
