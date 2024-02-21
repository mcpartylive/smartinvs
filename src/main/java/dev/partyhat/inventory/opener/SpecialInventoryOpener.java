package dev.partyhat.inventory.opener;

import com.google.common.collect.ImmutableList;
import dev.partyhat.inventory.InventoryManager;
import dev.partyhat.inventory.SmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class SpecialInventoryOpener implements InventoryOpener {

    private static final List<InventoryType> SUPPORTED = ImmutableList.of(
            InventoryType.DISPENSER,
            InventoryType.DROPPER,
            InventoryType.FURNACE,
            InventoryType.WORKBENCH,
            // Crafting
            InventoryType.ENCHANTING,
            InventoryType.BREWING,
            // Player
            // Creative
            // Merchant
            InventoryType.ANVIL,
            // Smithing
            InventoryType.BEACON,
            InventoryType.HOPPER,
            InventoryType.BLAST_FURNACE,
            // Lectern
            InventoryType.SMOKER
            // Loom
            // Cartography
            // Grindstone
            // Stonecutter
    );

    @Override
    public Inventory open(SmartInventory inv, Player player) {
        InventoryManager manager = inv.getManager();
        Inventory handle = Bukkit.createInventory(player, inv.getType(), inv.getTitle());

        fill(handle, manager.getContents(player).get());

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return SUPPORTED.contains(type);
    }

}
