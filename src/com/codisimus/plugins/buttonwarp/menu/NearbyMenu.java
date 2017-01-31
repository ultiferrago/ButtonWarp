package com.codisimus.plugins.buttonwarp.menu;

import com.codisimus.plugins.buttonwarp.ButtonWarp;
import com.codisimus.plugins.buttonwarp.Warp;
import com.conquestiamc.GUI.API.BackButton;
import com.conquestiamc.GUI.API.Button;
import com.conquestiamc.GUI.API.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class NearbyMenu extends Menu {
    private HashMap<Double, ArrayList<Warp>> warpMap = new HashMap<>();

    public NearbyMenu(Player player) {
        NearbyMenu thisMenu = this;
        this.name = ChatColor.YELLOW + "Nearby Warps";

        for (Warp warp : ButtonWarp.getWarps()) {
            //CLEAN FOR NULL WORLDS
            Location warpLoc = new Location(Bukkit.getServer().getWorld(warp.world), warp.x, warp.y, warp.z);
            double locDelta = player.getLocation().distance(warpLoc);
            put(locDelta, warp);
        }

        Map<Double, ArrayList<Warp>> map = new TreeMap<Double, ArrayList<Warp>>(warpMap);
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        int i = 0;
        while(iterator.hasNext() && i <= 52) {
            Map.Entry me = (Map.Entry)iterator.next();
            for (Warp warp : (ArrayList<Warp>) me.getValue()) {
                Button warpButton = new Button();
                warpButton.setIcon(Material.COMPASS);
                warpButton.setName(ChatColor.YELLOW + "Warp: " + ChatColor.LIGHT_PURPLE + warp.name);
                menuMap.put(i++, warpButton);
            }
        }

        BackButton backButton = new BackButton(new MainMenu(player));
        if (i < 17) {
            menuMap.put(17, backButton);
        } else if (i < 26) {
            menuMap.put(26, backButton);
        } else if (i < 35) {
            menuMap.put(35, backButton);
        } else if (i < 44) {
            menuMap.put(44, backButton);
        } else if (i < 53) {
            menuMap.put(53, backButton);
        }

    }

    public void put(Double d, Warp w) {
        if (warpMap.containsKey(d)) {
            warpMap.get(d).add(w);
        } else {
            ArrayList<Warp> warpSubMap = new ArrayList<>();
            warpSubMap.add(w);
            warpMap.put(d, warpSubMap);
        }
    }
}
