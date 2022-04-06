package com.github.realericvega.minetubers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MineTubersPlugin extends JavaPlugin {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().log(Level.INFO, "MineTubers has been initialized!");
    }

    @Override
    public void onDisable() {

    }
}
