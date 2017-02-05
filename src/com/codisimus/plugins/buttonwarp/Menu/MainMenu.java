package com.codisimus.plugins.buttonwarp.menu;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.conquestiamc.GUI.buttons.Button;
import com.conquestiamc.GUI.buttons.ExitButton;
import com.conquestiamc.GUI.menu.Menu;
import com.conquestiamc.GUI.menu.MenuInteractionEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class MainMenu extends Menu {
    public MainMenu() {
        setName(ChatColor.YELLOW + "ButtonWarp");
    }

    protected void CreateMenu(Player player) {
        final MainMenu thisMenu = this;
        String command = ButtonWarp.command_label;

        this.name = ChatColor.YELLOW + "ButtonWarp";

        int location = 0;

        if(ButtonWarp.hasPermission(player, "commandwarp")) {
            Button cWarp = new Button();
            cWarp.setIcon(Material.COMMAND);
            cWarp.setName(ChatColor.YELLOW + "Command Warp");
            cWarp.addLoreLine(ChatColor.AQUA + "/" + command + " <warp-name>");
            cWarp.addLoreLine(ChatColor.GRAY + "Teleports to the given warp.");
            cWarp.setOnPressedListener(new Button.onButtonPressedListener() {
                @Override
                public void onButtonPressed(MenuInteractionEvent e) {
                    //do something here
                }
            });
            menuMap.put(location++, cWarp);
        }

        if(ButtonWarp.hasPermission(player, "list")) {
            Button lWarps = new Button();
            lWarps.setIcon(Material.BOOK_AND_QUILL);
            lWarps.setName(ChatColor.YELLOW + "List Warps");
            lWarps.addLoreLine(ChatColor.AQUA + "/" + command + " list");
            lWarps.addLoreLine(ChatColor.GRAY + "Lists all warps");
            lWarps.setOnPressedListener(new Button.onButtonPressedListener() {
                @Override
                public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                    new NearbyMenu(thisMenu, null).ShowMenu(player);
                }
            });
            menuMap.put(location++, lWarps);
        }

        if(ButtonWarp.hasPermission(player, "info")) {
            Button iWarp = new Button();
            iWarp.setIcon(Material.PAPER);
            iWarp.setName(ChatColor.YELLOW + "Info");
            iWarp.addLoreLine(ChatColor.AQUA + "/" + command + " info <warp-name>");
            iWarp.addLoreLine(ChatColor.GRAY + "Gives information about the warp.");
            iWarp.setOnPressedListener(new Button.onButtonPressedListener() {
                @Override
                public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                    new NearbyMenu(thisMenu, null).ShowMenu(player);
                }
            });
            menuMap.put(location++, iWarp);
        }

        if(ButtonWarp.hasPermission(player, "reset")) {
            Button rWarp = new Button();
            rWarp.setIcon(Material.LEVER);
            rWarp.setName(ChatColor.YELLOW + "Reset Warp");
            rWarp.addLoreLine(ChatColor.AQUA + "/" + command + " reset <warp-name | all>");
            rWarp.addLoreLine(ChatColor.GRAY + "Resets buttons linked to the warp.");
            rWarp.setOnPressedListener(new Button.onButtonPressedListener() {
                @Override
                public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                    //do something here
                    //list nearby warps
                }
            });
            menuMap.put(location++, rWarp);
        }

        if(ButtonWarp.hasPermission(player, "create")) {
            Button crWarp = new Button();
            crWarp.setIcon(Material.WORKBENCH);
            crWarp.setName(ChatColor.YELLOW + "Warp Creation");
            crWarp.addLoreLine(ChatColor.GRAY + "Warp creation tools.");
            crWarp.setOnPressedListener(new Button.onButtonPressedListener() {
                @Override
                public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                    new HelpCreateMenu(thisMenu).ShowMenu(player);
                }
            });
            menuMap.put(location++, crWarp);

            Button sWarp = new Button();
            sWarp.setIcon(Material.ENCHANTMENT_TABLE);
            sWarp.setName(ChatColor.YELLOW + "Warp Setup");
            sWarp.addLoreLine(ChatColor.GRAY + "Edit warp properties.");
            sWarp.setOnPressedListener(new Button.onButtonPressedListener() {
                @Override
                public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                    new HelpSetupMenu(thisMenu).ShowMenu(player);
                }
            });
            menuMap.put(location++, sWarp);
        }

        addExitButton(getInventorySize() - 1);
    }
}
