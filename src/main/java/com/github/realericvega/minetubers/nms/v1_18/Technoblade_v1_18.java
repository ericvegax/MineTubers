package com.github.realericvega.minetubers.nms.v1_18;

import com.github.realericvega.minetubers.algo.NPCSpawnerAlgo;
import com.github.realericvega.minetubers.nms.NMSPlayer;
import com.github.realericvega.minetubers.nms.Phase;
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

public class Technoblade_v1_18 extends NMSPlayer implements Phase {

    private ServerPlayer _npc;
    private final NamespacedKey PHASE_KEY;

    public Technoblade_v1_18(JavaPlugin plugin) {
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

            String signature = "ArwoD4sGhthC32Qaq1oSwNOWPciJN54mLj+Tq0tZBUMCaw7Gnpj6W9HJhLrax6gVs8X3O5cWUrgLbAIF8uelb5jLdUpm9ZFsAFUo/MtE3oqCXBjoXw8+Wn8y8WR1UAXwv0ts+C6OSyOfLGk0tR7Jmkac6G7bUKYOAMFtCGcppdmoxvhALHPkcsPmdlE8SsHhOVDBp+SE9SBA0V5Z2YDTua34bLdCh4jHibb9x6D8yLxos5ksqcUzsLW9HZ6gqt29GqRD3+M2q1VyXyOjQCR1MD/5A0WfFAFBtExWPRn4V8Fl8a6+814a84H6apaoIN0e6rZHC9ArLEbfSStS54YbjFZ5jfUHx4jkyg0n16B14Z7KLVRmWJjUPtICWaW7zlOOzzq+ZkV1fckVmXEA0Ri349DnWMSGU44nkgPsjD5PL9PLdDqhWqXQGL9f3C+XmUC+5WWdE1cA2W+ZrTN0mZajlkmcwYL0priAZZfzubhVV6PqWAaM9phgaoK7s5oQc6ruaXObauGZvxZ2p+LDx8A+AKnpxSPvjE+fVoOZUAvzVIhwXkFo8Y7+lJi29GjNS8f+fZctPivnABnK2oHXVapvdWlOfpTg/Y8cgc+GHhsvY82f9p7tyFAjV59Ps2G3TDjNbxm7iRaNs4MBUf2e8+mQFt/MbbblCfDBMUOprV0vjks=";
            String texture = "ewogICJ0aW1lc3RhbXAiIDogMTYzMzI2Mzg5NjIyNSwKICAicHJvZmlsZUlkIiA6ICIwNjlhNzlmNDQ0ZTk0NzI2YTViZWZjYTkwZTM4YWFmNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJOb3RjaCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yOTIwMDlhNDkyNWI1OGYwMmM3N2RhZGMzZWNlZjA3ZWE0Yzc0NzJmNjRlMGZkYzMyY2U1NTIyNDg5MzYyNjgwIgogICAgfQogIH0KfQ==";
            npc.getGameProfile().getProperties().put("textures", new Property("textures", texture, signature));

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

            NPCSpawnerAlgo.getNPCList().add(npc);
            this._npc = npc;
            return npc;
        };
    }

    @Override
    public void changePhase(ServerPlayer npc) {
        PersistentDataContainer pd = Objects.requireNonNull(npc.getBukkitEntity().getPlayer()).getPersistentDataContainer();

        try {
            if (pd.get(PHASE_KEY, PersistentDataType.INTEGER) != null) {

            } else {

            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean hasChangedPhase(ServerPlayer npc) {
        return false;
    }

    @Override
    public int getPhase(ServerPlayer npc) {
        return (Objects.requireNonNull(npc.getBukkitEntity().getPlayer()).getPersistentDataContainer().get(PHASE_KEY, PersistentDataType.INTEGER) != null
                ? npc.getBukkitEntity().getPlayer().getPersistentDataContainer().get(PHASE_KEY, PersistentDataType.INTEGER) : -1);
    }
}
