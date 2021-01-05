package me.enzol.kitspreview.kitpreview.item;

import org.bukkit.inventory.ItemStack;

public class KitItem {
    private final ItemStack item;
    private final int slot;

    public KitItem(ItemStack item, int slot){
        this.item = item;
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItem() {
        return item;
    }
}