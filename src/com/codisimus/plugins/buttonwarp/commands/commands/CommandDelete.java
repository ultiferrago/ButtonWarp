package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpCreateMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.codisimus.plugins.buttonwarp.commands.ButtonWarpCommand.getWarp;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandDelete implements CqCommand {

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 1:
                    delete((Player) sender, null);
                    return true;
                case 2:
                    delete((Player) sender, args.get(1));
                    return true;
                default:
                    new HelpCreateMenu().ShowMenu((Player) sender);
            }
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "delete";
    }

    private static void delete(Player player, String name) {
        //Cancel if the Warp was not found
        Warp warp = getWarp(player, name);
        if (warp == null) {
            return;
        }

        ButtonWarp.removeWarp(warp);
        player.sendMessage(Colorizer.normColor + "Warp " + Colorizer.warpColor + warp.name + Colorizer.normColor + " was deleted!");
    }
}
