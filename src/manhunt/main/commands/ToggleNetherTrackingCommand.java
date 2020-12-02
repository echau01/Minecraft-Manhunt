package manhunt.main.commands;

import manhunt.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ToggleNetherTrackingCommand implements CommandExecutor {
    private Main plugin;

    public ToggleNetherTrackingCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equals("togglenethertracking")) {
                if (args.length == 0) {
                    FileConfiguration config = plugin.getConfig();
                    config.set("nethertracking", !config.getBoolean("nethertracking", false));
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    if (config.getBoolean("nethertracking")) {
                        p.sendMessage(ChatColor.AQUA + "Player tracking in the Nether is now enabled.");
                    } else {
                        p.sendMessage(ChatColor.AQUA + "Player tracking in the Nether is now disabled.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Correct command usage: /togglenethertracking");
                }
            }
            return true;
        }
        return false;
    }
}
