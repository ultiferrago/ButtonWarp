package com.codisimus.plugins.buttonwarp.commands;

import com.codisimus.plugins.buttonwarp.commands.commands.CommandMake;
import com.conquestiamc.CqAPI;
import com.conquestiamc.logging.CqLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author UltiFerrago
 */
public class CommandModule implements CommandExecutor, Listener {

    public static final String COMMAND_TAG = "[CommandModule]";

    private static final HashMap<String, CqCommand> commandMap = new HashMap<String, CqCommand>();

    public CommandModule() {
        Bukkit.getPluginManager().registerEvents(this, CqAPI.CqAPI);
        CqAPI.getInstance().getCommand("bw").setExecutor(this);
        registerCommand(new CommandMake());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length < 1) {
            //Later return the help menu for the command
            displayHelp(commandSender);
            return false;
        }

        if (commandMap.containsKey(args[0])) {
            CqLogger.debug(COMMAND_TAG + "Found executed command - " + label + " - Relaying command");
            ArrayList<String> list = new ArrayList();
            Collections.addAll(list, args);
            list.remove(0);
            return commandMap.get(args[0]).execute(commandSender, list);
        }

        return false;
    }

    public void registerCommand(CqCommand command) {
        CqLogger.debug(COMMAND_TAG + "Registered command - " + command.getCommandLabel());
        commandMap.put(command.getCommandLabel(), command);
    }

    public void displayHelp(CommandSender sender) {
        //Display the commands available to the specific command sender
    }
}