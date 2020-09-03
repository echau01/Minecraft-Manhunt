package main;

import main.commands.AddHunterCommand;
import main.commands.GetHuntersCommand;
import main.commands.RemoveHunterCommand;
import main.listeners.CompassClickListener;
import main.listeners.PlayerRespawnListener;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends JavaPlugin implements Listener {
    // The current hunters' usernames
    // Each player has a unique username, so we use a set of Strings instead of a set of Players to save memory.
    private Set<String> hunterNames = new HashSet<String>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CompassClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), this);
        getCommand("addhunter").setExecutor(new AddHunterCommand(this));
        getCommand("gethunters").setExecutor(new GetHuntersCommand(this));
        getCommand("removehunter").setExecutor(new RemoveHunterCommand(this));
    }

    @Override
    public void onDisable() {
        hunterNames.clear();
    }

    // EFFECTS: returns a set of all hunters' names.
    public Set<String> getHunterNames() {
        return this.hunterNames;
    }

    // EFFECTS: adds given hunter's name to hunterNames
    public void addHunter(Player hunter) {
        hunterNames.add(hunter.getName());
    }

    // EFFECTS: removes given hunter's name from hunterNames
    public void removeHunter(Player hunter) {
        hunterNames.remove(hunter.getName());
    }
}
