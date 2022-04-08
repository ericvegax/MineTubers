package com.github.realericvega.minetubers.nms.v1_18_2;

import com.github.realericvega.minetubers.nms.NPC;
import com.mojang.datafixers.util.Function3;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Hypixel_v1_18_2 implements NPC {

    @Override
    public Function3<Player, Location, String, ServerPlayer> createNPC() {
        return null;
    }
}
