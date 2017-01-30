package com.codisimus.plugins.buttonwarp.utils;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class Colorizer {

    public static String normColor;
    public static String warpColor;
    public static String badColor;
    public static String econColor;
    public static String infoColor;
    public static String descColor;

    public static void formatAll() {
        format(normColor);
        format(warpColor);
        format(badColor);
        format(econColor);
        format(infoColor);
        format(descColor);
    }

    public static String format(String string) {
        return string.replace("&", "§").replace("<ae>", "æ")
                .replace("<AE>", "Æ").replace("<o/>", "ø")
                .replace("<O/>", "Ø").replace("<a>", "å")
                .replace("<A>", "Å");
    }
}

