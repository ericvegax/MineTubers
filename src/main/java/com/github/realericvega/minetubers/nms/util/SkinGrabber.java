package com.github.realericvega.minetubers.nms.util;

import com.github.realericvega.minetubers.nms.MineTubeSkin;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.server.network.ServerPlayerConnection;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SkinGrabber {

    /**
     *
     * @param player The target player
     * @param skin The skin to change the player into
     */
    public static void changeSkins(Player player, MineTubeSkin skin) {
        GameProfile profile = ((CraftPlayer) player).getHandle().getGameProfile();
        ServerPlayerConnection connection = ((CraftPlayer) player).getHandle().connection;

        connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER));

        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", skin.getSkin());

        connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER));
    }
}
