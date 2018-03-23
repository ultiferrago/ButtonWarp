package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.Menu.HelpSetupMenu;
import com.codisimus.plugins.buttonwarp.Menu.MainMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.codisimus.plugins.buttonwarp.Warp.getWarp;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandReward implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 1:
                    try {
                        amount((Player) sender, null, Math.abs(Double.parseDouble(args.get(0))));
                        return true;
                    } catch (Exception notDouble) {
                        break;
                    }

                case 2:
                    try {
                        amount((Player) sender, args.get(0), Math.abs(Double.parseDouble(args.get(1))));
                        return true;
                    } catch (Exception notDouble) {
                        break;
                    }
                default: break;
            }

            new HelpSetupMenu(new MainMenu()).ShowMenu((Player) sender);
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "reward";
    }

    private static void amount(Player player, String name, double amount) {
        //Cancel if the Warp was not found
        Warp warp = getWarp(player, name);
        if (warp == null) {
            return;
        }

        warp.amount = amount;
        player.sendMessage(Colorizer.normColor + "Amount for Warp " + Colorizer.warpColor + warp.name
                + Colorizer.normColor + " has been set to " + Colorizer.warpColor + amount);
        warp.save();
    }
}
