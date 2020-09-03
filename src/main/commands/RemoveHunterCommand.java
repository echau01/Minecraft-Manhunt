package main.commands;

import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveHunterCommand implements CommandExecutor {
    private Main plugin;

    public RemoveHunterCommand(Main plugin) {
        this.plugin = plugin;
    }

    // The command "/removehunter <username>" removes the hunter role from the player with the given username.
    // If the player is not a hunter or is not online, the command just prints an appropriate message.
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equals("removehunter")) {
                if (args.length == 1) {
                    Player hunter = Bukkit.getServer().getPlayer(args[0]);
                    if (hunter != null) {
                        if (plugin.getHunterNames().contains(hunter.getName())) {
                            plugin.removeHunter(hunter);
                            Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[Server Broadcast] " + ChatColor.RED +
                                    hunter.getName() + ChatColor.GREEN + " is no longer a hunter.");
                        } else {
                            p.sendMessage(ChatColor.RED + hunter.getName() + " is not a hunter.");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Could not find the player " + args[0] + ".");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Correct command usage: /removehunter <username>");
                }
            }
            return true;
        }
        return false;
    }
}