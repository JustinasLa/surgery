package tfmc.justin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tfmc.justin.managers.SurgeryMenuManager;
import tfmc.justin.managers.SurgeryUIUpdater;
import tfmc.justin.SurgeryPlugin;

public class SurgeryCommand implements CommandExecutor {

    private final SurgeryMenuManager menuManager;
    private final SurgeryPlugin plugin;
    private final SurgeryUIUpdater uiUpdater;

    public SurgeryCommand(SurgeryMenuManager menuManager, SurgeryPlugin plugin) {
        this.menuManager = menuManager;
        this.plugin = plugin;
        this.uiUpdater = menuManager.getUiUpdater();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(uiUpdater.getMessage("command-console", "&cThis command can only be used by players."));
            return true;
        }

        Player surgeon = (Player) sender;

        // Check if player name is provided in command
        if (args.length == 0) {
            surgeon.sendMessage(uiUpdater.getMessage("command-usage", "&cUsage: /surgery <player_name>"));
            return true;
        }

        // Get the target player (patient)
        Player patient = Bukkit.getPlayer(args[0]);
        if (patient == null || !patient.isOnline()) {
            surgeon.sendMessage(uiUpdater.getMessage("command-player-not-found", "&cPlayer not found or not online!"));
            return true;
        }

        // Check if trying to "operate" on self
        if (patient.getUniqueId().equals(surgeon.getUniqueId())) {
            surgeon.sendMessage(uiUpdater.getMessage("command-self-surgery", "&cYou cannot perform surgery on yourself!"));
            return true;
        }

        // Check if patient is within config distance
        double maxDistance = plugin.getConfig().getDouble("max-surgery-distance", 5.0);
        if (surgeon.getLocation().distance(patient.getLocation()) > maxDistance) {
            surgeon.sendMessage(uiUpdater.getMessage("command-too-far", "&cThe patient must be within 5 blocks of you!"));
            return true;
        }

        // Open surgery menu for the player, operating on the specified "patient"
        menuManager.openSurgeryMenu(surgeon, patient);

        return true;
    }
}
