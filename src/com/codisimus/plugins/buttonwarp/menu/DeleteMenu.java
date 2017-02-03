package com.codisimus.plugins.buttonwarp.menu;

import com.conquestiamc.GUI.buttons.BackButton;
import com.conquestiamc.GUI.buttons.Button;
import com.conquestiamc.GUI.MenuInteractionEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Spearhartt on 1/31/2017.
 */
public class DeleteMenu extends NearbyMenu {
    public DeleteMenu(Player player) {
        super(new HelpCreateMenu(new MainMenu()));
        DeleteMenu thisMenu = this;
        this.name = ChatColor.RED + "Delete Warps";

        for (int i = 0; i < 52; i++) {
            if (menuMap.containsKey(i) && !(menuMap.get(i) instanceof BackButton)) {
                Button button = menuMap.get(i);
                button.setOnPressedListener(new Button.onButtonPressedListener() {
                    @Override
                    public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                        new ConfirmationMenu(player, thisMenu, "bw delete " + ChatColor.stripColor(button.getName()).substring(6)).ShowMenu(player);
                    }
                });
                menuMap.put(i, button);
            } else if (menuMap.containsKey(i) && menuMap.get(i) instanceof BackButton) {
                addBackButton(i, previous);
            }
        }
    }
}
