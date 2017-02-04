package com.codisimus.plugins.buttonwarp.menu;

import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.utils.WarpBook;
import com.codisimus.plugins.buttonwarp.utils.WarpMenuUtils;
import com.conquestiamc.GUI.menu.Menu;
import com.conquestiamc.GUI.menu.MenuInteractionEvent;
import com.conquestiamc.GUI.buttons.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class NearbyMenu extends Menu {
    public Menu previous;
    public ArrayList<Warp> sortedWarps;
    public WarpMenuUtils util = new WarpMenuUtils();

    public NearbyMenu(Menu prevMenu, ArrayList<Warp> warps) {
        setName(ChatColor.YELLOW + "Nearby Warps");
        previous = prevMenu;
        sortedWarps = warps;
    }

    protected void CreateMenu(Player player) {
        NearbyMenu thisMenu = this;
        this.name = ChatColor.YELLOW + "Nearby Warps";
        int menuLoc = 0;

        if (sortedWarps == null) {
            sortedWarps = util.getNearestWarps(player);
        }

        Button nearButton = new Button();
        nearButton.setIcon(Material.WOOL);
        nearButton.setName(ChatColor.AQUA + "Sort Nearest Warp");
        nearButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                new NearbyMenu(new MainMenu(), util.getNearestWarps(player)).ShowMenu(player);
            }
        });
        menuMap.put(0, nearButton);

        Button alphaButton = new Button();
        alphaButton.setIcon(Material.WOOL);
        alphaButton.setName(ChatColor.AQUA + "Sort Alphabetical");
        alphaButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                new NearbyMenu(new MainMenu(), util.getAlphabetizedWarps(sortedWarps)).ShowMenu(player);
            }
        });
        menuMap.put(1, alphaButton);

        Button linkButton = new Button();
        linkButton.setIcon(Material.WOOL);
        linkButton.setName(ChatColor.AQUA + "Sort Nearest Linked");
        linkButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                new NearbyMenu(new MainMenu(), util.getNearestLinked(sortedWarps, player)).ShowMenu(player);
            }
        });
        menuMap.put(2, linkButton);

        Button listButton = new Button();
        listButton.setIcon(Material.BOOK);
        listButton.setName(ChatColor.AQUA + "Gives a book of all warps.");
        listButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                new WarpBook().giveBook(player);
                ShowMenu(player);
            }
        });
        menuMap.put(3, listButton);

        menuLoc = 9;

        for (Button button : populateWarps(sortedWarps)) {
            menuMap.put(menuLoc++, button);
        }

        addBackButton(8, previous);

    }

    /** Populates all warp buttons */
    public ArrayList<Button> populateWarps(ArrayList<Warp> warps) {
        ArrayList<Button> warpButtons = new ArrayList<>();

        if (warps != null) {
            for (Warp warp : warps) {
                if (warpButtons.size() >= 44) {
                    break;
                }
                Button warpButton = new Button();
                warpButton.setName(ChatColor.YELLOW + "Warp: " + ChatColor.LIGHT_PURPLE + warp.name);
                warpButton.setIcon(Material.COMPASS);
                warpButton = util.warpDescription(warpButton, warp);
                warpButton = buttonAction(warp.name, warpButton);
                warpButtons.add(warpButton);
            }
        }

        return warpButtons;
    }

    public Button buttonAction(String warpName, Button button) {
        button.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                menuInteractionEvent.getInteractor().performCommand("bw info " + warpName);
            }
        });
        return button;
    }
}
