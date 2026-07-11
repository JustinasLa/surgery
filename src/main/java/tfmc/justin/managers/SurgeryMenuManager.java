package tfmc.justin.managers;

import me.Plugins.TLibs.Enums.APIType;
import me.Plugins.TLibs.Objects.API.ItemAPI;
import me.Plugins.TLibs.TLibs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

// ==============================================
// Main file for the surgery menu system
// Delegates manager files for different responsibilities
// ==============================================
public class SurgeryMenuManager {
    
    private final JavaPlugin plugin;
    private final SurgeryItemsConfig itemsConfig;
    private ItemAPI api;
    
    // Specialized managers
    private DiagnosisChecker diagnosisChecker;
    private SurgeryStateManager stateManager;
    private SurgeryUIUpdater uiUpdater;
    private SurgeryMenuBuilder menuBuilder;
    private SurgeryCompletionHandler completionHandler;
    private SurgeryItemHandler itemHandler;
    private SurgeryMechanicsManager mechanicsManager;
    
    public SurgeryMenuManager(JavaPlugin plugin, SurgeryItemsConfig itemsConfig) {
        this.plugin = plugin;
        this.itemsConfig = itemsConfig;
    }
    
    // ==============================================
    // Initializes all managers and loads TLibs API
    // ==============================================
    public void initialize() {
        plugin.getLogger().info("[Surgery] Loading TLibs API...");
        api = (ItemAPI) TLibs.getApiInstance(APIType.ITEM_API);
        
        // Initialize all managers in dependency order
        diagnosisChecker = new DiagnosisChecker(plugin);
        stateManager = new SurgeryStateManager();
        uiUpdater = new SurgeryUIUpdater(plugin, stateManager, diagnosisChecker);
        completionHandler = new SurgeryCompletionHandler(plugin, stateManager, uiUpdater);
        mechanicsManager = new SurgeryMechanicsManager(plugin, api, stateManager, uiUpdater, completionHandler, diagnosisChecker, itemsConfig);
        menuBuilder = new SurgeryMenuBuilder(plugin, api, stateManager, uiUpdater, itemsConfig);
        itemHandler = new SurgeryItemHandler(plugin, api, stateManager, uiUpdater, mechanicsManager, completionHandler, diagnosisChecker, itemsConfig);
        
        // Initialize any managers that need config
        mechanicsManager.initialize();
        itemHandler.initialize();
        
        plugin.getLogger().info("[Surgery] Surgery menu manager initialized!");
    }
    
    // ==============================================
    // Opens the surgery menu for the surgeon, operating on the specified patient
    // ==============================================
    public void openSurgeryMenu(Player surgeon, Player patient) {
        stateManager.setPatientName(surgeon.getUniqueId(), patient.getName());
        menuBuilder.buildAndOpenMenu(surgeon);
    }
    
    // ==============================================
    // Checks if an inventory title matches the surgery menu
    // ==============================================
    public boolean isSurgeryMenu(String title) {
        return title.equals("Surgery Menu");
    }
    
    // ==============================================
    // Handles item clicks within the surgery menu
    // Delegates to the item handler
    // ==============================================
    public void handleItemClick(Player player, ItemStack clickedItem, int slot) {
        itemHandler.handleItemClick(player, clickedItem, slot);
    }
    
    // ==============================================
    // Handles menu abandonment (player closed menu early)
    // Called from PlayerListener
    // ==============================================
    public void handleSurgeryAbandonment(Player player) {
        completionHandler.handleAbandonment(player);
    }
    
    // ==============================================
    // Cleanup -> removes all player data
    // ==============================================
    public void cleanup(Player player) {
        stateManager.cleanup(player.getUniqueId());
    }
    
    // ==============================================
    // Getters for accessing individual managers
    // ==============================================
    public SurgeryStateManager getStateManager() { return stateManager; }
    public SurgeryUIUpdater getUiUpdater() { return uiUpdater; }
    public SurgeryMenuBuilder getMenuBuilder() { return menuBuilder; }
    public SurgeryCompletionHandler getCompletionHandler() { return completionHandler; }
    public SurgeryItemHandler getItemHandler() { return itemHandler; }
    public SurgeryMechanicsManager getMechanicsManager() { return mechanicsManager; }
}
