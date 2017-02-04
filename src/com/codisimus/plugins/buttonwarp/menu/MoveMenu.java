package com.codisimus.plugins.buttonwarp.menu;

import com.codisimus.plugins.buttonwarp.Warp;
import com.conquestiamc.GUI.menu.Menu;
import com.conquestiamc.GUI.menu.MenuInteractionEvent;
import com.conquestiamc.GUI.buttons.Button;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 2/3/2017.
 */
public class MoveMenu extends NearbyMenu {
    public Menu thisMenu = this;

    public MoveMenu(Menu prevMenu, ArrayList<Warp> warps) {
        super(prevMenu, warps);
    }

    @Override
    public Button buttonAction(String warpName, Button button) {
        button.setOnPressedListener(new Button.onButtonPressedListener() {
            @Override
            public void onButtonPressed(MenuInteractionEvent menuInteractionEvent) {
                new ConfirmationMenu(menuInteractionEvent.getInteractor(), thisMenu, "bw move " + warpName).ShowMenu(menuInteractionEvent.getInteractor());
            }
        });
        return button;
    }
}
