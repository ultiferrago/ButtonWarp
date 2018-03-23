package com.codisimus.plugins.buttonwarp.Menu;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.conquestiamc.GUI.buttons.Button;
import com.conquestiamc.GUI.menu.Menu;
import com.conquestiamc.GUI.menu.MenuInteractionEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class HelpSetupMenu extends Menu {
    public Menu previous;

    public HelpSetupMenu(Menu prevMenu) {
        setName(ChatColor.AQUA + "Warp Properties");
        previous = prevMenu;
    }

    protected void CreateMenu(Player player) {
        if (!ButtonWarp.hasPermission(player, "create")) {
            new MainMenu().ShowMenu(player);
        }

        final HelpSetupMenu thisMenu = this;
        this.name = ChatColor.YELLOW + "Warp Setup Menu";
        String command = ButtonWarp.command_label;

        int loc = 0;

        Button msButton = new Button();
        msButton.setIcon(Material.NAME_TAG);
        msButton.setName(ChatColor.YELLOW + "Message");
        msButton.addLoreLine(ChatColor.AQUA + "/" + command + " msg <warp-name> <msg>");
        msButton.addLoreLine(ChatColor.GRAY + "Sets message received after using warp.");
        msButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, msButton);

        Button coButton = new Button();
        coButton.setIcon(Material.IRON_INGOT);
        coButton.setName(ChatColor.YELLOW + "Cost");
        coButton.addLoreLine(ChatColor.AQUA + "/" + command + " cost <warp-name> <amount>");
        coButton.addLoreLine(ChatColor.GRAY + "Sets the cost for using the warp.");
        coButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, coButton);

        Button reButton = new Button();
        reButton.setIcon(Material.GOLD_INGOT);
        reButton.setName(ChatColor.YELLOW + "Reward");
        reButton.addLoreLine(ChatColor.AQUA + "/" + command + " reward <warp-name> <amount>");
        reButton.addLoreLine(ChatColor.GRAY + "Sets the reward for using the warp.");
        reButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, reButton);

        Button itButton = new Button();
        itButton.setIcon(Material.CHEST);
        itButton.setName(ChatColor.YELLOW + "Items");
        itButton.addLoreLine(ChatColor.AQUA + "/" + command + " items <warp-name> <amount> <hand | item>");
        itButton.addLoreLine(ChatColor.GRAY + "Sets the item reward or cost for using the warp.");
        itButton.addLoreLine(ChatColor.GRAY + "Use 'hand' to add the item in your hand.");
        itButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, itButton);

        Button soButton = new Button();
        soButton.setIcon(Material.ENDER_CHEST);
        soButton.setName(ChatColor.YELLOW + "Source");
        soButton.addLoreLine(ChatColor.AQUA + "/" + command + " source <warp-name> server");
        soButton.addLoreLine(ChatColor.GRAY + "Generates or destroys money upon use.");
        soButton.addLoreLine(ChatColor.AQUA + "/" + command + " source <warp-name> <player>");
        soButton.addLoreLine(ChatColor.GRAY + "Gives or takes money from <player> upon use.");
        soButton.addLoreLine(ChatColor.AQUA + "/" + command + " source <warp-name> bank <bank>");
        soButton.addLoreLine(ChatColor.GRAY + "Gives or takes money from <bank> upon use.");
        soButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, soButton);

        Button cmButton = new Button();
        cmButton.setIcon(Material.COMMAND);
        cmButton.setName(ChatColor.YELLOW + "Command");
        cmButton.addLoreLine(ChatColor.AQUA + "/" + command + " cmd <warp-name> <add|remove> <command>");
        cmButton.addLoreLine(ChatColor.GRAY + "Sets a command to be executed.");
        cmButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, cmButton);

        Button tiButton = new Button();
        tiButton.setIcon(Material.WATCH);
        tiButton.setName(ChatColor.YELLOW + "Time");
        tiButton.addLoreLine(ChatColor.AQUA + "/" + command + " time <warp-name> <days> <hrs> <mins> <secs>");
        tiButton.addLoreLine(ChatColor.GRAY + "Sets the cooldown time.");
        tiButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, tiButton);

        Button glButton = new Button();
        glButton.setIcon(Material.MAP);
        glButton.setName(ChatColor.YELLOW + "Global");
        glButton.addLoreLine(ChatColor.AQUA + "/" + command + " global <warp-name> <true|false>");
        glButton.addLoreLine(ChatColor.GRAY + "Toggles global cooldowns.");
        glButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, glButton);

        Button acButton = new Button();
        acButton.setIcon(Material.IRON_DOOR);
        acButton.setName(ChatColor.YELLOW + "Access");
        acButton.addLoreLine(ChatColor.AQUA + "/" + command + " access <name> <public|restricted>");
        acButton.addLoreLine(ChatColor.GRAY + "Determines if you need permission to warp.");
        acButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, acButton);

        Button maButton = new Button();
        maButton.setIcon(Material.WATER_BUCKET);
        maButton.setName(ChatColor.YELLOW + "Max");
        maButton.addLoreLine(ChatColor.AQUA + "/" + command + " max <number>");
        maButton.addLoreLine(ChatColor.GRAY + "Sets max uses per reset");
        maButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, maButton);

        Button alButton = new Button();
        alButton.setIcon(Material.TRAP_DOOR);
        alButton.setName(ChatColor.YELLOW + "Allow");
        alButton.addLoreLine(ChatColor.AQUA + "/" + command + " allow items");
        alButton.addLoreLine(ChatColor.GRAY + "Players can warp with items.");
        alButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, alButton);

        Button deButton = new Button();
        deButton.setIcon(Material.IRON_TRAPDOOR);
        deButton.setName(ChatColor.YELLOW + "Deny");
        deButton.addLoreLine(ChatColor.AQUA + "/" + command + " deny items");
        deButton.addLoreLine(ChatColor.GRAY + "Players cannot warp with items.");
        deButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, deButton);

        Button resButton = new Button();
        resButton.setIcon(Material.GOLDEN_APPLE);
        resButton.setName(ChatColor.YELLOW + "Reset");
        resButton.addLoreLine(ChatColor.AQUA + "/" + command + " reset");
        resButton.addLoreLine(ChatColor.GRAY + "Resets activation times for target button.");
        resButton.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                //do something here
            }
        });
        menuMap.put(loc++, resButton);

        addBackButton(getInventorySize() - 1, previous);
    }
}
