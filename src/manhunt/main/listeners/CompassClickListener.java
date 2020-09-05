package manhunt.main.listeners;

import manhunt.main.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
    public void onHunterClickCompass(PlayerInteractEvent e) {
        if (plugin.getHunterIds().contains(e.getPlayer().getUniqueId())) {
            Player hunter = e.getPlayer();
            ItemStack item = e.getItem();
            if (item != null && item.isSimilar(new ItemStack(Material.COMPASS))) {
                Player closestPlayer = getClosestNonHunterPlayer(hunter);

                if (closestPlayer != null) {
                    hunter.setCompassTarget(closestPlayer.getLocation());
                    hunter.sendMessage(ChatColor.AQUA + "Now tracking " + closestPlayer.getName() + ".");
                } else {
                    hunter.sendMessage(ChatColor.AQUA + "Could not find a player to track in your world.");
                }
            }
        }
    }

    // EFFECTS: returns the closest non-hunter player to the given hunter. The returned player must be
    //          in survival mode and in the same dimension as the hunter. If no such player is found,
    //          returns null.
    private Player getClosestNonHunterPlayer(Player hunter) {
        Set<UUID> hunterIds = plugin.getHunterIds();
        Location hunterLocation = hunter.getLocation();
        Player closestPlayer = null;
        double closestDistanceSquared = Double.MAX_VALUE;

        List<Player> candidates = hunter.getWorld().getPlayers();
        for (UUID id : hunterIds) {
            Player p = Bukkit.getPlayer(id);
            candidates.remove(p);
        }
        for (Player p : candidates) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                double distanceSquared = p.getLocation().distanceSquared(hunterLocation);
                if (distanceSquared < closestDistanceSquared) {
                    closestDistanceSquared = distanceSquared;
                    closestPlayer = p;
                }
            }
        }
        return closestPlayer;
    }
}
