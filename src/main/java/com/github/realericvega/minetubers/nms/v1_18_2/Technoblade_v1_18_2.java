package com.github.realericvega.minetubers.nms.v1_18_2;

import com.github.realericvega.minetubers.algo.NPCSpawnerAlgo;
import com.github.realericvega.minetubers.manager.NPCSpawnerManager;
import com.github.realericvega.minetubers.nms.MineTubeSkin;
import com.github.realericvega.minetubers.nms.NMSPlayer;
import com.github.realericvega.minetubers.nms.Progressible;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EquipmentSlot;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Technoblade_v1_18_2 extends NMSPlayer implements Progressible {

    private ServerPlayer _npc;
    private boolean phaseChanged = false;

    private final NamespacedKey PHASE_KEY;

    public Technoblade_v1_18_2(JavaPlugin plugin) {
        PHASE_KEY = new NamespacedKey(plugin, "phase");
    }

    @Override
    public Function3<Player, Location, String, ServerPlayer> createNPC() {
        return (player, location, name) -> {
            CraftPlayer craftPlayer = (CraftPlayer) player;

            MinecraftServer server = craftPlayer.getHandle().getServer();
            Validate.notNull(server, "Null Server!");

            ServerLevel level = craftPlayer.getHandle().getLevel();

            ServerPlayer npc = new ServerPlayer(server, level, new GameProfile(UUID.randomUUID(), name));

            npc.setPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());

            npc.getGameProfile().getProperties().put("textures", new Property("textures", MineTubeSkin.TECHNOBLADE.getTEXTURE(), MineTubeSkin.TECHNOBLADE.getSIGNATURE()));

            //Send the packets to artificially spawn this entity, only the client we are sending the packet to will know of it's existence
            ServerGamePacketListenerImpl ps = craftPlayer.getHandle().connection;

            //Player Info Packet
            //Sent by the server to update the user list (<tab> in the client).
            ps.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc));

            //Spawn Player packet
            ps.send(new ClientboundAddPlayerPacket(npc));

            //Give the player items
            ItemStack item = new ItemStack(Material.DIAMOND_AXE);
            ItemMeta meta = item.getItemMeta();

            assert meta != null;

            meta.setUnbreakable(true);
            meta.setLore(List.of("The Axe Of Peace", "Legends says this axe destroyed many governments..."));
            meta.addEnchant(Enchantment.FIRE_ASPECT, 2, false);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            item.setItemMeta(meta);

            ps.send(new ClientboundSetEquipmentPacket(npc.getBukkitEntity().getEntityId(), List.of(Pair.of(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(item)))));

            NPCSpawnerManager.getNpc_list().add(npc);
            this._npc = npc;
            return npc;
        };
    }

    @Override
    public void changePhase(ServerPlayer npc) {
        PersistentDataContainer pd = Objects.requireNonNull(npc.getBukkitEntity().getPlayer()).getPersistentDataContainer();

        try {
            if (pd.get(PHASE_KEY, PersistentDataType.INTEGER) != null) {
                if (pd.get(PHASE_KEY, PersistentDataType.INTEGER).intValue() == 1)
                    pd.set(PHASE_KEY, PersistentDataType.INTEGER, 2);

                 else if (pd.get(PHASE_KEY, PersistentDataType.INTEGER).intValue() == 2)
                    pd.set(PHASE_KEY, PersistentDataType.INTEGER, 1);

                 else
                     pd.set(PHASE_KEY, PersistentDataType.INTEGER, -1);

                phaseChanged = true;
            } else {
                pd.set(PHASE_KEY, PersistentDataType.INTEGER, 0);
            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean hasChangedPhase(ServerPlayer npc) {
        return this.phaseChanged;
    }

    @Override
    public int getPhase(ServerPlayer npc) {
        return (Objects.requireNonNull(npc.getBukkitEntity().getPlayer()).getPersistentDataContainer().get(PHASE_KEY, PersistentDataType.INTEGER) != null
                ? npc.getBukkitEntity().getPlayer().getPersistentDataContainer().get(PHASE_KEY, PersistentDataType.INTEGER) : -1);
    }
}
