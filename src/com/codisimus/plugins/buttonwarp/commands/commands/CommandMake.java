package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpCreateMenu;
import com.codisimus.plugins.buttonwarp.menu.MainMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandMake implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if(sender instanceof Player) {
            switch (args.size()) {
                case 1:
                    make((Player)sender, args.get(0), false);
                    return true;
                case 2:
                    if (args.get(1).equalsIgnoreCase("nowhere")) {
                        make((Player)sender, args.get(0), true);
                        return true;
                    }
                    break;
                default: break;
            }

            new HelpCreateMenu(new MainMenu()).ShowMenu((Player)sender);
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "make";
    }

    private static void make(Player player, String name, boolean noWhere) {
        //Cancel if the Warp already exists
        if (ButtonWarp.findWarp(name) != null) {
            player.sendMessage(Colorizer.normColor + "A Warp named " + Colorizer.warpColor + name + Colorizer.normColor + " already exists.");
            return;
        }

        if (noWhere) {
            //Create a Warp with a null Location
            ButtonWarp.addWarp(new Warp(name, null));
            player.sendMessage(Colorizer.normColor + "Warp " + Colorizer.warpColor + name + Colorizer.normColor + " Made!");
        } else {
            //Create a Warp with the Player's Location
            ButtonWarp.addWarp(new Warp(name, player));
            player.sendMessage(Colorizer.normColor + "Warp " + Colorizer.warpColor + name + Colorizer.normColor + " Made at current location!");
        }
    }
}
