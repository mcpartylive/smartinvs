package live.mcparty.inventory.opener;

import live.mcparty.inventory.SmartInventory;
import live.mcparty.inventory.content.InventoryContents;

public interface InventoryOpener<I, P, T> {

    abstract I open(SmartInventory inv, P player);

    boolean supports(T type);

    public abstract void fill(I handle, InventoryContents contents);

}
