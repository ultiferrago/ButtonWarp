package com.codisimus.plugins.buttonwarp.utils;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Collection;
import java.util.List;

/**
 * Created by Spearhartt on 2/3/2017.
 */
public class WarpBook {
    public Collection<Warp> warps = ButtonWarp.getWarps();

    public ItemStack createBook() {
        ItemStack warpBook = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta meta = (BookMeta) warpBook.getItemMeta();

        meta.setTitle(ChatColor.LIGHT_PURPLE + "List of All Warps");
        meta.setAuthor("ButtonWarp");

        List<IChatBaseComponent> pages = null;

        try {
            pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(meta);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }

        if (pages != null) {

            TextComponent page = new TextComponent();
            int i = 1;

            for (Warp warp : warps) {
                TextComponent text = new TextComponent(ChatColor.DARK_PURPLE + "Warp: " + ChatColor.RESET + warp.name + "\n");
                text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bw warp " + warp.name));
                text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(warp.getJSONinfo()).create()));
                page.addExtra(text);
                if ((i != 0 && i % 13 == 0) || i == warps.size()) {
                    pages.add(IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(page)));
                    page = new TextComponent();
                    if (i == warps.size()) {
                        i = 0;
                    }
                    i++;
                } else {
                    i++;
                }
            }
        }

        warpBook.setItemMeta(meta);
        return warpBook;
    }

    public void giveBook(Player player) {
        player.getInventory().addItem(createBook());
    }
}
