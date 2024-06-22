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
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class SpiritSceptre implements Listener {
    @EventHandler
    public static void onRightClicked(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if (item == null || !item.hasItemMeta() || !action.isRightClick()) {
            return;
        }
        if (item.getItemMeta().getPersistentDataContainer()
                .has(new NamespacedKey(SkyblockItems.getInstance(), "neil_spirit_sceptre"), PersistentDataType.STRING)) {
            Player player = event.getPlayer();
            Location location = player.getLocation();
            World world = player.getWorld();
            Bat bat = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);
            location.setY(location.getY() + 1);
            bat.teleport(location);
            bat.setInvulnerable(true);
            Sound sound = Sound.sound(
                    Key.key("minecraft", "entity.blaze.shoot"),
                    Sound.Source.AMBIENT,
                    1.0f,
                    1.0f
            );
            player.playSound(sound);
            new BukkitRunnable() {
                int ticks = 0;

                @Override
                public void run() {
                    if (ticks < 20) {
                        Vector direction = player.getLocation().getDirection().normalize();
                        direction.multiply(1.4);
                        bat.setVelocity(direction);
                        for (LivingEntity entity : bat.getLocation().getNearbyLivingEntities(2)) {
                            System.out.println(entity.getName());
                            if (entity instanceof Player && ((Player) entity).equals(player) ) {
                                continue;
                            } else if (entity instanceof Bat)  {
                                continue;
                            }
                            ticks += 19;
                            break;
                        }
                        ticks++;
                    } else {
                        Location endLocation = bat.getLocation();
                        ParticleBuilder pb = new ParticleBuilder(Particle.EXPLOSION);
                        pb.color(null, 30)
                                .location(endLocation)
                                .count(2)
                                .spawn();
                        for (Entity entity : world.getNearbyEntities(endLocation, 4, 4, 4)) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                livingEntity.damage(5);
                            }
                        }
                        Sound sound = Sound.sound(
                                Key.key("minecraft", "entity.firework_rocket.blast_far"),
                                Sound.Source.AMBIENT,
                                1.0f,
                                1.0f
                        );
                        player.playSound(sound);
                        bat.remove();
                        cancel();
                    }
                }
            }.runTaskTimer(SkyblockItems.getInstance(), 0, 1); // Run the task every tick (1 tick = 1/20th of a second)


        }
    }

    public static Recipe getRecipe() {
        NamespacedKey key = new NamespacedKey(SkyblockItems.getInstance(), "SpiritSceptre");

        ShapedRecipe recipe = new ShapedRecipe(key, generateSpiritSceptre());
        recipe.shape(" A ", " B ", " B ");
        recipe.setIngredient('A', new ItemStack(Material.END_CRYSTAL));
        recipe.setIngredient('B', new ItemStack(Material.ECHO_SHARD));
        return recipe;
    }

    public static ItemStack generateSpiritSceptre() {
        ItemStack ss = new ItemStack(Material.ALLIUM);
        ItemMeta meta = ss.getItemMeta();

        if (meta != null) {
            meta.setMaxStackSize(1);
            meta.displayName(Component.text().content("Spirit Sceptre").color(TextColor.color(0xFCFF33)).build());
            List<Component> lore = new ArrayList<Component>();
            lore.add(Component.empty()
                    .append(Component.text().content("RIGHT CLICK")
                            .color(TextColor.color(0xFCFF33))
                            .style(Style.style(TextDecoration.BOLD)))
                    .append(Component.text(" to fire Bat Missiles")
                            .color(TextColor.color(0xffffff))));
            meta.lore(lore);
            meta.getPersistentDataContainer()
                    .set(new NamespacedKey(SkyblockItems.getInstance(), "neil_spirit_sceptre"),
                            PersistentDataType.STRING, String.valueOf(itemNBTData.SPIRIT_SCEPTRE));
            ss.setItemMeta(meta);
        }
        return ss;
    }
}
