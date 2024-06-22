package me.neilhommes.skyblockitems.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class preventItemPlacement  implements Listener {
    @EventHandler
    public void onItemPlaced(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        for (NamespacedKey key : player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().getKeys()) {
            if (key.getKey().startsWith("neil_")) {
                event.setCancelled(true);
            }
        }
    }

}
