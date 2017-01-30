package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.MainMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import com.codisimus.plugins.buttonwarp.utils.Econ;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandList implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            switch (args.size()) {
                case 1: list((Player) sender, null); return true;
                case 2: list((Player) sender, args.get(1)); return true;
                default: new MainMenu().ShowMenu((Player) sender); return true;
            }
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "list";
    }

    private static void list(Player player, String keyword) {
        String warpList = "Current Warps: " + Colorizer.warpColor;

        //Display each Warp, including the amount if an Economy plugin is present
        if (Econ.economy != null) {
            for (Warp warp : ButtonWarp.getWarps()) {
                if (keyword == null || StringUtils.containsIgnoreCase(warp.name, keyword)) {
                    if (warp.amount != 0) {
                        warpList += warp.name + "§f(" + Colorizer.econColor + Econ.format(warp.amount) + "§f)§7, " + Colorizer.warpColor;
                    } else {
                        warpList += warp.name + "§7, " + Colorizer.warpColor;
                    }
                }
            }
        } else {
            for (Warp warp : ButtonWarp.getWarps()) {
                if (keyword == null || StringUtils.containsIgnoreCase(warp.name, keyword)) {
                    warpList += warp.name + "§7, " + Colorizer.warpColor;
                }
            }
        }

        player.sendMessage(warpList.substring(0, warpList.length() - 4));
    }
}
