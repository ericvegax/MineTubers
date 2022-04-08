package com.github.realericvega.minetubers;

import com.github.realericvega.minetubers.manager.CommandManager;
import com.github.realericvega.minetubers.manager.CorpseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MineTubersPlugin extends JavaPlugin {

    private CorpseManager corpseManager;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        corpseManager = new CorpseManager();

        Bukkit.getLogger().log(Level.INFO, "MineTubers has been initialized!");
    }

    @Override
    public void onDisable() {

    }

    public CorpseManager getCorpseManager() {
        return corpseManager;
    }
}
