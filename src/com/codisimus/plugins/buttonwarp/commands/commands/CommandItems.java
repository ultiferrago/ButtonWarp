package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpSetupMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.codisimus.plugins.buttonwarp.commands.ButtonWarpCommand.getWarp;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandItems implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 3:
                    try {
                        items((Player) sender, null, Integer.parseInt(args.get(1)), args.get(2));
                        return true;
                    } catch (Exception notInt) {
                        break;
                    }
                case 4:
                    try {
                        items((Player) sender, args.get(1), Integer.parseInt(args.get(2)), args.get(3));
                        return true;
                    } catch (Exception notInt) {
                        break;
                    }

                default: break;
            }
            new HelpSetupMenu().ShowMenu((Player) sender);
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "items";
    }

    private static void items(Player player, String name, int amount, String itemSelect) {
        //Cancel if the Warp was not found
        Warp warp = getWarp(player, name);
        if (warp == null) {
            return;
        }

        if (amount < 0) {
            if (itemSelect.toLowerCase().contentEquals("hand")) {
                if (player.getEquipment().getItemInMainHand() == null) {
                    player.sendMessage(Colorizer.normColor + "You must have an item in hand yo use this command.");
                } else {
                    warp.itemType = player.getEquipment().getItemInMainHand().getType();
                    warp.itemAmount = amount;
                    warp.itemName = player.getEquipment().getItemInMainHand().getItemMeta().getDisplayName();
                    player.sendMessage(Colorizer.normColor + "Item cost set to " + amount + " " + player.getEquipment().getItemInMainHand().getItemMeta().getDisplayName() + "(s)");
                }
            } else {
                for (Material mat: Material.values()) {
                    if (mat.toString().equalsIgnoreCase(itemSelect)) {
                        warp.itemType = mat;
                        warp.itemAmount = amount;
                        warp.itemName = null;
                        player.sendMessage(Colorizer.normColor + "Item cost set to " + amount + " " + itemSelect);
                    }
                }
            }
        } else if (amount > 0) {
            if (itemSelect.toLowerCase().contentEquals("hand")) {
                if (player.getEquipment().getItemInMainHand() == null) {
                    player.sendMessage(Colorizer.normColor + "You must have an item in hand yo use this command.");
                } else {
                    warp.itemType = player.getEquipment().getItemInMainHand().getType();
                    warp.itemAmount = amount;
                    warp.itemName = player.getEquipment().getItemInMainHand().getItemMeta().getDisplayName();
                    player.sendMessage(Colorizer.normColor + "Item cost set to " + amount + " " + player.getEquipment().getItemInMainHand().getItemMeta().getDisplayName() + "(s)");
                }
            } else {
                for (Material mat: Material.values()) {
                    if (mat.toString().equalsIgnoreCase(itemSelect)) {
                        warp.itemType = mat;
                        warp.itemAmount = amount;
                        warp.itemName = null;
                        player.sendMessage(Colorizer.normColor + "Item reward set to " + amount + " " + itemSelect);
                    }
                }
            }
        } else {
            //Occurs if amount = 0. Nothing happens.
        }

        if (warp.itemType == Material.AIR) {
            player.sendMessage(itemSelect + Colorizer.normColor + " is not a valid item name.");
        }

        warp.save();
    }
}
