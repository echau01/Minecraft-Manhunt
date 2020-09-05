package manhunt.main.listeners;

import manhunt.main.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerRespawnListener implements Listener {
    private Main plugin;

    public PlayerRespawnListener(Main plugin) {
        this.plugin = plugin;
    }

    // When a hunter respawns, a compass is automatically added to their inventory.
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (plugin.getHunterIds().contains(p.getUniqueId())) {
            p.getInventory().addItem(new ItemStack(Material.COMPASS));
        }
    }
}
