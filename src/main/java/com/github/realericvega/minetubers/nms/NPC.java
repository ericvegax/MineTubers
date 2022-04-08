package com.github.realericvega.minetubers.nms;

import com.mojang.datafixers.util.Function3;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface NPC {

    Function3<Player, Location, String, ServerPlayer> createNPC();
}
