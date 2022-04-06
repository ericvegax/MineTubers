package com.github.realericvega.minetubers.nms.task;

import com.github.realericvega.minetubers.MineTubersPlugin;
import com.github.realericvega.minetubers.manager.CorpseManager;
import com.github.realericvega.minetubers.nms.Corpse;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Removes corpses after a certain amount of time
 */
public class CorpseRemoval extends BukkitRunnable {

    private final MineTubersPlugin PLUGIN;
    private final CorpseManager MANAGER;

    public CorpseRemoval(MineTubersPlugin plugin, CorpseManager manager) {
        this.PLUGIN = plugin;
        this.MANAGER = manager;
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis();

        for (Iterator<Corpse> iterator = MANAGER.getCorpses().iterator(); iterator.hasNext(); ) {
            Corpse corpse = iterator.next();

            if (now - corpse.getWhenDied() >= 10000) { // Checks If the corpse has existed for more than the specified time
                iterator.remove();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location location = corpse.getNpc().getBukkitEntity().getLocation().clone();

                        Bukkit.getOnlinePlayers().forEach(player -> {
                            ServerGamePacketListenerImpl ps = ((CraftPlayer) player).getHandle().connection;
                            corpse.getNpc().setPos(location.getX(), location.getY() - 0.1, location.getZ());
                            ps.send(new ClientboundTeleportEntityPacket(corpse.getNpc()));
                        });

                        if (!location.add(0, 1, 0).getBlock().isPassable()) {
                            MANAGER.removeNPC(corpse);
                            this.cancel();
                        }
                    }
                }.runTaskTimerAsynchronously(PLUGIN, 0L, 5L);

                Player whoDied = Bukkit.getServer().getPlayer(corpse.getWhoDied());

                if (whoDied != null) {
                    Inventory inv = whoDied.getInventory();
                    Arrays.stream(corpse.getItems()).forEach(inv::addItem);
                    for (ItemStack item : inv) {
                        if (item != null) {
                            whoDied.getWorld().dropItem(whoDied.getLocation(), item);
                        }
                    }


                    whoDied.getNearbyEntities(5, 5, 5).forEach(entity -> {
                        if (entity instanceof Player)
                            ((Player) entity).playSound(entity.getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 1, 1);
                    });
                }
            }
        }
    }
}
