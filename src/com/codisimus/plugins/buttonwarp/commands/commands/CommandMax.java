package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.Button;
import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpButtonMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandMax implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            if (args.size() == 2) {
                try {
                    max((Player) sender, Integer.parseInt(args.get(1)));
                    return true;
                } catch (Exception notInt) {
                }
            }

            new HelpButtonMenu().ShowMenu((Player) sender);
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "max";
    }

    private static void max(Player player, int max) {
        Block block = player.getTargetBlock((Set<Material>)null, 10);

        //Find the Warp that will be modified using the target Block
        Warp warp = ButtonWarp.findWarp(block);

        //Cancel if the Warp does not exist
        if (warp == null ) {
            player.sendMessage(Colorizer.badColor + "Target Block is not linked to a Warp");
            return;
        }

        Button button = warp.findButton(block);
        button.max = max;

        player.sendMessage(Colorizer.normColor + "Players may use target Button " + Colorizer.warpColor
                + max + Colorizer.normColor + " times per reset");
        warp.save();
    }
}
