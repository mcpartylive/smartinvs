package live.mcparty.inventory.openers;

import com.google.common.base.Preconditions;
import live.mcparty.inventory.PaperInventoryManager;
import live.mcparty.inventory.SmartInventory;
import live.mcparty.inventory.content.InventoryContents;
import live.mcparty.inventory.opener.InventoryOpener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class ChestInventoryOpener implements InventoryOpener<Inventory, Player, InventoryType> {

    public Inventory open(SmartInventory inv, Player player) {
        Preconditions.checkArgument(inv.getColumns() == 9,
            "The column count for the chest inventory must be 9, found: %s.", inv.getColumns());
        Preconditions.checkArgument(inv.getRows() >= 1 && inv.getRows() <= 6,
            "The row count for the chest inventory must be between 1 and 6, found: %s", inv.getRows());

        PaperInventoryManager manager = (PaperInventoryManager) inv.getManager();
        Inventory handle = Bukkit.createInventory(player, inv.getRows() * inv.getColumns(), inv.getTitle());

        fill(handle, manager.getContents(player).get());

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return type == InventoryType.CHEST || type == InventoryType.ENDER_CHEST;
    }

    @Override
    public void fill(Inventory handle, InventoryContents contents) {

    }

}