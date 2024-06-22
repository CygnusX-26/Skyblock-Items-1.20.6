package me.neilhommes.skyblockitems;

import me.neilhommes.skyblockitems.items.AspectOfTheEnd;
import me.neilhommes.skyblockitems.items.Hyperion;
import me.neilhommes.skyblockitems.items.SpiritSceptre;
import me.neilhommes.skyblockitems.items.partials.VoidCrystal;
import me.neilhommes.skyblockitems.listeners.preventItemPlacement;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyblockItems extends JavaPlugin {

    private static SkyblockItems instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().addRecipe(Hyperion.getRecipe());
        getServer().addRecipe(SpiritSceptre.getRecipe());
        getServer().addRecipe(AspectOfTheEnd.getRecipeAOTE());
        getServer().addRecipe(AspectOfTheEnd.getRecipeAOTV());
        getServer().addRecipe(VoidCrystal.getRecipe());

        getServer().getPluginManager().registerEvents(new Hyperion(), this);
        getServer().getPluginManager().registerEvents(new SpiritSceptre(), this);
        getServer().getPluginManager().registerEvents(new preventItemPlacement(), this);
        getServer().getPluginManager().registerEvents(new AspectOfTheEnd(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SkyblockItems getInstance() {
        return instance;
    }
}
