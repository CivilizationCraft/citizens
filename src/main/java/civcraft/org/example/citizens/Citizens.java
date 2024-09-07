package civcraft.org.example.citizens;

import civcraft.org.example.citizens.listeners.EntityListener;
import civcraft.org.example.citizens.misc.Dialog;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import org.bukkit.entity.Villager;

public final class Citizens extends JavaPlugin {

    private static final Logger log = LoggerFactory.getLogger(Citizens.class);

    @Override
    public void onEnable() {
        // Logique de démarrage du plugin
        log.info("Citizens plugin démarré !");

        // Initialisation de la map dialogs
        EntityListener entityListener = new EntityListener();
        new BukkitRunnable() {
            @Override
            public void run() {
                log.info("ICi");
                entityListener.clearUnusedDialogs();
            }
        }.runTaskTimer(this, 0L, 20*15L); // 100 ticks = 5 secondes (20 ticks = 1 seconde)

        // Enregistrement du listener
        getServer().getPluginManager().registerEvents(entityListener, this);
    }

    private void startRepeatingTask() {
        }

    @Override
    public void onDisable() {
        // Logique d'arrêt du plugin
        log.info("Citizens plugin arrêté !");
    }
}
