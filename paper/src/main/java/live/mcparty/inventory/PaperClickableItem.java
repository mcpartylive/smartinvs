package live.mcparty.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class PaperClickableItem extends ClickableItem<ItemStack, InventoryClickEvent> {

    private ItemStack item;
    private Consumer<InventoryClickEvent> consumer;

    private PaperClickableItem(ItemStack item, Consumer<InventoryClickEvent> consumer) {
        super(item, consumer);
    }

    public static PaperClickableItem empty(ItemStack item) {
        return of(item, e -> {});
    }

    public static PaperClickableItem of(ItemStack item, Consumer<InventoryClickEvent> consumer) {
        return new PaperClickableItem(item, consumer);
    }

    public void run(InventoryClickEvent e) { consumer.accept(e); }

    public ItemStack getItem() { return item; }

}
