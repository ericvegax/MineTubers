package com.github.realericvega.minetubers.listener;

import com.github.realericvega.minetubers.algo.NPCSpawnerAlgo;
import com.github.realericvega.minetubers.nms.util.CorpseEntity;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getFinalDamage() >= ((Player) event.getEntity()).getHealth()) {
                if (NPCSpawnerAlgo.getNPCList().contains(((CraftPlayer) event.getEntity()).getHandle())) {
                    CorpseEntity.createCorpse((Player) event.getEntity());
                }
            }
        }
    }
}
