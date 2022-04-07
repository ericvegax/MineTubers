package com.github.realericvega.minetubers.listener;

import com.github.realericvega.minetubers.MineTubersPlugin;
import com.github.realericvega.minetubers.algo.NPCSpawnerAlgo;
import com.github.realericvega.minetubers.manager.NPCSpawnerManager;
import com.github.realericvega.minetubers.nms.Corpse;
import com.github.realericvega.minetubers.nms.util.CorpseEntity;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class DamageListener implements Listener {

    private final MineTubersPlugin PLUGIN;

    public DamageListener(MineTubersPlugin plugin) {
        this.PLUGIN = plugin;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            if (NPCSpawnerManager.getNpc_list().contains(((CraftPlayer) event.getEntity()).getHandle())) {
                PLUGIN.getCorpseManager().getCorpses().add(CorpseEntity.createCorpse((Player) event.getEntity()));
            }
        }
        event.getDrops().clear();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getFinalDamage() >= ((Player) event.getEntity()).getHealth()) {
                // Check if the player wants corpses to be created on "Damage" instead of on "Death"
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Arrow) {
                /*
                 * Play a sound each time a player hits an entity with an arrow
                 * in order to indicate that the Arrow successfully HIT it's target
                 */
            }
        }
    }

    private void dropItems(Player whoClicked, Corpse corpse) {

    }
}
