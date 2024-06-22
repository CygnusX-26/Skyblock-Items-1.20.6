package me.neilhommes.skyblockitems.items.partials;

import me.neilhommes.skyblockitems.SkyblockItems;
import me.neilhommes.skyblockitems.nbts.itemNBTData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class TarantulaSilk {
    public static Recipe getRecipe() {
        NamespacedKey key = new NamespacedKey(SkyblockItems.getInstance(), "TarantulaSilk");

        ShapedRecipe recipe = new ShapedRecipe(key, generateTarantulaSilk());
        recipe.shape(" A ", "ABA", " A ");
        recipe.setIngredient('A', new ItemStack(Material.STRING));
        recipe.setIngredient('B', new ItemStack(Material.NETHERITE_INGOT));
        return recipe;
    }

    public static ItemStack generateTarantulaSilk() {
        ItemStack ss = new ItemStack(Material.COBWEB);
        ItemMeta meta = ss.getItemMeta();
        if (meta != null) {
            meta.setEnchantmentGlintOverride(true);
            meta.displayName(Component.text().content("Tarantula Silk").color(TextColor.color(0x0000ff)).build());
            List<Component> lore = new ArrayList<Component>();
            lore.add(Component.empty()
                    .append(Component.text("Netherite infused string", TextColor.color(0xffffff))));
            meta.lore(lore);
            meta.getPersistentDataContainer()
                    .set(new NamespacedKey(SkyblockItems.getInstance(), "neil_tarantula_silk"),
                            PersistentDataType.STRING, String.valueOf(itemNBTData.TARANTULA_SILK));
            ss.setItemMeta(meta);
        }
        return ss;
    }
}
