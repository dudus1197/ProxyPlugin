package pl.mcmodstudio.pp.utils;

import org.bukkit.ChatColor;

public class Utils {
    
    // Szybka metoda do kolorowania wiadomości, np. Utils.color("&aHej!")
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}