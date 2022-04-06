package com.github.realericvega.minetubers.algo;

import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class NPCSpawnerAlgo {

    private static NPCSpawnerAlgo instance;
    private static List<ServerPlayer> npc_list;

    public void spawn() {

    }

    public static NPCSpawnerAlgo get() {
        if (instance == null)
            instance = new NPCSpawnerAlgo();

        return instance;
    }

    public static List<ServerPlayer> getNPCList() {
        return npc_list;
    }
}
