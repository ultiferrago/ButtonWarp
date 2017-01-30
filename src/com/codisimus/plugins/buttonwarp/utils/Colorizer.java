package com.codisimus.plugins.buttonwarp.utils;

/**
 * Created by Spearhartt on 1/30/2017.
 */
public class Colorizer {

    public static String aColor;
    public static String bColor;
    public static String cColor;
    public static String dColor;

    public static void formatAll() {
        format(aColor);
        format(bColor);
        format(cColor);
        format(dColor);
    }

    public static String format(String string) {
        return string.replace("&", "§").replace("<ae>", "æ")
                .replace("<AE>", "Æ").replace("<o/>", "ø")
                .replace("<O/>", "Ø").replace("<a>", "å")
                .replace("<A>", "Å");
    }
}

