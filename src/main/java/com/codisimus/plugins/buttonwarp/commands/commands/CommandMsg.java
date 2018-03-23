package com.codisimus.plugins.buttonwarp.commands.commands;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.ButtonWarpMessages;
import com.codisimus.plugins.buttonwarp.Warp;
import com.codisimus.plugins.buttonwarp.commands.CqCommand;
import com.codisimus.plugins.buttonwarp.Menu.HelpSetupMenu;
import com.codisimus.plugins.buttonwarp.Menu.MainMenu;
import com.codisimus.plugins.buttonwarp.utils.Colorizer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class CommandMsg implements CqCommand {
    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            if (args.size() < 2) {
                new HelpSetupMenu(new MainMenu()).ShowMenu((Player) sender);
                return true;
            }

            String msg = "";
            for (int i=1; i < args.size(); i++) {
                msg = msg.concat(args.get(i).concat(" "));
            }

            msg((Player) sender, args.get(0), msg);
            return true;
        }
        return false;
    }

    @Override
    public String getCommandLabel() {
        return "msg";
    }

    private static void msg(Player player, String name, String msg) {
        //Find the Warp that will be modified using the given name
        Warp warp = ButtonWarp.findWarp(name);

        //Cancel if the Warp does not exist
        if (warp == null ) {
            player.sendMessage(Colorizer.badColor + "Warp " + Colorizer.warpColor + name + Colorizer.badColor + "does not exist.");
            return;
        }

        warp.msg = ButtonWarpMessages.format(msg);

        player.sendMessage(Colorizer.normColor + "Message for Warp " + Colorizer.warpColor + warp.name
                + Colorizer.normColor + " has been set to " + Colorizer.warpColor + warp.msg);
        warp.save();
    }
}
