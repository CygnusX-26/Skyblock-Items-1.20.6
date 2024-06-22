package me.neilhommes.skyblockitems.items;

import me.neilhommes.skyblockitems.SkyblockItems;
import me.neilhommes.skyblockitems.items.partials.TarantulaSilk;
import me.neilhommes.skyblockitems.nbts.itemNBTData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Terminator implements Listener {
    @EventHandler
    public static void onClicked(PlayerInteractEvent event) {
        ItemStack bow = event.getItem();
        Player player = (Player) event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        if (bow == null || !bow.hasItemMeta()) {
            return;
        }

        if (bow.getItemMeta().getPersistentDataContainer()
                .has(new NamespacedKey(SkyblockItems.getInstance(), "neil_terminator"), PersistentDataType.STRING)) {
            if (event.getAction().isLeftClick()) {
                //TODO: shoot beam
            }
            event.setCancelled(true);
            boolean hasArrows = false;
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                for (ItemStack stack : inventory.getContents()) {
                    if (stack != null
                            && (stack.getType().equals(Material.ARROW)
                            || stack.getType().equals(Material.TIPPED_ARROW)
                            || stack.getType().equals(Material.SPECTRAL_ARROW))) {
                        stack.setAmount(stack.getAmount() - 1 );
                        hasArrows = true;
                    }
                }
            } else {
                hasArrows = true;
            }

            if (hasArrows) {
                Location location = player.getLocation();
                location.setY(location.getY() + 1.5);
                Vector direction = location.getDirection();
                player.getWorld().spawnArrow(location, direction, 3.5f, 4.0f);
                player.getWorld().spawnArrow(location, direction, 3.5f, 4.0f);
                player.getWorld().spawnArrow(location, direction, 3.5f, 4.0f);
            }
        }
    }

    public static Recipe getRecipe() {
        NamespacedKey key = new NamespacedKey(SkyblockItems.getInstance(), "Terminator");

        ShapedRecipe recipe = new ShapedRecipe(key, generateSpiritSceptre());
        recipe.shape(" DC", "ABC", " DC");
        recipe.setIngredient('A', new ItemStack(Material.NETHER_STAR));
        recipe.setIngredient('B', new ItemStack(Material.BOW));
        recipe.setIngredient('C', TarantulaSilk.generateTarantulaSilk());
        recipe.setIngredient('D', new ItemStack(Material.NETHERITE_INGOT));
        return recipe;
    }

    public static ItemStack generateSpiritSceptre() {
        ItemStack ss = new ItemStack(Material.BOW);
        ItemMeta meta = ss.getItemMeta();

        if (meta != null) {
            meta.setMaxStackSize(1);
            meta.displayName(Component.text().content("Terminator").color(TextColor.color(0xFF00E0)).build());
            List<Component> lore = new ArrayList<Component>();
            lore.add(Component.empty()
                    .append(Component.text("Short bow!", TextColor.color(0xFFEF00))
                    .append(Component.text(" Instantly shoots",TextColor.color(0xffffff)))));
            lore.add(Component.empty()
                    .append(Component.text("Not affected by infinity", TextColor.color(0xffffff))));
            meta.lore(lore);
            meta.getPersistentDataContainer()
                    .set(new NamespacedKey(SkyblockItems.getInstance(), "neil_terminator"),
                            PersistentDataType.STRING, String.valueOf(itemNBTData.TERMINATOR));
            ss.setItemMeta(meta);
        }
        return ss;
    }

}
