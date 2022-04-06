package com.github.realericvega.minetubers.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Text {

    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void sendTranslated(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
