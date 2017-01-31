package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpSetupMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.codisimus.plugins.buttonwarp.commands.ButtonWarpCommand.getWarp;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandAccess  implements CqCommand {

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 1: access((Player) sender, null, args.get(0)); return true;
                case 2: access((Player) sender, args.get(0), args.get(1)); return true;
                default:
                    new HelpSetupMenu((Player) sender).ShowMenu((Player) sender);
                    return true;
            }
        }

        return false;
    }

    @Override
    public String getCommandLabel() {
        return "access";
    }

    private static void access(Player player, String name, String access) {
        //Cancel if the Warp was not found
        Warp warp = getWarp(player, name);
        if (warp == null) {
            return;
        }

        if (access.equals("public")) {
            warp.restricted = false;
        } else if (access.equals("restricted")) {
            warp.restricted = true;
        } else {
            player.sendMessage("§6" + access + "§4is not valid access type. Use §6public §4or §6restricted");
            return;
        }
        player.sendMessage("§5Access for Warp §6" + warp.name
                + "§5 has been set to §6" + access);

        warp.save();
    }
}
