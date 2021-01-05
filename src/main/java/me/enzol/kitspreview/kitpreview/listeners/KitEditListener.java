package me.enzol.kitspreview.kitpreview.listeners;

import me.enzol.kitspreview.KitsPreview;
import me.enzol.kitspreview.kitpreview.item.KitItem;
import me.enzol.kitspreview.kitpreview.KitPreview;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class KitEditListener implements Listener {

    public KitEditListener(KitsPreview pluginInstance){
        this.pluginInstance = pluginInstance;
    }

    private final KitsPreview pluginInstance;

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        if(!player.getOpenInventory().getTitle().contains("Editing ")) return;
        if(!player.hasPermission("kitpreview.edit")) return;

        String kitName = player.getOpenInventory().getTitle().replace("Editing ", "");
        KitPreview kitPreview = pluginInstance.getByName(kitName);

        if (kitPreview == null) return;

        kitPreview.getItems().clear();

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) == null) continue;
            kitPreview.getItems().add(new KitItem(inventory.getItem(slot), slot));
        }

        player.sendMessage(ChatColor.GREEN + "Kit preview inventory saved");
        pluginInstance.getServer().getScheduler().runTaskAsynchronously(pluginInstance, kitPreview::save);
    }

}