package me.neilhommes.skyblockitems.items.partials;

import me.neilhommes.skyblockitems.SkyblockItems;
import me.neilhommes.skyblockitems.nbts.itemNBTData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class VoidCrystal {
    public static Recipe getRecipe() {
        NamespacedKey key = new NamespacedKey(SkyblockItems.getInstance(), "VoidCrystal");

        ShapedRecipe recipe = new ShapedRecipe(key, generateVoidCrystal());
        recipe.shape(" A ", "ABA", " A ");
        recipe.setIngredient('A', new ItemStack(Material.NETHERITE_INGOT));
        recipe.setIngredient('B', new ItemStack(Material.DRAGON_BREATH));
        return recipe;
    }

    public static ItemStack generateVoidCrystal() {
        ItemStack ss = new ItemStack(Material.BLACK_STAINED_GLASS);
        ItemMeta meta = ss.getItemMeta();
        if (meta != null) {
            meta.setEnchantmentGlintOverride(true);
            meta.displayName(Component.text().content("Void Crystal").color(TextColor.color(0x0000ff)).build());
            List<Component> lore = new ArrayList<Component>();
            lore.add(Component.empty()
                    .append(Component.text().content("Netherite infused dragons breath")
                            .color(TextColor.color(0xffffff))));
            meta.lore(lore);
            meta.getPersistentDataContainer()
                    .set(new NamespacedKey(SkyblockItems.getInstance(), "neil_void_crystal"),
                            PersistentDataType.STRING, String.valueOf(itemNBTData.VOID_CRYSTAL));
            ss.setItemMeta(meta);
        }
        return ss;
    }
}
