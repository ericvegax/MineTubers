package com.github.realericvega.minetubers.manager;

import com.github.realericvega.minetubers.nms.MineTuber;
import lombok.Getter;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NPCSpawnerManager {

    @Getter
    private static List<ServerPlayer> npc_list;

    @Getter
    private static final Map<Player, MineTuber> MINETUBER_MAP = new HashMap<>();
}
