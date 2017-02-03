package com.codisimus.plugins.buttonwarp.menu;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import com.conquestiamc.GUI.Menu;
import com.conquestiamc.GUI.MenuInteractionEvent;
import com.conquestiamc.GUI.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class NearbyMenu extends Menu {
    public Menu previous;

    public NearbyMenu(Menu prevMenu) {
        setName(ChatColor.YELLOW + "Nearby Warps");
        previous = prevMenu;
    }

    protected void CreateMenu(Player player) {
        NearbyMenu thisMenu = this;
        this.name = ChatColor.YELLOW + "Nearby Warps";
        int menuLoc = 0;

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
                return (int)Math.floor(o1.getLocation().distanceSquared(player.getLocation()) - o2.getLocation().distanceSquared(player.getLocation()));
            }
        });

        Button alphaButton = new Button();
        alphaButton.setIcon(Material.WOOL);
        alphaButton.setName(ChatColor.AQUA + "Sort Alphabetical");
        alphaButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //stuff
            }
        });
        menuMap.put(menuLoc++, alphaButton);

        Button linkButton = new Button();
        linkButton.setIcon(Material.WOOL);
        linkButton.setName(ChatColor.AQUA + "Sort Nearest Linked");
        linkButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //stuff
            }
        });
        menuMap.put(menuLoc++, linkButton);

        Button nearButton = new Button();
        nearButton.setIcon(Material.WOOL);

        for (Warp warp : sortedWarps) {
            if (menuLoc >= 52) {
                break;
            }
            final Button warpButton = new Button();
            warpButton.setIcon(Material.COMPASS);
            warpButton.setName(ChatColor.YELLOW + "Warp: " + ChatColor.LIGHT_PURPLE + warp.name);
            menuMap.put(menuLoc++, warpButton);
        }

        addBackButton(getInventorySize() - 1, previous);

    }
}
