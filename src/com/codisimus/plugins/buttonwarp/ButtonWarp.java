package com.codisimus.plugins.buttonwarp;

import com.codisimus.plugins.buttonwarp.configs.ButtonWarpConfig;
import com.codisimus.plugins.buttonwarp.listeners.ButtonWarpDelayListener;
import com.codisimus.plugins.buttonwarp.listeners.ButtonWarpListener;
import com.codisimus.plugins.buttonwarp.listeners.ButtonWarpVehicleListener;
import com.codisimus.plugins.buttonwarp.utils.Econ;
import com.codisimus.plugins.buttonwarp.commands.CommandModule;
import com.conquestiamc.CqAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Loads Plugin and manages Data/Permissions
 *
 * @author Codisimus
 */
public class ButtonWarp extends JavaPlugin implements CommandExecutor {
    public static Server server;
    public static Logger logger;
    public static PluginManager pm;
    public static Plugin plugin;
    public static int defaultDays;
    public static int defaultHours;
    public static int defaultMinutes;
    public static int defaultSeconds;
    public static boolean defaultTakeItems;
    public static boolean defaultRestricted;
    public static int defaultMax;
    public static String dataFolder;
    private static CommandModule commandHandler;
    private static TreeMap<String, Warp> warps = new TreeMap<String, Warp>();
    public static ArrayList<String> opPlayers = new ArrayList<>();

    /**
     * Loads this Plugin by doing the following:
     * Loads the settings from the Config file
     * Finds the Permission and Economy Plugins to use
     * Loads the saved ButtonWarp data
     * Registers the Events to listen for
     */
    @Override
    public void onEnable () {
        //Metrics hook
        //try { new Metrics(this).start(); } catch (IOException e) {}

        server = getServer();
        logger = getLogger();
        pm = server.getPluginManager();
        plugin = this;

        File dir = this.getDataFolder();
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        dataFolder = dir.getPath();

        dir = new File(dataFolder+"/Warps");
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        ButtonWarpConfig.load();

        //Find Economy
        RegisteredServiceProvider<Economy> economyProvider =
                getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            Econ.economy = economyProvider.getProvider();
        }

        //Load Warps Data
        loadData();

        //Register Events
        pm.registerEvents(new ButtonWarpListener(), this);
        pm.registerEvents(new ButtonWarpVehicleListener(), this);
        if (ButtonWarpListener.delay > 0) {
            pm.registerEvents(new ButtonWarpDelayListener(), this);
        }

        //Register commands
        commandHandler = new CommandModule();

        //Register the command found in the plugin.yml
        //ButtonWarpCommand.command = (String)this.getDescription().getCommands().keySet().toArray()[0];
        //getCommand(ButtonWarpCommand.command).setExecutor(new ButtonWarpCommand());

        Properties version = new Properties();
        try {
            version.load(this.getResource("version.properties"));
        } catch (Exception ex) {
            logger.warning("version.properties file not found within jar");
        }


        logger.info("ButtonWarp "+this.getDescription().getVersion()+" (Build "+version.getProperty("Build")+") is enabled!");
    }

    @Override
    public void onDisable() {
        saveAll();

        for (String string : opPlayers) {
            server.getPlayer(string).setOp(false);
        }
    }

    /**
     * Returns boolean value of whether the given player has the specific permission
     *
     * @param player The Player who is being checked for permission
     * @param node The String of the permission, ex. admin
     * @return true if the given player has the specific permission
     */
    public static boolean hasPermission(Player player, String node) {
        return player.hasPermission("buttonwarp." + node);
    }

    /**
     * Loads Warps from the save file
     * Saving is turned off if an error occurs
     */
    public static void loadData() {
        File[] files = plugin.getDataFolder().listFiles();

        //Organize files
        if (files != null) {
            for (File file: files) {
                String name = file.getName();
                if (name.endsWith(".dat")) {
                    File dest = new File(dataFolder+"/Warps/"+name.substring(0, name.length() - 4)+".properties");
                    file.renameTo(dest);
                }
            }
        }

        files = new File(dataFolder+"/Warps/").listFiles();

        FileInputStream fis = null;
        for (File file: files) {
            String name = file.getName();
            if (name.endsWith(".properties")) {
                try {
                    //Load the Properties file for reading
                    Properties p = new Properties();
                    fis = new FileInputStream(file);
                    p.load(fis);

                    //Construct a new Warp using the file name and values of message, amount, and source
                    Warp warp = new Warp(name.substring(0, name.length() - 11), ButtonWarpMessages.format(p.getProperty("Message")),
                            Double.parseDouble(p.getProperty("Amount")), p.getProperty("Source"), Material.getMaterial(p.getProperty("ItemType")), Integer.parseInt(p.getProperty("ItemAmount")), p.getProperty("ItemName"));

                    if (p.containsKey("Location")) {
                        //Set the Location data
                        String[] location = p.getProperty("Location").split("'");
                        warp.world = location[0];
                        warp.x = Double.parseDouble(location[1]);
                        warp.y = Double.parseDouble(location[2]);
                        warp.z = Double.parseDouble(location[3]);
                        warp.pitch = Float.parseFloat(location[4]);
                        warp.yaw = Float.parseFloat(location[5]);
                        if (p.containsKey("IgnorePitch")) {
                            warp.ignorePitch = Boolean.parseBoolean(p.getProperty("IgnorePitch"));
                        }
                        if (p.containsKey("IgnoreYaw")) {
                            warp.ignoreYaw = Boolean.parseBoolean(p.getProperty("IgnoreYaw"));
                        }
                    }

                    if (p.containsKey("Commands")) {
                        String command = p.getProperty("Commands");
                        if (!command.equals("none")) {
                            warp.commands.addAll(Arrays.asList(command.split(", ")));
                        }
                    }

                    //Set the reset time
                    String[] resetTime = p.getProperty("ResetTime").split("'");
                    warp.days = Integer.parseInt(resetTime[0]);
                    warp.hours = Integer.parseInt(resetTime[1]);
                    warp.minutes = Integer.parseInt(resetTime[2]);
                    warp.seconds = Integer.parseInt(resetTime[3]);

                    //Set the reset type
                    warp.global = p.getProperty("ResetType").equals("global");

                    //Set whether the Warp is restricted
                    if (p.containsKey("Restricted")) {
                        warp.restricted = Boolean.parseBoolean(p.getProperty("Restricted"));
                    }

                    //Load the data of all the Buttons
                    if (p.containsKey("Buttons")) {
                        warp.setButtons(p.getProperty("Buttons"));
                    } else {
                        warp.setButtonsOld(p.getProperty("ButtonsData"));
                    }

                    warps.put(warp.name, warp);

                    file = new File(dataFolder + "/Warps/"
                                    + warp.name + ".warptimes");
                    if (file.exists()) {
                        fis = new FileInputStream(file);
                        warp.activationTimes.load(fis);
                    } else {
                        warp.save();
                    }
                } catch (Exception loadFailed) {
                    logger.severe("Failed to load " + name);
                    loadFailed.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    /**
     * Invokes save() method for each Warp
     */
    public static void saveAll() {
        for (Warp warp: warps.values()) {
            saveWarp(warp);
        }
    }

    /**
     * Writes the given Warp to its save file
     * If the file already exists, it is overwritten
     *
     * @param warp The given Warp
     */
    static void saveWarp(Warp warp) {
        FileOutputStream fos = null;
        try {
            Properties p = new Properties();

            if (warp.commands.isEmpty()) {
                p.setProperty("Commands", "none");
            } else {
                String command = warp.commands.toString();
                p.setProperty("Commands", command.substring(1, command.length() - 1));
            }

            if (warp.itemName != null) {
                p.setProperty("ItemName", warp.itemName);
            }
            p.setProperty("ItemType", warp.itemType.toString());
            p.setProperty("ItemAmount", String.valueOf(warp.itemAmount));

            p.setProperty("Message", ButtonWarpMessages.unformat(warp.msg));
            p.setProperty("Amount", String.valueOf(warp.amount));
            p.setProperty("Source", warp.source);
            if (warp.world != null) {
                p.setProperty("Location", warp.world+"'"+warp.x+"'"+warp.y+"'"+warp.z+"'"+warp.pitch+"'"+warp.yaw);
                p.setProperty("IgnorePitch", String.valueOf(warp.ignorePitch));
                p.setProperty("IgnoreYaw", String.valueOf(warp.ignoreYaw));
            }
            p.setProperty("ResetTime", warp.days+"'"+warp.hours+"'"+warp.minutes+"'"+warp.seconds);

            p.setProperty("ResetType", warp.global ? "global" : "player");

            p.setProperty("Restricted", String.valueOf(warp.restricted));

            String value = "";
            for (Button button: warp.buttons) {
                value += "; " + button.toString();
            }
            if (!value.isEmpty()) {
                value = value.substring(2);
            }
            p.setProperty("Buttons", value);

            //Write the Warp Properties to file
            fos = new FileOutputStream(dataFolder + "/Warps/" + warp.name + ".properties");
            p.store(fos, null);
            fos.close();

            //Write the Warp activation times to file
            fos = new FileOutputStream(dataFolder + "/Warps/" + warp.name + ".warptimes");
            warp.activationTimes.store(fos, null);
        } catch (Exception saveFailed) {
            logger.severe("Save Failed!");
            saveFailed.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Returns the Collection of all Warps
     *
     * @return The Collection of all Warps
     */
    public static Collection<Warp> getWarps() {
        return warps.values();
    }

    /**
     * Adds the given Warp to the collection of Warps
     *
     * @param warp The given Warp
     */
    public static void addWarp(Warp warp) {
        warps.put(warp.name, warp);
        warp.save();
    }

    /**
     * Removes the given Warp from the collection of Warps
     *
     * @param warp The given Warp
     */
    public static void removeWarp(Warp warp) {
        warps.remove(warp.name);
        File trash = new File(dataFolder+"/Warps/"+warp.name+".properties");
        trash.delete();
        trash = new File(dataFolder+"/Warps/"+warp.name+".warptimes");
        trash.delete();
    }

    /**
     * Reloads ButtonWarp data
     */
    public static void rl() {
        rl(null);
    }

    /**
     * Reloads ButtonWarp data
     *
     * @param player The Player reloading the data
     */
    public static void rl(Player player) {
        warps.clear();
        loadData();

        logger.info("reloaded");
        if (player != null) {
            player.sendMessage("§5ButtonWarp reloaded");
        }
    }

    /**
     * Returns the Warp with the given name
     *
     * @param name The name of the Warp you wish to find
     * @return The Warp with the given name or null if not found
     */
    public static Warp findWarp(String name) {
        return warps.get(name);
    }

    /**
     * Returns the Warp that contains the given Block
     *
     * @param block The Block that is part of the Warp
     * @return The Warp that contains the given Block or null if not found
     */
    public static Warp findWarp(Block block) {
        //Iterate through all Warps to find the one with the given Block
        for (Warp warp: warps.values()) {
            if (warp.findButton(block) != null) {
                return warp;
            }
        }

        //Return null because the Warp does not exist
        return null;
    }

    /*
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equals("setblock")) {
            //Check length here to ensure match
            try {
                World world = Bukkit.getWorld(args[0]);

                int x = Integer.parseInt(args[1]);
                int y = Integer.parseInt(args[2]);
                int z = Integer.parseInt(args[3]);

                Material mat = Material.valueOf(args[4]);

                Location loc = new Location(world, x, y, z);
                loc.getBlock().setType(mat);
            } catch (NumberFormatException e) {
                logger.warning("Invalid coordinates for button.");
            }
        }

        return false;
    }
    */

}
