package live.mcparty.inventory;

import net.minestom.server.event.inventory.InventoryItemChangeEvent;
import net.minestom.server.item.ItemStack;

import java.util.function.Consumer;

public class MinestomClickableItem extends ClickableItem<ItemStack, InventoryItemChangeEvent> {
    public MinestomClickableItem(ItemStack item, Consumer<InventoryItemChangeEvent> consumer) {
        super(item, consumer);
    }
}
