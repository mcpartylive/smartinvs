package live.mcparty.inventory.opener;

import live.mcparty.inventory.MinestomInventoryManager;
import live.mcparty.inventory.SmartInventory;
import live.mcparty.inventory.content.InventoryContents;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

public class ChestInventoryOpener implements InventoryOpener<Inventory, Player, InventoryType> {

    public Inventory open(SmartInventory inv, Player player) {
        MinestomInventoryManager manager = (MinestomInventoryManager) inv.getManager();
        InventoryType type = InventoryType.CHEST_6_ROW;
        switch (inv.getRows() * inv.getColumns()) {
            case 9 -> type = InventoryType.CHEST_1_ROW;
            case 18 -> type = InventoryType.CHEST_2_ROW;
            case 27 -> type = InventoryType.CHEST_3_ROW;
            case 36 -> type = InventoryType.CHEST_4_ROW;
            case 45 -> type = InventoryType.CHEST_5_ROW;
        }
        Inventory handle = new Inventory(type, inv.getTitle());

        fill(handle, manager.getContents(player).get());

        player.sendMessage("beep boop");
        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return type.name().startsWith("CHEST_");
    }

    @Override
    public void fill(Inventory handle, InventoryContents contents) {

    }

}