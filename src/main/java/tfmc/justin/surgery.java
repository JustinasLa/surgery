package tfmc.justin;

import org.bukkit.plugin.java.JavaPlugin;
import tfmc.justin.managers.PluginManager;
import tfmc.justin.managers.SurgeryItemsConfig;
import tfmc.justin.managers.SurgeryMenuManager;
import tfmc.justin.listeners.PlayerListener;
import tfmc.justin.commands.SurgeryCommand;

public class surgery extends JavaPlugin {
    
    private static surgery instance;
    private SurgeryMenuManager surgeryMenuManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        // Load surgery items config
        SurgeryItemsConfig itemsConfig = new SurgeryItemsConfig(this);
        
        surgeryMenuManager = new SurgeryMenuManager(this, itemsConfig);
        surgeryMenuManager.initialize();

        PluginManager.getInstance().initialize();
        
        getServer().getPluginManager().registerEvents(new PlayerListener(surgeryMenuManager), this);
        getCommand("surgery").setExecutor(new SurgeryCommand(surgeryMenuManager, this));
        
        getLogger().info("surgery has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("surgery has been disabled!");
    }
    
    public static surgery getInstance() {
        return instance;
    }
    
}