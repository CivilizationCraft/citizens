package civcraft.org.example.citizens.entities;

import org.bukkit.entity.Villager;

import javax.xml.crypto.Data;
import java.util.Date;

public class Citizen{
    public Villager e;
    public boolean isBusy = false;
    public long startDiscussionTime = -1;

    public Citizen(Villager villager){
        this.e = villager;
    }

    public void setBusy(){
        this.isBusy = true;
        this.startDiscussionTime = System.currentTimeMillis();
    }

    public void setFree(){
        this.isBusy = false;
        this.startDiscussionTime = -1;
    }
}
