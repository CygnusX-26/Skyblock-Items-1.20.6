package me.neilhommes.skyblockitems.items;

import com.destroystokyo.paper.ParticleBuilder;
import me.neilhommes.skyblockitems.SkyblockItems;
import me.neilhommes.skyblockitems.nbts.itemNBTData;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Hyperion implements Listener {

    private static final int teleportDistance = 10;

    @EventHandler
    public static void onRightClicked(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if (item == null || !item.hasItemMeta() || !action.isRightClick()) {
            return;
        }

        if (item.getItemMeta().getPersistentDataContainer()
                .has(new NamespacedKey(SkyblockItems.getInstance(), "neil_hyperion"), PersistentDataType.STRING)) {
            Player player = event.getPlayer();
            Location eyeLocation = player.getEyeLocation();
            Vector direction = eyeLocation.getDirection();
            Location destination = eyeLocation.clone().add(direction.clone().multiply(0));
            Location temp;
            for (float i = 0.2f; i < teleportDistance; i+= 0.2f) {
                temp = eyeLocation.clone().add(direction.clone().multiply(i));
                if (!temp.getBlock().isCollidable()) {
                    destination = eyeLocation.clone().add(direction.clone().multiply(i));
                } else {
                    break;
                }

            }
            destination.setY(destination.getY());
            player.teleport(destination);
            Location particleLocation = destination.clone();
            player.teleport(destination);
            particleLocation.setY(particleLocation.getY() + 1);
            ParticleBuilder pb = new ParticleBuilder(Particle.EXPLOSION);
            pb.color(null, 20)
                    .location(particleLocation)
                    .count(3)
                    .spawn();
            Sound sound = Sound.sound(
                    Key.key("minecraft", "entity.generic.explode"),
                    Sound.Source.AMBIENT,
                    1.0f,
                    1.0f
            );
            player.playSound(sound);
            for (LivingEntity entity : particleLocation.getNearbyLivingEntities(7)) {
                if (entity instanceof Player && entity.equals(player)) {
                    continue;
                }
                entity.setNoDamageTicks(0);
                entity.damage(10);
                entity.setNoDamageTicks(10);
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 2));
        }
    }

    public static Recipe getRecipe() {
        NamespacedKey key = new NamespacedKey(SkyblockItems.getInstance(), "Hyperion");

        ShapedRecipe recipe = new ShapedRecipe(key, generateHyperion());
        recipe.shape("DAE", "CAC", " B ");
        recipe.setIngredient('A', new ItemStack(Material.NETHER_STAR));
        recipe.setIngredient('B', AspectOfTheEnd.generateAOTV());
        recipe.setIngredient('C', new ItemStack(Material.END_CRYSTAL));
        recipe.setIngredient('D', new ItemStack(Material.ELYTRA));
        recipe.setIngredient('E', new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        return recipe;
    }

    public static ItemStack generateHyperion() {
        ItemStack hyperion = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = hyperion.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text().content("Hyperion").color(TextColor.color(0xff33fC)).build());
            List<Component> lore = new ArrayList<Component>();
            lore.add(Component.empty()
                    .append(Component.text().content("RIGHT CLICK")
                    .color(TextColor.color(0xFCFF33))
                    .style(Style.style(TextDecoration.BOLD)))
                    .append(Component.text(" to use Wither Impact!")
                            .color(TextColor.color(0xffffff))));
            meta.lore(lore);
            meta.getPersistentDataContainer()
                    .set(new NamespacedKey(SkyblockItems.getInstance(), "neil_hyperion"),
                            PersistentDataType.STRING, String.valueOf(itemNBTData.HYPERION));
            hyperion.setItemMeta(meta);
        }
        return hyperion;
    }
}
