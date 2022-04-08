package com.github.realericvega.minetubers.nms.util;

import com.github.realericvega.minetubers.MineTubersPlugin;
import com.github.realericvega.minetubers.nms.Corpse;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class CorpseEntity {

    /**
     *
     * @param player The player to create a corpse of
     */
    public static Corpse createCorpse(Player player) {
        Corpse body = new Corpse();

        body.setWhoDied(player.getUniqueId());
        body.setItems(Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).toArray(ItemStack[]::new));
        body.setWhenDied(System.currentTimeMillis());

        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();

        Property textures = (Property) serverPlayer.getBukkitEntity().getHandle().getGameProfile().getProperties().get("textures").toArray()[0];
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), player.getName());

        // Gets the targets Texture & Signature
        gameProfile.getProperties().put("textures",
                new Property("textures", textures.getValue(), textures.getSignature()));

        ServerPlayer corpse = new ServerPlayer(
                ((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) player.getWorld()).getHandle(),
                gameProfile);

        corpse.setPos(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        // 1.16 and below, you need to spawn a bed, 1.17+ you don't need to, but it's best to do it anyway
        Location bed = player.getLocation().add(1, 0, 0);
        corpse.startSleepInBed(new BlockPos(bed.getX(), bed.getY(), bed.getZ()));

        // Hide Name
        PlayerTeam team = new PlayerTeam(new Scoreboard(), player.getName());

        team.setNameTagVisibility(Team.Visibility.NEVER);

        ClientboundSetPlayerTeamPacket teamPacket = ClientboundSetPlayerTeamPacket.createRemovePacket(team);

        ClientboundSetPlayerTeamPacket teamPacket2 = ClientboundSetPlayerTeamPacket
                .createAddOrModifyPacket(team, true);

        ClientboundSetPlayerTeamPacket teamPacket3 = ClientboundSetPlayerTeamPacket
                .createPlayerPacket(team, corpse.getName().toString(), ClientboundSetPlayerTeamPacket.Action.ADD);

        // Set the pose/overlays
        Random random = new Random();
        int r = random.nextInt(2);

        if (r == 1) {
            corpse.setPose(Pose.SWIMMING);
        } else {
            corpse.setPose(Pose.SLEEPING);
        }

        // Change the Fall Position
        ClientboundMoveEntityPacket moveEntityPacket = new ClientboundMoveEntityPacket
                .Pos(corpse.getId(), (short) 0, (short) ((player.getLocation().getY() - 1.7 - player.getLocation().getY()) * 32),
                (short) 0, false);

        // Send Packets to all players
        Bukkit.getOnlinePlayers().forEach(pl -> {
            ServerGamePacketListenerImpl ps = ((CraftPlayer) pl).getHandle().connection;

            ps.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, corpse));

            ps.send(teamPacket);
            ps.send(teamPacket2);
            ps.send(teamPacket3);

            ps.send(moveEntityPacket);

            // Hide NameTag from TabList
            new BukkitRunnable() {
                public void run() {
                    ps.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, corpse));
                }
            }.runTaskAsynchronously(MineTubersPlugin.getPlugin(MineTubersPlugin.class));
        });

        body.setNpc(serverPlayer);

        body.getArmorStands().add(player.getWorld().spawn(player.getLocation(), ArmorStand.class, armorstand1 -> {
            armorstand1 = (ArmorStand) player.getWorld().spawnEntity(serverPlayer.getBukkitEntity().getLocation(), EntityType.ARMOR_STAND);

            armorstand1.setSmall(true);
            armorstand1.setInvisible(true);
            armorstand1.setInvisible(true);
        }));

        body.getArmorStands().add(player.getWorld().spawn(player.getLocation(), ArmorStand.class, armorstand2 -> {
            armorstand2 = (ArmorStand) player.getWorld().spawnEntity(serverPlayer.getBukkitEntity().getLocation().subtract(1, 0, 0), EntityType.ARMOR_STAND);

            armorstand2.setSmall(true);
            armorstand2.setInvisible(true);
            armorstand2.setInvisible(true);
        }));

        body.getArmorStands().add(player.getWorld().spawn(player.getLocation(), ArmorStand.class, armorstand3 -> {
            armorstand3 = (ArmorStand) player.getWorld().spawnEntity(serverPlayer.getBukkitEntity().getLocation().subtract(2, 0, 0), EntityType.ARMOR_STAND);

            armorstand3.setSmall(true);
            armorstand3.setInvisible(true);
            armorstand3.setInvisible(true);
        }));

        return body;
    }
}
