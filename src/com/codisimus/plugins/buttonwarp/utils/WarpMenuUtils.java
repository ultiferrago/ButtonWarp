package com.codisimus.plugins.buttonwarp.utils;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.LinkedButton;
import com.codisimus.plugins.buttonwarp.Warp;
import com.conquestiamc.GUI.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Spearhartt on 2/3/2017.
 */
public class WarpMenuUtils {

    /** Finds the nearest warps based on the nearest linked object. */
    public ArrayList<Warp> getNearestLinked(ArrayList<Warp> warps, Player player) {
        Collections.sort(warps, new Comparator<Warp>() {
            @Override
            public int compare(Warp o1, Warp o2) {
                int o1Near = Integer.MAX_VALUE;
                int o2Near = Integer.MAX_VALUE;

                //Find nearest relevant linked button
                for (LinkedButton lButton : o1.buttons) {
                    if (lButton.getLocation() != null && lButton.getLocation().getWorld() == player.getWorld()
                            && Math.floor(lButton.getLocation().distanceSquared(player.getLocation())) < o1Near) {
                        o1Near = (int)Math.floor(lButton.getLocation().distanceSquared(player.getLocation()));
                    }
                }

                //Find nearest relevant linked button
                for (LinkedButton lButton : o2.buttons) {
                    if (lButton.getLocation() != null && lButton.getLocation().getWorld() == player.getWorld()
                            && Math.floor(lButton.getLocation().distanceSquared(player.getLocation())) < o2Near) {
                        o2Near = (int)Math.floor(lButton.getLocation().distanceSquared(player.getLocation()));
                    }
                }

                return o1Near - o2Near;
            }
        });
        return warps;
    }

    /** Finds all warps in the current world as the player and sorts
     *  by proximity */
    public ArrayList<Warp> getNearestWarps(Player player) {
        ArrayList<Warp> sortedWarps = new ArrayList<>();

        for (Warp warp : ButtonWarp.getWarps()) {
            if (warp.world == null || Bukkit.getServer().getWorld(warp.world) != player.getWorld()) {
                sortedWarps.remove(warp);
            } else {
                sortedWarps.add(warp);
            }
        }

        Collections.sort(sortedWarps, new Comparator<Warp>() {
            @Override
            public int compare(Warp o1, Warp o2) {
                return (int) Math.floor(o1.getLocation().distanceSquared(player.getLocation()) - o2.getLocation().distanceSquared(player.getLocation()));
            }
        });

        return sortedWarps;
    }

    /** Alphabetizes warps */
    public ArrayList<Warp> getAlphabetizedWarps(ArrayList<Warp> warps) {
        Collections.sort(warps, new Comparator<Warp>() {
            @Override
            public int compare(Warp o1, Warp o2) {
                return Collator.getInstance().compare(o1.name, o2.name);
            }
        });
        return warps;
    }


    /** Formats warp properties for the menu */
    public Button warpDescription(Button warpButton, Warp warp) {
        warpButton.addLoreLine(ChatColor.GOLD + "" + ChatColor.BOLD + "Warp Information");
        warpButton.addLoreLine(ChatColor.GRAY + "World: " + ChatColor.AQUA + warp.world);
        warpButton.addLoreLine(ChatColor.GRAY + "X:" + ChatColor.AQUA + Math.floor(warp.x) + ChatColor.GRAY + " Y:"
                + ChatColor.AQUA + Math.floor(warp.y) + ChatColor.GRAY + " Z:" + ChatColor.AQUA + Math.floor(warp.z));
        if (Econ.economy != null) {
            warpButton.addLoreLine(ChatColor.GRAY + "Amount: " + ChatColor.GREEN + Econ.format(warp.amount) + ChatColor.GRAY
                    + "Money Source: " + ChatColor.GREEN + warp.source);
        }
        warpButton.addLoreLine(ChatColor.GRAY + "Reset Time: " + ChatColor.AQUA + warp.days + "D " + warp.hours + "H "
                + warp.minutes + "M " + warp.seconds + "S");
        warpButton.addLoreLine(ChatColor.GRAY + "Commands: " + ChatColor.YELLOW + warp.commands);
        warpButton.addLoreLine(ChatColor.GRAY + "Restricted: " + ChatColor.RED + warp.restricted);
        warpButton.addLoreLine(ChatColor.GRAY + "Item Cost Type: " + ChatColor.YELLOW + warp.itemType);
        warpButton.addLoreLine(ChatColor.GRAY + "Item Name: " + ChatColor.YELLOW + warp.itemName);
        warpButton.addLoreLine(ChatColor.GRAY + "Item Cost Amount: " + ChatColor.YELLOW + warp.itemAmount);
        return warpButton;
    }
}
