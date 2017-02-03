package com.codisimus.plugins.buttonwarp.menu;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.conquestiamc.GUI.buttons.BackButton;
import com.conquestiamc.GUI.buttons.Button;
import com.conquestiamc.GUI.Menu;
import com.conquestiamc.GUI.MenuInteractionEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class HelpCreateMenu extends Menu {
    public Menu previous;

    public HelpCreateMenu(Menu prevMenu) {
        setName(ChatColor.YELLOW + "Create Warps");
        previous = prevMenu;
    }

    protected void CreateMenu(Player player) {
        final HelpCreateMenu thisMenu = this;
        String command = ButtonWarp.command_label;

        int loc = 0;

        this.name = ChatColor.YELLOW + "Warp Creation Menu";

        Button mWarp = new Button();
        mWarp.setIcon(Material.WORKBENCH);
        mWarp.setName(ChatColor.YELLOW + "Make Warp");
        mWarp.addLoreLine(ChatColor.AQUA + "/" + command + " make <warp-name>");
        mWarp.addLoreLine(ChatColor.GRAY + "Creates warp at current location.");
        mWarp.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, mWarp);


        Button mvWarp = new Button();
        mvWarp.setIcon(Material.MINECART);
        mvWarp.setName(ChatColor.YELLOW + "Move Warp");
        mvWarp.addLoreLine(ChatColor.AQUA + "/" + command + " move <warp-name>");
        mvWarp.addLoreLine(ChatColor.GRAY + "Moves warp to your current location.");
        mvWarp.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, mvWarp);

        Button lWarp = new Button();
        lWarp.setIcon(Material.LEASH);
        lWarp.setName(ChatColor.YELLOW + "Link Warp");
        lWarp.addLoreLine(ChatColor.AQUA + "/" + command + " link <warp-name>");
        lWarp.addLoreLine(ChatColor.GRAY + "Links target block to warp.");
        lWarp.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, lWarp);

        Button uWarp = new Button();
        uWarp.setIcon(Material.SHEARS);
        uWarp.setName(ChatColor.YELLOW + "Unlink Warp");
        uWarp.addLoreLine(ChatColor.AQUA + "/" + command + " unlink");
        uWarp.addLoreLine(ChatColor.GRAY + "Unlinks target block with warp.");
        uWarp.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, uWarp);

        Button dWarp = new Button();
        dWarp.setIcon(Material.BARRIER);
        dWarp.setName(ChatColor.RED + "Delete Warp");
        dWarp.addLoreLine(ChatColor.AQUA + "/" + command + "delete <name>");
        dWarp.addLoreLine(ChatColor.GRAY + "Deletes warp.");
        dWarp.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                new DeleteMenu(thisMenu, null).ShowMenu(menuInteractionEvent.getInteractor());
            }
        });
        menuMap.put(loc++, dWarp);

        addBackButton(getInventorySize() - 1, previous);
    }
}
