package tfmc.justin;

import org.bukkit.plugin.java.JavaPlugin;
import tfmc.justin.managers.SurgeryItemsConfig;
import tfmc.justin.managers.SurgeryMenuManager;
import tfmc.justin.listeners.PlayerListener;
import tfmc.justin.commands.SurgeryCommand;

public class SurgeryPlugin extends JavaPlugin {

    private static SurgeryPlugin instance;
    private SurgeryMenuManager surgeryMenuManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        // Load surgery items config
        SurgeryItemsConfig itemsConfig = new SurgeryItemsConfig(this);

        surgeryMenuManager = new SurgeryMenuManager(this, itemsConfig);
        surgeryMenuManager.initialize();

        // initialize() disables the plugin if the TLibs API could not be loaded
        if (!isEnabled()) {
            return;
        }

        getServer().getPluginManager().registerEvents(new PlayerListener(surgeryMenuManager), this);
        getCommand("surgery").setExecutor(new SurgeryCommand(surgeryMenuManager, this));

        getLogger().info("surgery has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("surgery has been disabled!");
    }

    public static SurgeryPlugin getInstance() {
        return instance;
    }

}
