package tfmc.justin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import tfmc.justin.managers.SurgeryMenuManager;

public class PlayerListener implements Listener {
    
    private final SurgeryMenuManager menuManager;
    
    public PlayerListener(SurgeryMenuManager menuManager) {
        this.menuManager = menuManager;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Handle player join event
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        String title = view.getTitle();
        
        // Check if the clicked inventory is the surgery menu
        if (menuManager.isSurgeryMenu(title)) {
            // Cancel the event to prevent item removal/movement
            event.setCancelled(true);
            
            // Only handle clicks on the top inventory (the menu), not the player's inventory
            if (event.getClickedInventory() != null && event.getClickedInventory().equals(view.getTopInventory())) {
                // Handle the item click if it's a player
                if (event.getWhoClicked() instanceof Player) {
                    Player player = (Player) event.getWhoClicked();
                    ItemStack clickedItem = event.getCurrentItem();
                    int slot = event.getSlot();
                    
                    // Handle the click
                    menuManager.handleItemClick(player, clickedItem, slot);
                }
            }
        }
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // Check if the closed inventory is the surgery menu
        if (menuManager.isSurgeryMenu(event.getView().getTitle())) {
            if (event.getPlayer() instanceof Player) {
                Player player = (Player) event.getPlayer();
                // Handle abandonment (giving up on surgery)
                menuManager.handleSurgeryAbandonment(player);
            }
        }
    }
}