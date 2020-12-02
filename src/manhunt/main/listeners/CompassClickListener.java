package manhunt.main.listeners;

import manhunt.main.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CompassClickListener implements Listener {
    private Main plugin;

    public CompassClickListener(Main plugin) {
        this.plugin = plugin;
    }

    // When a hunter clicks their compass, the compass points to the closest player in survival mode
    // that is not a hunter. The compass will not point to players in different dimensions.
    @EventHandler
    public void onCompassClick(PlayerInteractEvent e) {
        if (plugin.getHunterIds().contains(e.getPlayer().getUniqueId())) {
            Player hunter = e.getPlayer();
            ItemStack item = e.getItem();
            if (item != null && item.getType() == Material.COMPASS) {
                Player closestVictim = getClosestVictim(hunter);
                if (closestVictim != null) {
                    if (plugin.getConfig().getBoolean("nethertracking", false)) {
                        CompassMeta meta = (CompassMeta) item.getItemMeta();

                        // I don't think meta will ever be null. But, just in case:
                        if (meta == null) {
                            meta = (CompassMeta) (new ItemStack(Material.COMPASS).getItemMeta());
                        }

                        meta.setLodestoneTracked(false);
                        meta.setLodestone(closestVictim.getLocation());
                        item.setItemMeta(meta);
                    } else {
                        hunter.setCompassTarget(closestVictim.getLocation());
                    }
                    hunter.sendMessage(ChatColor.AQUA + "Now tracking " + closestVictim.getName() + ".");
                } else {
                    hunter.sendMessage(ChatColor.AQUA + "Could not find a player to track in your world.");
                }
            }
        }
    }

    // EFFECTS: returns the closest non-hunter player to the given hunter. The returned player must be
    //          in survival mode and in the same dimension as the hunter. If no such player is found,
    //          returns null.
    private Player getClosestVictim(Player hunter) {
        Set<UUID> hunterIds = plugin.getHunterIds();
        Location hunterLocation = hunter.getLocation();
        Player closestPlayer = null;
        double closestDistanceSquared = Double.MAX_VALUE;

        List<Player> candidates = hunter.getWorld().getPlayers();
        for (Player p : candidates) {
            if (!hunterIds.contains(p.getUniqueId()) && p.getGameMode() == GameMode.SURVIVAL) {
                double distanceSquared = p.getLocation().distanceSquared(hunterLocation);
                if (distanceSquared <= closestDistanceSquared) {
                    closestDistanceSquared = distanceSquared;
                    closestPlayer = p;
                }
            }
        }
        return closestPlayer;
    }
}
