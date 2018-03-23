package com.codisimus.plugins.buttonwarp.Menu;

import com.conquestiamc.GUI.buttons.BackButton;
import com.conquestiamc.GUI.buttons.Button;
import com.conquestiamc.GUI.menu.Menu;
import com.conquestiamc.GUI.menu.MenuInteractionEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Spearhartt on 1/31/2017.
 */
public class ConfirmationMenu extends Menu {
    public Menu previous;
    public String command;

    public ConfirmationMenu(Player player, Menu prevMenu, String command) {
        this.name = ChatColor.RED + "Are you sure?";
        previous = prevMenu;
        this.command = command;
    }

    protected void CreateMenu(Player player) {
        Button yes = new Button();
        yes.setIcon(Material.EMERALD);
        yes.setName(ChatColor.GREEN + "YES");
        yes.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                player.performCommand(command);
                previous.ShowMenu(player);
            }
        });
        menuMap.put(1, yes);

        BackButton no = new BackButton(previous);
        no.setIcon(Material.BARRIER);
        no.setName(ChatColor.RED + "NO");
        menuMap.put(7, no);
    }
}
