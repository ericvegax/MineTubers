package com.github.realericvega.minetubers.util;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public final class ItemBuilder {

    private static final ItemFlag[] ALL_FLAGS = {
            ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DYE,
            ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS,
            ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON,
            ItemFlag.HIDE_POTION_EFFECTS
    };

    private final ItemStack itemStack;

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = Objects.requireNonNull(itemStack, "itemStack");
    }

    public ItemBuilder transformItem(Consumer<ItemStack> itemStackConsumer) {
        itemStackConsumer.accept(this.itemStack);
        return this;
    }

    public ItemBuilder transformItemMeta(Consumer<ItemMeta> itemMetaConsumer) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            itemMetaConsumer.accept(meta);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setName(String name) {
        return this.transformItemMeta(im -> im.setDisplayName(name));
    }

    public ItemBuilder setLore(String line) {
        return this.transformItemMeta(im -> {
            List<String> lore = im.getLore() == null ? new ArrayList<>() : im.getLore();
            lore.add(Text.colorize(line));
            im.setLore(lore);
        });
    }
}
