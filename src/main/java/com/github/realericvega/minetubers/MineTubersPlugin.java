package com.github.realericvega.minetubers;

import com.github.realericvega.minetubers.algo.NPCSpawnerAlgo;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineTubersPlugin extends JavaPlugin {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        NPCSpawnerAlgo.get().spawn();
    }

    @Override
    public void onDisable() {

    }
}
