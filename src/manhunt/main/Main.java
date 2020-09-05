package manhunt.main;

import manhunt.main.commands.AddHunterCommand;
import manhunt.main.commands.GetHuntersCommand;
import manhunt.main.commands.RemoveHunterCommand;
import manhunt.main.listeners.CompassClickListener;
import manhunt.main.listeners.PlayerRespawnListener;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {
    // The current hunters' UUIDs
    private Set<UUID> hunterIds = new HashSet<UUID>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CompassClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), this);
        getCommand("addhunter").setExecutor(new AddHunterCommand(this));
        getCommand("gethunters").setExecutor(new GetHuntersCommand(this));
        getCommand("removehunter").setExecutor(new RemoveHunterCommand(this));
    }

    @Override
    public void onDisable() {}

    // EFFECTS: returns a set of all hunters' names.
    public Set<UUID> getHunterIds() {
        return this.hunterIds;
    }

    // EFFECTS: adds given hunter's name to hunterNames
    public void addHunter(Player hunter) {
        hunterIds.add(hunter.getUniqueId());
    }

    // EFFECTS: removes given hunter's name from hunterNames
    public void removeHunter(OfflinePlayer hunter) {
        hunterIds.remove(hunter.getUniqueId());
    }
}
