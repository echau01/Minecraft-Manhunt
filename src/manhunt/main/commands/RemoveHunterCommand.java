package manhunt.main.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import manhunt.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.UUID;

public class RemoveHunterCommand implements CommandExecutor {
    private Main plugin;

    public RemoveHunterCommand(Main plugin) {
        this.plugin = plugin;
    }

    // The command "/removehunter <username>" removes the hunter role from the player with the given username.
    // If the player could not be found, we notify the command sender of this.
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equals("removehunter")) {
                if (args.length == 1) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UUID uuid;
                            try {
                                uuid = getUUIDFromName(args[0]);
                            } catch (IOException e) {
                                p.sendMessage(ChatColor.RED + "An error occurred when trying to get data for the "
                                        + "player with username " + args[0]);
                                return;
                            }
                            if (uuid != null) {
                                OfflinePlayer hunter = Bukkit.getServer().getOfflinePlayer(uuid);
                                if (plugin.getHunterIds().contains(hunter.getUniqueId())) {
                                    plugin.removeHunter(hunter);
                                    Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[Server Broadcast] " + ChatColor.RED +
                                            hunter.getName() + ChatColor.GREEN + " is no longer a hunter.");
                                } else {
                                    p.sendMessage(ChatColor.RED + args[0] + " is not a hunter.");
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "Could not find the player " + args[0] + ".");
                            }
                        }
                    }).start();
                } else {
                    p.sendMessage(ChatColor.RED + "Correct command usage: /removehunter <username>");
                }
            }
            return true;
        }
        return false;
    }

    // EFFECTS: Calls the Mojang API and returns the UUID associated with given username. If no UUID is found,
    //          returns null. Throws IOException if something goes wrong when connecting to the Mojang API.
    private UUID getUUIDFromName(String username) throws IOException {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
        URLConnection connection = url.openConnection();
        connection.connect();

        // Warning: using JsonParser.parseReader(reader) results in a NoSuchMethodError
        // Instead, we use gson.fromJson(reader, type)

        JsonReader reader = new JsonReader(new InputStreamReader(connection.getInputStream()));
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(reader, new TypeToken<Map<String, String>>(){}.getType());

        if (map != null) {
            String uuid = map.get("id");
            if (uuid != null && uuid.length() == 32) {
                String formatted = String.format("%s-%s-%s-%s-%s", uuid.substring(0, 8), uuid.substring(8, 12),
                        uuid.substring(12, 16), uuid.substring(16, 20), uuid.substring(20, 32));
                return UUID.fromString(formatted);
            }
        }

        return null;
    }
}