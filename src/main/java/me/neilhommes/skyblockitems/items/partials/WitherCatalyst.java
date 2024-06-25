package me.neilhommes.skyblockitems.items.partials;

import me.neilhommes.skyblockitems.SkyblockItems;
import me.neilhommes.skyblockitems.nbts.itemNBTData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class WitherCatalyst {
    public static Recipe getRecipe() {
        NamespacedKey key = new NamespacedKey(SkyblockItems.getInstance(), "WitherCatalyst");

        ShapelessRecipe recipe = new ShapelessRecipe(key, generateWitherCatalyst());
        recipe.addIngredient(new ItemStack(Material.NETHER_STAR));
        recipe.addIngredient(new ItemStack(Material.WITHER_SKELETON_SKULL));
        return recipe;
    }

    public static ItemStack generateWitherCatalyst() {
        ItemStack ss = new ItemStack(Material.WITHER_ROSE);
        ItemMeta meta = ss.getItemMeta();
        if (meta != null) {
            meta.setEnchantmentGlintOverride(true);
            meta.displayName(Component.text().content("Wither Catalyst").color(TextColor.color(0x3364FF)).build());
            List<Component> lore = new ArrayList<>();
            lore.add(Component.empty()
                    .append(Component.text("Known to speed up the effects of the wither", TextColor.color(0xffffff))));
            meta.lore(lore);
            meta.getPersistentDataContainer()
                    .set(new NamespacedKey(SkyblockItems.getInstance(), "neil_wither_catalyst"),
                            PersistentDataType.STRING, String.valueOf(itemNBTData.WITHER_CATALYST));
            ss.setItemMeta(meta);
        }
        return ss;
    }
}
