package live.mcparty.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class PaperClickableItem extends ClickableItem<ItemStack, InventoryClickEvent> {

    private ItemStack item;
    private Runnable consumer;

    private PaperClickableItem(ItemStack item, Runnable consumer) {
        super(item, consumer);
    }

    public static PaperClickableItem empty(ItemStack item) {
        return of(item, null);
    }

    public static PaperClickableItem of(ItemStack item, Runnable consumer) {
        return new PaperClickableItem(item, consumer);
    }

    public void run() { consumer.run(); }

    public ItemStack getItem() { return item; }

}
