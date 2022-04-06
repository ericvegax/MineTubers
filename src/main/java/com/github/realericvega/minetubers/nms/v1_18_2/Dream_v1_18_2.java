package com.github.realericvega.minetubers.nms.v1_18_2;

import com.github.realericvega.minetubers.nms.NMSPlayer;
import com.github.realericvega.minetubers.nms.Progressible;
import com.mojang.datafixers.util.Function3;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Dream_v1_18_2 extends NMSPlayer implements Progressible {

    @Override
    public Function3<Player, Location, String, ServerPlayer> createNPC() {
        return null;
    }

    @Override
    public void changePhase(ServerPlayer npc) {

    }

    @Override
    public boolean hasChangedPhase(ServerPlayer npc) {
        return false;
    }

    @Override
    public int getPhase(ServerPlayer npc) {
        return 0;
    }
}
