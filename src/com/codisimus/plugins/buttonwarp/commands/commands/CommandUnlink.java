package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpCreateMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandUnlink implements CqCommand {
    public static final EnumSet<Material> LINKABLE = EnumSet.of(
            Material.LEVER, Material.STONE_PLATE, Material.WOOD_PLATE,
            Material.STONE_BUTTON, Material.WOOD_BUTTON, Material.TRIPWIRE,
            Material.TRIPWIRE_HOOK, Material.DETECTOR_RAIL);

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            if (args.size() == 1) {
                unlink((Player) sender);
                return true;
            } else {
                new HelpCreateMenu().ShowMenu((Player) sender);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "unlink";
    }

    private static void unlink(Player player) {
        //Cancel if the Player is not targeting a correct Block type
        Block block = player.getTargetBlock((Set<Material>)null, 10);
        Material type = block.getType();
        if (!LINKABLE.contains(type)) {
            player.sendMessage(Colorizer.badColor + "You are targeting a " + Colorizer.warpColor + type.name()
                    + "," + Colorizer.badColor + " linkable items are "
                    + LINKABLE.toString());
            return;
        }

        //Cancel if the Block is not linked to a Warp
        Warp warp = ButtonWarp.findWarp(block);
        if (warp == null) {
            player.sendMessage(Colorizer.badColor + "Target Block is not linked to a Warp");
            return;
        }

        warp.buttons.remove(warp.findButton(block));
        player.sendMessage(Colorizer.normColor + "Button has been unlinked from Warp " + Colorizer.warpColor
                + warp.name);
        warp.save();
    }
}
