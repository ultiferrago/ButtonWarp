package com.codisimus.plugins.buttonwarp.commands;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandAccess;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandAllow;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandCmd;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandCost;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandDelete;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandDeny;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandGlobal;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandHelp;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandInfo;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandItems;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandLink;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandList;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandMake;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandMax;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandMove;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandMsg;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandReset;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandReward;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandRl;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandSource;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandTime;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandUnlink;
import com.codisimus.plugins.buttonwarp.commands.commands.CommandWarp;
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

    public static final String COMMAND_TAG = "[ButtonWarp CommandModule]";

    private static final HashMap<String, CqCommand> commandMap = new HashMap<String, CqCommand>();

    public CommandModule() {
        Bukkit.getPluginManager().registerEvents(this, ButtonWarp.plugin);
        registerCommand(new CommandAccess());
        registerCommand(new CommandAllow());
        registerCommand(new CommandCmd());
        registerCommand(new CommandCost());
        registerCommand(new CommandDelete());
        registerCommand(new CommandDeny());
        registerCommand(new CommandGlobal());
        registerCommand(new CommandHelp());
        registerCommand(new CommandInfo());
        registerCommand(new CommandItems());
        registerCommand(new CommandLink());
        registerCommand(new CommandList());
        registerCommand(new CommandMake());
        registerCommand(new CommandMax());
        registerCommand(new CommandMove());
        registerCommand(new CommandMsg());
        registerCommand(new CommandReset());
        registerCommand(new CommandReward());
        registerCommand(new CommandRl());
        registerCommand(new CommandSource());
        registerCommand(new CommandTime());
        registerCommand(new CommandUnlink());
        registerCommand(new CommandWarp());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length < 1) {
            //Later return the help menu for the command
            displayHelp(commandSender);
            return false;
        }

        if (commandMap.containsKey(args[0])) {
            CqLogger.debug(ButtonWarp.plugin, "Found executed command - " + label + " - Relaying command");
            ArrayList<String> list = new ArrayList();
            Collections.addAll(list, args);
            list.remove(0);
            return commandMap.get(args[0]).execute(commandSender, list);
        }

        return false;
    }

    public void registerCommand(CqCommand command) {
        if (command.getCommandLabel() != null) {
            CqLogger.debug(ButtonWarp.plugin, "Registered command - " + command.getCommandLabel());
            commandMap.put(command.getCommandLabel(), command);
        } else {
            CqLogger.debug(ButtonWarp.plugin, "You have an unset command label on - " + command.getClass().getName());
        }
    }

    public void displayHelp(CommandSender sender) {
        //Display the commands available to the specific command sender
    }
}