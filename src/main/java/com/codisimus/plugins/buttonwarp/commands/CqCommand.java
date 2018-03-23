package com.codisimus.plugins.buttonwarp.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * Created by Ferrago on 2/18/16.
 */
public interface  CqCommand {
    public boolean execute(CommandSender sender, ArrayList<String> args);
    public String getCommandLabel();
}