package com.codisimus.plugins.buttonwarp.menu;

import com.conquestiamc.GUI.API.BackButton;
import com.conquestiamc.GUI.API.Button;
import com.conquestiamc.GUI.API.Menu;
import com.conquestiamc.GUI.API.MenuInteractionEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Spearhartt on 1/31/2017.
 */
public class ConfirmationMenu extends Menu {
    public ConfirmationMenu(Player player, Menu prevMenu, String command) {
        this.name = ChatColor.RED + "Are you sure?";

        Button yes = new Button();
        yes.setIcon(Material.EMERALD);
        yes.setName(ChatColor.GREEN + "YES");
        yes.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                player.performCommand(command);
                prevMenu.ShowMenu(player);
            }
        });
        menuMap.put(1, yes);

        BackButton no = new BackButton(prevMenu);
        no.setIcon(Material.BARRIER);
        no.setName(ChatColor.RED + "NO");
        menuMap.put(7, no);
    }
}
