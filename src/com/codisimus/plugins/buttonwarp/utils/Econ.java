package com.codisimus.plugins.buttonwarp.utils;

import com.codisimus.plugins.buttonwarp.ButtonWarpMessages;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Manages payment/rewards of using Warps
 *
 * @author Codisimus
 */
public class Econ {
    public static Economy economy;

    /**
     * Charges a Player a given amount of money, which goes to a Player/Bank
     *
     * @param player The name of the Player to be charged
     * @param source The Player/Bank that will receive the money
     * @param amount The amount that will be charged
     * @return True if the transaction was successful
     */
    public static boolean charge(Player player, String source, double amount) {
        String name = player.getName();

        //Cancel if the Player cannot afford the transaction
        if (!economy.has(name, amount)) {
            player.sendMessage(ButtonWarpMessages.insufficentFunds
                    .replace("<amount>", economy.format(amount)));
            return false;
        }

        economy.withdrawPlayer(name, amount);

        //Money does not go to anyone if the source is the server
        if (source.equalsIgnoreCase("server")) {
            return true;
        }

        if (source.startsWith("bank:")) {
            //Send money to a bank account
            economy.bankDeposit(source.substring(5), amount);
        } else {
            //Send money to a Player
            economy.depositPlayer(source, amount);
        }

        return true;
    }

    public static boolean itemCharge(Player player, Material itemType, int amount, String itemName) {
        boolean hasBeenSet = false;
        int itemQuantity = 0;

        //Check if the player can afford the transaction and remove items.
        for (ItemStack item : player.getInventory().getContents()) {
            if (!(item == null)) {
                if (item.getType() == itemType && itemName == null) {
                    if (amount < 0 && (item.getAmount() >= Math.abs(amount) || item.getAmount() == 64)) {
                        if (Math.abs(amount) <= 64) {
                            ItemStack removeItem = new ItemStack(itemType);
                            if (itemName != null) {
                                ItemMeta meta = removeItem.getItemMeta();
                                meta.setDisplayName(itemName);
                                removeItem.setItemMeta(meta);
                            }
                            removeItem.setAmount(Math.abs(amount));
                            player.getInventory().removeItem(removeItem);
                            return true;
                        } else if (Math.abs(amount) > 64){
                            itemQuantity = itemQuantity + item.getAmount();
                        }
                    } else if (amount > 0){
                        ItemStack addItem = new ItemStack(itemType);
                        if (itemName != null) {
                            ItemMeta meta = addItem.getItemMeta();
                            meta.setDisplayName(itemName);
                            addItem.setItemMeta(meta);
                        }
                        addItem.setAmount(amount);
                        player.getInventory().addItem(addItem);
                        return true;
                    }
                } else if (item.getType() == itemType && item.getItemMeta().getDisplayName().equals(itemName)) {
                    if (amount < 0 && (item.getAmount() >= Math.abs(amount) || item.getAmount() == 64)) {
                        if (Math.abs(amount) <= 64) {
                            ItemStack removeItem = new ItemStack(itemType);
                            ItemMeta meta = removeItem.getItemMeta();
                            meta.setDisplayName(itemName);
                            removeItem.setItemMeta(meta);
                            removeItem.setAmount(Math.abs(amount));
                            player.getInventory().removeItem(removeItem);
                            return true;
                        } else if (Math.abs(amount) > 64){
                            itemQuantity = itemQuantity + item.getAmount();
                        }
                    } else if (amount > 0){
                        ItemStack addItem = new ItemStack(itemType);
                        ItemMeta meta = addItem.getItemMeta();
                        meta.setDisplayName(itemName);
                        addItem.setItemMeta(meta);
                        addItem.setAmount(amount);
                        player.getInventory().addItem(addItem);
                        return true;
                    }
                } else if (amount > 0) {
                    ItemStack addItem = new ItemStack(itemType);
                    if (itemName != null) {
                        ItemMeta meta = addItem.getItemMeta();
                        meta.setDisplayName(itemName);
                        addItem.setItemMeta(meta);
                    }
                    addItem.setAmount(amount);
                    player.getInventory().addItem(addItem);
                    return true;
                }
            } else if (amount > 0) {
                ItemStack addItem = new ItemStack(itemType);
                if (itemName != null) {
                    ItemMeta meta = addItem.getItemMeta();
                    meta.setDisplayName(itemName);
                    addItem.setItemMeta(meta);
                }
                addItem.setAmount(amount);
                player.getInventory().addItem(addItem);
                return true;
            }
        }

        //Check if the player can afford the transaction and remove items.
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (!(item == null)) {
                if (item.getType() == itemType) {
//                    System.out.println("[BW]: I found " + item.getAmount() + " " + item.getType().toString());
//                    System.out.println("[BW]: You need " + Math.abs(amount) + " " + item.getType().toString());
                    if (amount < 0 && (item.getAmount() >= Math.abs(amount) || item.getAmount() == 64)) {
                        if (Math.abs(amount) <= 64) {
                            ItemStack removeItem = new ItemStack(itemType);
                            removeItem.setAmount(Math.abs(amount));
                            player.getInventory().removeItem(removeItem);
                            return true;
                        } else if (Math.abs(amount) > 64){
                            itemQuantity = itemQuantity + item.getAmount();
                        }
//                        System.out.println("[BW]: Removed " + amount + " " + itemType);
                    } else if (amount > 0){
                        ItemStack addItem = new ItemStack(itemType);
                        addItem.setAmount(amount);
                        player.getInventory().addItem(addItem);
//                        System.out.println("[BW]: Added " + amount + " " + itemType);
                        return true;
                    }
                } else if (amount > 0) {
                    ItemStack addItem = new ItemStack(itemType);
                    addItem.setAmount(amount);
                    player.getInventory().addItem(addItem);
//                    System.out.println("[BW] Added " + amount + " " + itemType);
                    return true;
                }
            } else if (amount > 0) {
                ItemStack addItem = new ItemStack(itemType);
                addItem.setAmount(amount);
                player.getInventory().addItem(addItem);
//                System.out.println("[BW] Added " + amount + " " + itemType);
                return true;
            }
        }

        if (itemQuantity >= Math.abs(amount)) {
            ItemStack removeItem = new ItemStack(itemType);
            if (itemName != null) {
                ItemMeta meta = removeItem.getItemMeta();
                meta.setDisplayName(itemName);
                removeItem.setItemMeta(meta);
            }
            removeItem.setAmount(Math.abs(amount));
            player.getInventory().removeItem(removeItem);
            return true;
        }

        if (itemName != null) {
            player.sendMessage(ButtonWarpMessages.insufficientItems.replace("<amount>", ("" + Math.abs(amount))).replace("<items>", ("" + itemName)));
        } else {
            player.sendMessage(ButtonWarpMessages.insufficientItems.replace("<amount>", ("" + Math.abs(amount))).replace("<items>", ("" + itemType)));
        }
        return false;
    }

    /**
     * Gives the given Player the given amount of money from the given source
     *
     * @param player The Player being rewarded
     * @param source The Player/Bank that will give the reward
     * @param amount The amount that will be rewarded
     */
    public static void reward(Player player, String source, double amount) {
        //Charge the source if it is not the server
        if (!source.equalsIgnoreCase("server")) {
            //Check if money comes from a Player or a Bank
            if (source.startsWith("bank:")) {
                source = source.substring(5);

                //Cancel if the Bank does not have enough money
                if (economy.bankHas(source, amount).type != (ResponseType.SUCCESS)) {
                    player.sendMessage(ButtonWarpMessages.sourceInsufficentFunds
                            .replace("<amount>", economy.format(amount)));
                    return;
                }

                economy.bankWithdraw(source, amount);
            } else {
                //Cancel if the Source Player cannot afford the transaction
                if (!economy.has(source, amount)) {
                    player.sendMessage(ButtonWarpMessages.sourceInsufficentFunds
                            .replace("<amount>", economy.format(amount)));
                    return;
                }

                economy.withdrawPlayer(source, amount);
            }
        }

        //Send money to the Player
        economy.depositPlayer(player.getName(), amount);
    }

    /**
     * Formats the money amount by adding the unit
     *
     * @param amount The amount of money to be formatted
     * @return The String of the amount + currency name
     */
    public static String format(double amount) {
        return economy.format(amount).replace(".00", "");
    }
}
