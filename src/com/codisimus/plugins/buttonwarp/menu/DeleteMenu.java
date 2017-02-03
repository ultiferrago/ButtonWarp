package com.codisimus.plugins.buttonwarp.menu;

import com.codisimus.plugins.buttonwarp.Warp;
import com.conquestiamc.GUI.Menu;
import com.conquestiamc.GUI.MenuInteractionEvent;
import com.conquestiamc.GUI.buttons.Button;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 1/31/2017.
 */
public class DeleteMenu extends NearbyMenu {
    public Menu previous;
    public ArrayList<Warp> sortedWarps;

    public DeleteMenu(Menu prevMenu, ArrayList<Warp> warps) {
        super(prevMenu, warps);
        previous = prevMenu;
        sortedWarps = warps;
    }

    @Override
    public Button buttonAction(String warpName, Button button) {
        button.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                new ConfirmationMenu(menuInteractionEvent.getInteractor(), previous, "bw delete " + warpName);
            }
        });
        return button;
    }
}
