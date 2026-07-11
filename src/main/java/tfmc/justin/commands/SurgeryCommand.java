package tfmc.justin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import tfmc.justin.managers.SurgeryMenuManager;
import tfmc.justin.surgery;

import java.io.File;

public class SurgeryCommand implements CommandExecutor {
    
    private final SurgeryMenuManager menuManager;
    private final surgery plugin;
    private final FileConfiguration messages;
    
    public SurgeryCommand(SurgeryMenuManager menuManager, surgery plugin) {
        this.menuManager = menuManager;
        this.plugin = plugin;
        
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        this.messages = YamlConfiguration.loadConfiguration(messagesFile);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                messages.getString("command-console", "&cThis command can only be used by players.")));
            return true;
        }
        
        Player surgeon = (Player) sender;
        
        // Check if player name is provided in command
        if (args.length == 0) {
            surgeon.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                messages.getString("command-usage", "&cUsage: /surgery <player_name>")));
            return true;
        }
        
        // Get the target player (patient)
        Player patient = Bukkit.getPlayer(args[0]);
        if (patient == null || !patient.isOnline()) {
            surgeon.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                messages.getString("command-player-not-found", "&cPlayer not found or not online!")));
            return true;
        }
        
        // Check if trying to "operate" on self
        if (patient.getUniqueId().equals(surgeon.getUniqueId())) {
            surgeon.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                messages.getString("command-self-surgery", "&cYou cannot perform surgery on yourself!")));
            return true;
        }
        
        // Check if patient is within config distance
        double maxDistance = plugin.getConfig().getDouble("max-surgery-distance", 5.0);
        if (surgeon.getLocation().distance(patient.getLocation()) > maxDistance) {
            surgeon.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                messages.getString("command-too-far", "&cThe patient must be within 5 blocks of you!")));
            return true;
        }
        
        // Open surgery menu for the player, operating on the specified "patient"
        menuManager.openSurgeryMenu(surgeon, patient);
        
        return true;
    }
}
