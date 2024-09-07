package civcraft.org.example.citizens.listeners;


import civcraft.org.example.citizens.entities.Citizen;
import civcraft.org.example.citizens.misc.Dialog;
import io.papermc.paper.entity.LookAnchor;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class EntityListener implements Listener {

    public Map<Player, Dialog> dialogs;

    public EntityListener() {
        this.dialogs = new HashMap<>();
    }

    public boolean IsBusy(Villager villager){
        if (this.dialogs.isEmpty())
                return false;

        for (Map.Entry<Player, Dialog> entry : this.dialogs.entrySet()) {
            Dialog dialog = entry.getValue();
            if (villager == dialog.villager)
                return true;
        }

        return false;
    }

    public String getChatMessageFormat(Entity e, String m){
        if (e instanceof Villager)
            return "<" + ChatColor.GREEN + e.getName() + ChatColor.WHITE + "> " +  m;
        return "<" + e.getName() + "> " +  m;
    }

    public void clearUnusedDialogs(){
        if (this.dialogs.isEmpty())
            return;
        long currentTime = System.currentTimeMillis();
        this.dialogs.forEach((villager, dialog) -> {
            if (currentTime-dialog.lastInteraction > 10000L && !(dialog.isAnswering)) {
                dialog.sendLastMessage();
                this.dialogs.remove(villager);
                villager.setAI(true);
            }

        });
    }
    @EventHandler
    public void onPlayerInteractAtVillager(PlayerInteractAtEntityEvent event) {

        Player player = event.getPlayer();
        Entity entity =  event.getRightClicked();

        if (!(entity instanceof Villager) || !(player.isSneaking()))
            return;
        Villager villager = (Villager) entity;

        if (IsBusy(villager)){
            player.sendMessage("Une seconde, je suis occupé !");
            return;
        }

        //le villageois prête son attention
        villager.setCustomNameVisible(true);
        villager.customName(Component.text("Thorne Oakheart"));
        villager.lookAt(player);
        villager.setAI(false);

        player.sendMessage(getChatMessageFormat(villager,
                "Bonjour, que puis-je faire pour vous ? " + ChatColor.GRAY + "(ex: /say Comment tu t'appelles ?)"));
        this.dialogs.put(player, new Dialog(player, villager));
    }


    @EventHandler
    public void onPlayerCommandEnter(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String[] array = message.split(" ");
        String cmd = array[0];
        String arg = String.join(" ", Arrays.copyOfRange(array, 1, array.length));

        if ( cmd.equalsIgnoreCase("/say") ) {
            player.sendMessage("<" + player.getName() + ">: " + arg);
            this.dialogs.forEach((_, dialog) -> {
                if (dialog.player == player)
                    dialog.ask(arg);
            });

        }
        event.setCancelled(true);
    }

}