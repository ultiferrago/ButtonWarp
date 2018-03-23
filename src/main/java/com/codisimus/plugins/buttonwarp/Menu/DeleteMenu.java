package com.codisimus.plugins.buttonwarp.Menu;

import com.codisimus.plugins.buttonwarp.Warp;
import com.conquestiamc.GUI.menu.Menu;
import com.conquestiamc.GUI.menu.MenuInteractionEvent;
import com.conquestiamc.GUI.buttons.Button;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 1/31/2017.
 */
public class DeleteMenu extends NearbyMenu {
    public Menu previous;
    public ArrayList<Warp> sortedWarps;
    public Menu thisMenu = this;

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
                new ConfirmationMenu(menuInteractionEvent.getInteractor(), thisMenu, "bw delete " + warpName).ShowMenu(menuInteractionEvent.getInteractor());
            }
        });
        return button;
    }
}
