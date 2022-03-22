package live.mcparty.inventory.opener;

import live.mcparty.inventory.ClickableItem;
import live.mcparty.inventory.MinestomInventoryManager;
import live.mcparty.inventory.SmartInventory;
import live.mcparty.inventory.content.InventoryContents;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;

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

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return type.name().startsWith("CHEST_");
    }

    @Override
    public void fill(Inventory handle, InventoryContents contents) {
        ClickableItem[][] items = contents.all();

        for(int row = 0; row < items.length; row++) {
            for(int column = 0; column < items[row].length; column++) {
                if(items[row][column] != null) {
                    handle.setItemStack(9 * row + column, (ItemStack) items[row][column].getItem());
                    int finalRow = row;
                    int finalColumn = column;
                    handle.addInventoryCondition(((player, slot, clickType, inventoryConditionResult) -> {
                        if (slot == 9 * finalRow + finalColumn) {
                            inventoryConditionResult.setCancel(true);
                            items[finalRow][finalColumn].run();
                        }

                    }));
                }
            }
        }
    }

}