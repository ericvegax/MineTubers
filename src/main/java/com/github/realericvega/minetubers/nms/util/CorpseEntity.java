package com.github.realericvega.minetubers.nms.util;

import com.github.realericvega.minetubers.MineTubersPlugin;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R2.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class CorpseEntity {

    public static void createCorpse(Player player) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();

        Property textures = (Property) serverPlayer.getBukkitEntity().getHandle().getGameProfile().getProperties().get("textures").toArray()[0];
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), player.getName());

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
        Scoreboard scoreboard = ((CraftScoreboard) Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard()).getHandle();
        PlayerTeam team = new PlayerTeam(scoreboard, player.getName());

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

        // Send Packets
        for (Player pl : Bukkit.getOnlinePlayers()) {
            ServerPlayerConnection connection = ((CraftPlayer) pl).getHandle().connection;

            connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, corpse));

            connection.send(teamPacket);
            connection.send(teamPacket2);
            connection.send(teamPacket3);

            connection.send(moveEntityPacket);

            // Hide NameTag from TabList
            new BukkitRunnable() {
                public void run() {
                    connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, corpse));
                }
            }.runTaskAsynchronously(MineTubersPlugin.getPlugin(MineTubersPlugin.class));
        }
    }
}
