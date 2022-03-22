package live.mcparty.inventory.contents;

import live.mcparty.inventory.ClickableItem;
import net.minestom.server.event.inventory.InventoryClickEvent;
import net.minestom.server.item.ItemStack;

import java.util.function.Consumer;

public class MinestomClickableItem extends ClickableItem<ItemStack, InventoryClickEvent> {
    public MinestomClickableItem(ItemStack item, Runnable runnable) {
        super(item, runnable);
    }
}
