package com.codisimus.plugins.buttonwarp;

/**
 * Holds messages that are displayed to users of this plugin
 *
 * @author Codisimus
 */
public class ButtonWarpMessages {
    public static String broadcast;
    public static String permission;
    public static String insufficentFunds;
    public static String sourceInsufficentFunds;
    public static String delay;
    public static String alreadyWarping;
    public static String cancel;
    public static String cannotUseWarps;
    public static String noAccess;
    public static String cannotTakeItems;
    public static String cannotTakeArmor;
    public static String worldMissing;
    public static String cannotHaveAnotherReward;
    public static String cannotUseAgain;
    public static String timeRemainingReward;
    public static String timeRemainingUse;
    public static String insufficientItems;

    /**
     * Formats all messages
     */
    public static void formatAll() {
        broadcast = format(broadcast);
        permission = format(permission);
        insufficentFunds = format(insufficentFunds);
        sourceInsufficentFunds = format(sourceInsufficentFunds);
        delay = format(delay);
        alreadyWarping = format(alreadyWarping);
        cancel = format(cancel);
        cannotUseWarps = format(cannotUseWarps);
        noAccess = format(noAccess);
        cannotTakeItems = format(cannotTakeItems);
        cannotTakeArmor = format(cannotTakeArmor);
        worldMissing = format(worldMissing);
        cannotHaveAnotherReward = format(cannotHaveAnotherReward);
        cannotUseAgain = format(cannotUseAgain);
        timeRemainingReward = format(timeRemainingReward);
        timeRemainingUse = format(timeRemainingUse);
        insufficientItems = format(insufficientItems);
    }

    /**
     * Adds various Unicode characters and colors to a string
     *
     * @param string The string being formated
     * @return The formatted String
     */
    public static String format(String string) {
        return string.replace("&", "§").replace("<ae>", "æ")
                .replace("<AE>", "Æ").replace("<o/>", "ø")
                .replace("<O/>", "Ø").replace("<a>", "å")
                .replace("<A>", "Å");
    }

    /**
     * Changes Unicode characters back
     *
     * @param string The string being unformated
     * @return The unformatted String
     */
    public static String unformat(String string) {
        return string.replace("§", "&").replace("æ", "<ae>")
                .replace("Æ", "<AE>").replace("ø", "<o/>")
                .replace("Ø", "<O/>").replace("å", "<a>")
                .replace("Å", "<A>");
    }
}
