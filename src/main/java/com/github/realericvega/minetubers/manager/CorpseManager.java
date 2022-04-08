package com.github.realericvega.minetubers.manager;

import com.github.realericvega.minetubers.nms.Corpse;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public final class CorpseManager {

    private final List<Corpse> corpses;

    public CorpseManager() {
        corpses = new ArrayList<>();
    }

    public List<Corpse> getCorpses() {
        return this.corpses;
    }

    public void removeNPC(Corpse corpse) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            ServerGamePacketListenerImpl ps = ((CraftPlayer) player).getHandle().connection;

            // Removes the corpse from the TabList
            ps.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, corpse.getNpc())); // Error: ClientboundPlayerInfoPacket cannot be found

            // Removes the corpse from sight
            ps.send(new ClientboundRemoveEntitiesPacket(corpse.getNpc().getId()));
        });

        for (ArmorStand armorStand : corpse.getArmorStands()) {
            armorStand.remove();
        }
    }
}
