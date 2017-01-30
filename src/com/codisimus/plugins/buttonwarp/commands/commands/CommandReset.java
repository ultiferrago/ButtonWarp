package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.MainMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandReset implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 1: reset((Player) sender, null); return true;
                case 2: reset((Player) sender, args.get(1)); return true;
                default: break;
            }

            new MainMenu().ShowMenu((Player) sender);
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "reset";
    }

    private static void reset(Player player, String name) {
        //Reset the target Button if a name was not provided
        if (name == null) {
            //Find the Warp that will be reset using the given name
            Block block = player.getTargetBlock((Set<Material>)null, 10);
            Warp warp = ButtonWarp.findWarp(block);

            //Cancel if the Warp does not exist
            if (warp == null ) {
                player.sendMessage("§4Target Block is not linked to a Warp");
                return;
            }

            warp.reset(block);

            player.sendMessage("§5Target Button has been reset.");
            return;
        }

        //Reset all Buttons in every Warp if the name provided is 'all'
        if (name.equals("all")) {
            for (Warp warp: ButtonWarp.getWarps()) {
                warp.reset(null);
            }

            player.sendMessage("§5All Buttons in all Warps have been reset.");
            return;
        }

        //Find the Warp that will be reset using the given name
        Warp warp = ButtonWarp.findWarp(name);

        //Cancel if the Warp does not exist
        if (warp == null ) {
            player.sendMessage("§4Warp §6" + name + "§4 does not exsist.");
            return;
        }

        //Reset all Buttons linked to the Warp
        warp.reset(null);

        player.sendMessage("§5All Buttons in Warp §6"
                + name + "§5 have been reset.");
        warp.save();
    }
}
