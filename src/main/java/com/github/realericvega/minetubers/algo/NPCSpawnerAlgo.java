package com.github.realericvega.minetubers.algo;

import com.github.realericvega.minetubers.MineTubersPlugin;
import com.github.realericvega.minetubers.nms.MineTuber;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class NPCSpawnerAlgo {

    private final Map<Player, MineTuber> MINETUBER_MAP = new HashMap<>();

    private static NPCSpawnerAlgo instance;
    private static List<ServerPlayer> npc_list;

    public static NPCSpawnerAlgo get() {
        if (instance == null)
            instance = new NPCSpawnerAlgo();

        return instance;
    }

    public void spawn(int radius, int npcCap, int spawnTime) {
        MineTuber[] mineTuber = MineTuber.values();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    for (MineTuber tuber : mineTuber) {
                        tuber.getNMSPlayer().createNPC().apply(player, player.getLocation(), tuber.getNAME());
                    }
                }
            }
        }.runTaskTimer(MineTubersPlugin.getPlugin(MineTubersPlugin.class), 0L, spawnTime);
    }

    private boolean canSpawn(Location location) {
        Block feetBlock = location.getBlock();
        Block headBlock = location.clone().add(0.0, 1.0, 0.0).getBlock();
        Block upperBlock = location.clone().add(0.0, 2.0, 0.0).getBlock();

        List<Material> blocksToNotSpawnOn = new ArrayList<>();

        blocksToNotSpawnOn.add(Material.BARRIER);
        blocksToNotSpawnOn.add(Material.FIRE);
        blocksToNotSpawnOn.add(Material.COBWEB);
        blocksToNotSpawnOn.add(Material.TALL_GRASS);
        blocksToNotSpawnOn.add(Material.VINE);
        blocksToNotSpawnOn.add(Material.AIR);

        for (Material mat : blocksToNotSpawnOn) {
            if (feetBlock.getType() == mat) return false;
            if (headBlock.getType() == mat) return false;
            if (upperBlock.getType() == mat) return false;
        }

        return (feetBlock.isPassable() && !feetBlock.isLiquid() && headBlock.isPassable()
                && !headBlock.isLiquid() && upperBlock.isPassable() && !upperBlock.isLiquid());
    }

    private int getRandomNum(int radiusSize) {
        int random = (int) (Math.random() * (radiusSize + 1));
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

    private double getRandomBlockCorner() {
        double random = Math.random();
        if (random >= 0.5) random *= -1.0;
        return random;
    }

    public static List<ServerPlayer> getNPCList() {
        return npc_list;
    }
}
