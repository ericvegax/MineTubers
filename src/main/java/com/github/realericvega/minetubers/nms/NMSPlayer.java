package com.github.realericvega.minetubers.nms;

import com.mojang.datafixers.util.Function3;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class NMSPlayer {

    public abstract Function3<Player, Location, String, ServerPlayer> createNPC();
}
