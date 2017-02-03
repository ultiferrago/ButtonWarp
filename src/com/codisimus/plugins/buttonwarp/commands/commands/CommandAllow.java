package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.LinkedButton;
import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpSetupMenu;
import com.codisimus.plugins.buttonwarp.menu.MainMenu;
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
public class CommandAllow implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            if (args.size() == 1 && args.get(0).startsWith("item")) {
                allow((Player) sender);
            } else {
                new HelpSetupMenu(new MainMenu()).ShowMenu((Player) sender);
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "allow";
    }

    private static void allow(Player player) {
        Block block = player.getTargetBlock((Set<Material>)null, 10);

        //Find the Warp that will be modified using the target Block
        Warp warp = ButtonWarp.findWarp(block);

        //Cancel if the Warp does not exist
        if (warp == null ) {
            player.sendMessage(Colorizer.badColor + "Target Block is not linked to a Warp");
            return;
        }

        LinkedButton button = warp.findButton(block);
        button.takeItems = true;

        player.sendMessage(Colorizer.normColor + "Players may take items when using this Button to Warp");
        warp.save();
    }
}
