package com.github.realericvega.minetubers.nms;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Corpse {

    @Getter
    @Setter
    private UUID whoDied;

    @Getter
    @Setter
    private ServerPlayer npc;

    @Getter
    @Setter
    private ItemStack[] items;

    @Getter
    @Setter
    private List<ArmorStand> armorStands;

    @Getter
    @Setter
    private long whenDied; //epoch time as long in ms

    public Corpse() {
        this.armorStands = new ArrayList<>();
    }

    public Corpse(UUID whoDied, ServerPlayer npc, ItemStack[] items, List<ArmorStand> armorStands, long whenDied) {
        this.whoDied = whoDied;
        this.npc = npc;
        this.items = items;
        this.armorStands = armorStands;
        this.whenDied = whenDied;
    }
}
