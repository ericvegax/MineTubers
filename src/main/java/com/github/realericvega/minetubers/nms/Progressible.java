package com.github.realericvega.minetubers.nms;

import net.minecraft.server.level.ServerPlayer;

/**
 * This interface is implemented when an NPC can be progressed...
 * Meaning that the NPC can go from Stage 1 (normal NPC) into a Stage 2 (NPC Boss)
 */
public interface Progressible {

    void changePhase(ServerPlayer npc);

    boolean hasChangedPhase(ServerPlayer npc);

    int getPhase(ServerPlayer npc);
}
