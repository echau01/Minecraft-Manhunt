package manhunt.main.commands;

import manhunt.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class GetHuntersCommand implements CommandExecutor {
    private Main plugin;

    public GetHuntersCommand(Main plugin) {
        this.plugin = plugin;
    }

    // The command "/gethunters" displays a list of all current hunters.
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equals("gethunters")) {
                if (args.length == 0) {
                    Set<UUID> hunterIds = plugin.getHunterIds();
                    if (!hunterIds.isEmpty()) {
                        StringBuilder hunters = new StringBuilder();
                        Iterator<UUID> it = hunterIds.iterator();
                        String oneHunter = Bukkit.getOfflinePlayer(it.next()).getName();
                        while (it.hasNext()) {
                            hunters.append(Bukkit.getOfflinePlayer(it.next()).getName()).append(", ");
                        }
                        hunters.append(oneHunter);
                        p.sendMessage(ChatColor.GREEN + "The current hunters are: "
                                + hunters.toString() + ".");
                    } else {
                        p.sendMessage(ChatColor.GREEN + "There is no hunter right now.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Correct command usage: /gethunters");
                }
            }
            return true;
        }
        return false;
    }
}
