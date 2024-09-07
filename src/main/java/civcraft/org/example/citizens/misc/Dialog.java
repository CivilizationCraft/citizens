package civcraft.org.example.citizens.misc;

import civcraft.org.example.citizens.entities.Citizen;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.concurrent.TimeUnit;

public class Dialog {
    public Player player;
    public Villager villager;
    public String conversation = getInitialPrompt() ;
    public long lastInteraction = System.currentTimeMillis();
    public boolean isAnswering = false;

    public Dialog(Player player, Villager villager) {
        this.player = player;
        this.villager = villager;
    }

    public String getInitialPrompt(){
        return "";
    }

    public String generateResponse(String request){
        this.villager.customName(Component.text("..."));
        this.isAnswering = true;
        try {
            Thread.sleep(8000);
            villager.customName(Component.text("Thorne Oakheart"));
            this.villager.setCustomNameVisible(true);
            return " " + "<Response>";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public String getChatMessageFormat(Entity e, String m){
        if (e instanceof Villager)
            return "<" + ChatColor.GREEN + e.getName() + ChatColor.WHITE + "> " +  m;
        return "<" + e.getName() + "> " +  m;
    }

    public void sendLastMessage(){
        this.player.sendMessage(getChatMessageFormat(villager,
                " J'espère t'avoir été utile au moins !"));

    }

    public void ask(String request){
        this.lastInteraction =System.currentTimeMillis();
        if (isAnswering) {
            this.player.sendMessage("[WARNING] Tu as déjà demandé quelque chose au villageois !");
            return;
        }
        this.player.sendMessage(getChatMessageFormat(villager, generateResponse(request)));
        this.lastInteraction =System.currentTimeMillis();

        this.isAnswering = false;
    }

}
