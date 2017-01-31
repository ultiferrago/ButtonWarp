package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.MainMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandRl implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            if (args.size() == 0) {
                ButtonWarp.rl((Player) sender);
            } else {
                new MainMenu((Player) sender).ShowMenu((Player) sender);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "rl";
    }
}
