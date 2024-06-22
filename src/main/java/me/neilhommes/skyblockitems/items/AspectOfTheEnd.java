package me.neilhommes.skyblockitems.items;

import com.destroystokyo.paper.ParticleBuilder;
import me.neilhommes.skyblockitems.SkyblockItems;
import me.neilhommes.skyblockitems.items.partials.VoidCrystal;
import me.neilhommes.skyblockitems.nbts.itemNBTData;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
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

import java.util.ArrayList;
import java.util.List;

public class AspectOfTheEnd implements Listener {

    private static final int teleportDistance = 6;

    @EventHandler
    public static void onRightClicked(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if (item == null || !item.hasItemMeta() || !action.isRightClick()) {
            return;
        }

        if (item.getItemMeta().getPersistentDataContainer()
                .has(new NamespacedKey(SkyblockItems.getInstance(), "neil_aote"), PersistentDataType.STRING)) {
            Player player = event.getPlayer();
            Location location = player.getLocation();
            World world = player.getWorld();
            float yaw = location.getYaw();
            float pitch = location.getPitch();
            float tpd = teleportDistance;
            Location destination;
            double calcX = Math.sin(Math.toRadians(yaw));
            double calcY = Math.sin(-1 * Math.toRadians(pitch));
            double calcZ = Math.cos(Math.toRadians(yaw));
            destination = new Location(player.getWorld(),
                    location.x() - tpd * calcX,
                    location.y() + tpd * calcY,
                    location.z() + tpd * calcZ,
                    yaw,
                    pitch);
            for (float i = tpd; i > 0.5; i-=0.5) {
                destination.setX(location.x() - tpd * calcX);
                destination.setY(location.y() + tpd * calcY);
                destination.setZ(location.z() + tpd * calcZ);
                if (!destination.getBlock().isSolid()) {
                    break;
                }
                tpd-- ;
            }
            Location warpDest;
            if (!destination.getBlock().isSolid()) {
                player.teleport(destination);
                warpDest = new Location(world, destination.x(), destination.y() + 1, destination.z());
                ParticleBuilder pb = new ParticleBuilder(Particle.PORTAL);
                pb.color(null, 20)
                        .location(warpDest)
                        .count(3)
                        .spawn();
            } else {
                warpDest = new Location(world, location.x(), location.y() + 1, location.z());
                ParticleBuilder pb = new ParticleBuilder(Particle.PORTAL);
                pb.color(null, 20)
                        .location(warpDest)
                        .count(3)
                        .spawn();
            }
            Sound sound = Sound.sound(
                    Key.key("minecraft", "entity.enderman.teleport"),
                    Sound.Source.AMBIENT,
                    1.0f,
                    1.0f
            );
            player.playSound(sound);
        } else if (item.getItemMeta().getPersistentDataContainer()
                .has(new NamespacedKey(SkyblockItems.getInstance(), "neil_aotv"), PersistentDataType.STRING)){
            Player player = event.getPlayer();
            Location location = player.getLocation();
            World world = player.getWorld();
            float yaw = location.getYaw();
            float pitch = location.getPitch();
            float tpd = teleportDistance+2;
            Location destination;
            double calcX = Math.sin(Math.toRadians(yaw));
            double calcY = Math.sin(-1 * Math.toRadians(pitch));
            double calcZ = Math.cos(Math.toRadians(yaw));
            destination = new Location(player.getWorld(),
                    location.x() - tpd * calcX,
                    location.y() + tpd * calcY,
                    location.z() + tpd * calcZ,
                    yaw,
                    pitch);
            for (float i = tpd; i > 0.5; i-=0.5) {
                destination.setX(location.x() - tpd * calcX);
                destination.setY(location.y() + tpd * calcY);
                destination.setZ(location.z() + tpd * calcZ);
                if (!destination.getBlock().isSolid()) {
                    break;
                }
                tpd-- ;
            }
            Location warpDest;
            if (!destination.getBlock().isSolid()) {
                player.teleport(destination);
                warpDest = new Location(world, destination.x(), destination.y() + 1, destination.z());
                ParticleBuilder pb = new ParticleBuilder(Particle.PORTAL);
                pb.color(null, 20)
                        .location(warpDest)
                        .count(3)
                        .spawn();
            } else {
                warpDest = new Location(world, location.x(), location.y() + 1, location.z());
                ParticleBuilder pb = new ParticleBuilder(Particle.PORTAL);
                pb.color(null, 20)
                        .location(warpDest)
                        .count(3)
                        .spawn();
            }
            Sound sound = Sound.sound(
                    Key.key("minecraft", "entity.enderman.teleport"),
                    Sound.Source.AMBIENT,
                    1.0f,
                    1.0f
            );
            player.playSound(sound);
        }
    }

    public static Recipe getRecipeAOTE() {
        NamespacedKey key = new NamespacedKey(SkyblockItems.getInstance(), "AspectOfTheEnd");

        ShapedRecipe recipe = new ShapedRecipe(key, generateAOTE());
        recipe.shape(" A ", " A ", " B ");
        recipe.setIngredient('A', new ItemStack(Material.SHULKER_SHELL));
        recipe.setIngredient('B', new ItemStack(Material.DIAMOND));
        return recipe;
    }

    public static ItemStack generateAOTE() {
        ItemStack aote = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = aote.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text().content("Aspect Of The End").color(TextColor.color(0x913FF)).build());
            List<Component> lore = new ArrayList<Component>();
            lore.add(Component.empty()
                    .append(Component.text().content("RIGHT CLICK")
                            .color(TextColor.color(0xFCFF33))
                            .style(Style.style(TextDecoration.BOLD)))
                    .append(Component.text(" to teleport!")
                            .color(TextColor.color(0xffffff))));
            meta.lore(lore);
            meta.getPersistentDataContainer()
                    .set(new NamespacedKey(SkyblockItems.getInstance(), "neil_aote"),
                            PersistentDataType.STRING, String.valueOf(itemNBTData.AOTE));
            aote.setItemMeta(meta);
        }
        return aote;
    }

    public static Recipe getRecipeAOTV() {
        NamespacedKey key = new NamespacedKey(SkyblockItems.getInstance(), "AspectOfTheVoid");

        ShapedRecipe recipe = new ShapedRecipe(key, generateAOTV());
        recipe.shape(" A ", " A ", " B ");
        recipe.setIngredient('A', VoidCrystal.generateVoidCrystal());
        recipe.setIngredient('B', generateAOTE());
        return recipe;
    }

    public static ItemStack generateAOTV() {
        ItemStack aotv = new ItemStack(Material.DIAMOND_SHOVEL);
        ItemMeta meta = aotv.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text().content("Aspect Of The Void").color(TextColor.color(0x913FF)).build());
            List<Component> lore = new ArrayList<Component>();
            lore.add(Component.empty()
                    .append(Component.text().content("RIGHT CLICK")
                            .color(TextColor.color(0xFCFF33))
                            .style(Style.style(TextDecoration.BOLD)))
                    .append(Component.text(" to teleport!")
                            .color(TextColor.color(0xffffff))));
            meta.lore(lore);
            meta.getPersistentDataContainer()
                    .set(new NamespacedKey(SkyblockItems.getInstance(), "neil_aotv"),
                            PersistentDataType.STRING, String.valueOf(itemNBTData.AOTV));
            aotv.setItemMeta(meta);
        }
        return aotv;
    }
}
