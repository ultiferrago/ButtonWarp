package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.menu.HelpSetupMenu;
import com.codisimus.plugins.buttonwarp.menu.HelpCreateMenu;
import com.codisimus.plugins.buttonwarp.menu.HelpSetupMenu;
import com.codisimus.plugins.buttonwarp.menu.MainMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandHelp implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            if (args.size() == 1) {
                switch (args.get(1).toLowerCase()) {
                    case "create":
                        new HelpCreateMenu(new MainMenu()).ShowMenu((Player) sender);
                        return true;
                    case "setup":
                        new HelpSetupMenu(new MainMenu()).ShowMenu((Player) sender);
                        return true;
                    case "button":
                        new HelpSetupMenu(new MainMenu()).ShowMenu((Player) sender);
                        return true;
                    default:
                        break;
                }
                new MainMenu().ShowMenu((Player) sender);
                return true;
            } else {
                new MainMenu().ShowMenu((Player) sender);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "help";
    }
}
