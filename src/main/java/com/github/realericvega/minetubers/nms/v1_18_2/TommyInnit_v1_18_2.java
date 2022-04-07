package com.github.realericvega.minetubers.nms.v1_18_2;

import com.github.realericvega.minetubers.algo.NPCSpawnerAlgo;
import com.github.realericvega.minetubers.manager.NPCSpawnerManager;
import com.github.realericvega.minetubers.nms.MineTubeSkin;
import com.github.realericvega.minetubers.nms.NMSPlayer;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Function3;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TommyInnit_v1_18_2 extends NMSPlayer {

    @Override
    public Function3<Player, Location, String, ServerPlayer> createNPC() {
        return (player, location, name) -> {
            CraftPlayer craftPlayer = (CraftPlayer) player;

            MinecraftServer server = craftPlayer.getHandle().getServer();
            Validate.notNull(server, "Null Server!");

            ServerLevel level = craftPlayer.getHandle().getLevel();

            ServerPlayer npc = new ServerPlayer(server, level, new GameProfile(UUID.randomUUID(), name));

            npc.setPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());

            npc.getGameProfile().getProperties().put("textures", new Property("textures", MineTubeSkin.TOMMY_INNIT.getTEXTURE(), MineTubeSkin.TOMMY_INNIT.getSIGNATURE()));

            //Send the packets to artificially spawn this entity, only the client we are sending the packet to will know of it's existence
            ServerGamePacketListenerImpl ps = craftPlayer.getHandle().connection;

            //Player Info Packet
            //Sent by the server to update the user list (<tab> in the client).
            ps.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc));

            //Spawn Player packet
            ps.send(new ClientboundAddPlayerPacket(npc));

            NPCSpawnerManager.getNpc_list().add(npc);
            return npc;
        };
    }
}
