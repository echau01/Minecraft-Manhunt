package main.commands;

import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

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
                    Set<String> hunterNames = plugin.getHunterNames();
                    if (!hunterNames.isEmpty()) {
                        StringBuilder hunters = new StringBuilder();
                        Iterator<String> it = hunterNames.iterator();
                        String oneHunter = it.next();
                        while (it.hasNext()) {
                            hunters.append(it.next()).append(", ");
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
