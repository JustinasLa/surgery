package tfmc.justin.managers;

import me.Plugins.TLibs.Objects.API.ItemAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.UUID;

// ==============================================
// Builds and initializes the surgery menu
// ==============================================
public class SurgeryMenuBuilder {
    
    private final JavaPlugin plugin;
    private final ItemAPI api;
    private final SurgeryStateManager stateManager;
    private final SurgeryUIUpdater uiUpdater;
    private final SurgeryItemsConfig itemsConfig;
    private final Random random;
    
    public SurgeryMenuBuilder(JavaPlugin plugin, ItemAPI api, SurgeryStateManager stateManager, SurgeryUIUpdater uiUpdater, SurgeryItemsConfig itemsConfig) {
        this.plugin = plugin;
        this.api = api;
        this.stateManager = stateManager;
        this.uiUpdater = uiUpdater;
        this.itemsConfig = itemsConfig;
        this.random = new Random();
    }
    
    // ==============================================
    // Builds and opens the surgery menu for a player
    // ==============================================
    public void buildAndOpenMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 54, "Surgery Menu");
        
        // Map specific items to specific slots
        int[][] slotMapping = {
            {28, 0},  // sponge
            {29, 1},  // scalpel
            {30, 2},  // stitches
            {32, 4},  // antiseptic
            {34, 6},  // ultrasound
            {37, 7},  // lab_kit
            {38, 8},  // anesthetic
            {43, 13}  // transfusion
            
            // Antibiotics (31) appears after using the lab kit

            // Surgical Glove/Fix it (33),
            // Defibrillator (39),
            // Pins (40),
            // Splint (41),
            // Clamp (42)
            // appear dynamically
        };
        
        // Place items in the specified slots
        for (int[] mapping : slotMapping) {
            int slot = mapping[0];
            int itemIndex = mapping[1];
            
            String itemPath = itemsConfig.getItemPath(itemIndex);
            if (itemPath != null) {
                ItemStack item = api.getCreator().getItemFromPath(itemPath);
                if (item != null) {
                    menu.setItem(slot, item);
                } else {
                    plugin.getLogger().warning("[Surgery] Could not load item: " + itemPath);
                }
            }
        }
        
        // Add placeholder info blocks (slots 10-16)
        for (int i = 10; i <= 16; i++) {
            ItemStack infoBlock = uiUpdater.createInfoBlock(Material.RED_CONCRETE, " ", "");
            menu.setItem(i, infoBlock);
        }
        
        initializePlayerState(player, menu);
        player.openInventory(menu);
    }
    
    // ==============================================
    // Initializes player state when opening menu
    // ==============================================
    private void initializePlayerState(Player player, Inventory menu) {
        UUID playerId = player.getUniqueId();
        
        // ==============================================
        // Set the first info block (slot 10) as "diagnosis"
        // ==============================================
        ItemStack diagnosisBlock = uiUpdater.createInfoBlock(Material.RED_CONCRETE, ChatColor.GOLD + "Diagnosis", ChatColor.GRAY + "The patient has not been diagnosed.");
        menu.setItem(10, diagnosisBlock);
        
        // ==============================================
        // Set the second info block (slot 11) as "pulse". Always starts at Strong
        // ==============================================
        String pulseStatus = "Strong";
        stateManager.setPulse(playerId, pulseStatus);
        Material pulseColor = SurgeryConstants.getPulseColor(pulseStatus);
        ItemStack pulseBlock = uiUpdater.createInfoBlock(pulseColor, ChatColor.GOLD + "Pulse", ChatColor.GRAY + pulseStatus);
        menu.setItem(11, pulseBlock);
        
        // ==============================================
        // Set the third info block (slot 12) as "status". Always starts at Awake
        // ==============================================
        String patientStatus = "Awake";
        stateManager.setStatus(playerId, patientStatus);
        Material statusColor = SurgeryConstants.getStatusColor(patientStatus);
        ItemStack statusBlock = uiUpdater.createInfoBlock(statusColor, ChatColor.GOLD + "Status", ChatColor.GRAY + patientStatus);
        menu.setItem(12, statusBlock);
        
        // ==============================================
        // Randomize if patient has rising temperature (50% chance)
        // ==============================================
        boolean hasRisingTemp = random.nextBoolean();
        stateManager.setHasRisingTemp(playerId, hasRisingTemp);
        
        // ==============================================
        // Set the fourth info block (slot 13) as "temperature"
        // If patient has rising temp, start with random temperature from config range
        // ==============================================
        double temperature;
        if (hasRisingTemp) {
            double minTemp = plugin.getConfig().getDouble("temperature.rising-temp-min", 98.6);
            double maxTemp = plugin.getConfig().getDouble("temperature.rising-temp-max", 104.0);
            temperature = minTemp + (random.nextDouble() * (maxTemp - minTemp));
        } else {
            temperature = plugin.getConfig().getDouble("temperature.normal", 98.6);
        }
        stateManager.setTemperature(playerId, temperature);
        Material tempColor = SurgeryConstants.getTemperatureColor(temperature);
        String tempDisplay = SurgeryConstants.formatTemperature(temperature);
        ItemStack tempBlock = uiUpdater.createInfoBlock(tempColor, ChatColor.GOLD + "Temperature", ChatColor.GRAY + tempDisplay);
        menu.setItem(13, tempBlock);
        
        // ==============================================
        // Set the fifth info block (slot 14) as "operation site". Always starts at Not sanitized
        // ==============================================
        String opSiteStatus = "Not sanitized";
        stateManager.setOperationSite(playerId, opSiteStatus);
        Material opSiteColor = SurgeryConstants.getOperationSiteColor(opSiteStatus);
        ItemStack opSiteBlock = uiUpdater.createInfoBlock(opSiteColor, ChatColor.GOLD + "Operation site", ChatColor.GRAY + opSiteStatus);
        menu.setItem(14, opSiteBlock);
        
        // ==============================================
        // Set the sixth info block (slot 15) as "incisions". Always starts at 0
        // ==============================================
        int incisions = 0;
        stateManager.setIncisions(playerId, incisions);
        Material incisionColor = SurgeryConstants.getIncisionColor(incisions);
        ItemStack incisionBlock = uiUpdater.createInfoBlock(incisionColor, ChatColor.GOLD + "Incisions", ChatColor.GRAY + String.valueOf(incisions));
        menu.setItem(15, incisionBlock);
        
        // ==============================================
        // Set the seventh info block (slot 16) as "skill fail". Starts empty
        // ==============================================
        stateManager.setSkillFail(playerId, "");
        ItemStack skillFailBlock = uiUpdater.createInfoBlock(Material.LIME_CONCRETE, ChatColor.GOLD + "Skill Fail", ChatColor.GRAY + "Nothing to show here");
        menu.setItem(16, skillFailBlock);

        // ==============================================
        // Initialize other state variables
        // ==============================================
        stateManager.setBleeding(playerId, false);
        stateManager.setCured(playerId, false);
        stateManager.setAntibioticsCounter(playerId, 0);
        stateManager.setAntisepticProtection(playerId, false);
        stateManager.setSpongeEffect(playerId, false);
        stateManager.setMoveCount(playerId, 0);
        stateManager.setMovesSinceLastSponge(playerId, 0);
        stateManager.setWoundsExamined(playerId, false);
        stateManager.setUnconsciousTimer(playerId, 0);

        // ==============================================
        // Initialize bone counts (will be set after diagnosis)
        // ==============================================
        stateManager.setBrokenBones(playerId, 0);
        stateManager.setShatteredBones(playerId, 0);
        stateManager.setRevealedBrokenBones(playerId, 0);
        stateManager.setRevealedShatteredBones(playerId, 0);
    }
}
