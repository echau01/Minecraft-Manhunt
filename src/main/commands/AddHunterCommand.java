package main.commands;

import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddHunterCommand implements CommandExecutor {
    private Main plugin;

    public AddHunterCommand(Main plugin) {
        this.plugin = plugin;
    }

    // The command "/addhunter <username>" sets the player with the given username as a hunter.
    // If the player is already a hunter or is not online, the command just prints an appropriate message.
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equals("addhunter")) {
                if (args.length == 1) {
                    Player hunter = Bukkit.getServer().getPlayer(args[0]);
                    if (hunter != null) {
                        if (!plugin.getHunterNames().contains(hunter.getName())) {
                            plugin.addHunter(hunter);
                            hunter.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
                            Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[Server Broadcast] " + ChatColor.RED +
                                    hunter.getName() + ChatColor.GREEN + " is now a hunter.");
                        } else {
                            p.sendMessage(ChatColor.RED + hunter.getName() + " is already a hunter.");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Could not find the player " + args[0] + ".");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Correct command usage: /addhunter <username>");
                }
            }
            return true;
        }
        return false;
    }
}
