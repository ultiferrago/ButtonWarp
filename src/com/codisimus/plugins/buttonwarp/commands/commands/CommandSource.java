package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.codisimus.plugins.buttonwarp.commands.ButtonWarpCommand.getWarp;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandSource implements CqCommand {

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 2:
                    source((Player) sender, null, false, args.get(1));
                    return true;

                case 3:
                    if (args.get(1).equals("bank")) {
                        source((Player) sender, null, true, args.get(2));
                    } else {
                        source((Player) sender, args.get(1), false, args.get(2));
                    }
                    return true;

                case 4:
                    if (args.get(2).equals("bank")) {
                        source((Player) sender, args.get(1), true, args.get(3));
                    } else {
                        break;
                    }
                    return true;

                default: break;
            }
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "source";
    }

    private static void source(Player player, String name, boolean bank, String source) {
        //Cancel if the Warp was not found
        Warp warp = getWarp(player, name);
        if (warp == null) {
            return;
        }

        if (bank) {
            source = "bank:".concat(source);
        }

        warp.source = source;
        player.sendMessage("§5Money source for Warp §6" + warp.name
                + "§5 has been set to §6" + source);
        warp.save();
    }
}
