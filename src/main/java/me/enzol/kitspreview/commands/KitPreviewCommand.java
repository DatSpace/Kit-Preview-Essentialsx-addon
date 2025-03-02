package me.enzol.kitspreview.commands;

import com.earth2me.essentials.Essentials;
import me.enzol.kitspreview.KitsPreview;
import me.enzol.kitspreview.kitpreview.listeners.InventoryListener;
import me.enzol.kitspreview.kitpreview.KitPreview;
import me.enzol.kitspreview.utils.Color;
import me.enzol.kitspreview.utils.EssentialsUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class KitPreviewCommand implements CommandExecutor, TabExecutor{

    public KitPreviewCommand(KitsPreview pluginInstance){
        this.pluginInstance = pluginInstance;
        this.config = pluginInstance.getConfig();
    }

    private final KitsPreview pluginInstance;
    private final Configuration config;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args){

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players");
            return false;
        }

        Player player = (Player) sender;

        if(args.length < 1){
            player.sendMessage(Color.translate( "&bUse&7: "));
            player.sendMessage(Color.translate("&b/kitpreview (kit name)"));
            return false;
        }

        if(player.isSleeping()) return false;

        String kitName = args[0];

        KitPreview kitPreview = pluginInstance.getByName(kitName);

        if(kitPreview == null){
            player.sendMessage(ChatColor.RED + "Kit not found");
            return false;
        }

        Inventory inventory = Bukkit.createInventory(null,
            9 * kitPreview.getRows(),
            Color.translate(config.getString("gui.title").replace("{kit}", kitName)));

        if(kitPreview.getItems().isEmpty()){
            EssentialsUtils.getItems(player, kitName).forEach(inventory::addItem);
        }else {
            kitPreview.getItems().forEach(kitItem -> inventory.setItem(kitItem.getSlot(), kitItem.getItem()));
        }

        player.openInventory(inventory);

        InventoryListener.inventorysOpen.add(player.getUniqueId());

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args){
        Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

        if (args.length == 1) {
            return new ArrayList<>(ess.getKits().getKits().getKeys(false)).stream()
                .filter(s1 -> s1.toLowerCase().startsWith(args[0]))
                .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
