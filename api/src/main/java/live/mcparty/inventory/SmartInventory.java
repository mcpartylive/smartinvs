package live.mcparty.inventory;

import live.mcparty.inventory.content.InventoryProvider;
import net.kyori.adventure.text.Component;

import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class SmartInventory<I, P, M> {

    protected String id;
    protected Component title;

    protected int rows, columns;
    protected boolean closeable;

    protected InventoryProvider provider;
    protected SmartInventory parent;

    protected M manager;

    public SmartInventory(M manager) {
        this.manager = manager;
    }

    public I open(P player) { return open(player, 0); }
    public abstract I open(P player, int page);

    @SuppressWarnings("unchecked")
    public abstract void close(P player);

    public String getId() { return id; }

    public Component getTitle() { return title; }

    public int getRows() { return rows; }
    public int getColumns() { return columns; }

    public boolean isCloseable() { return closeable; }
    public void setCloseable(boolean closeable) { this.closeable = closeable; }

    public InventoryProvider getProvider() { return provider; }
    public Optional<SmartInventory> getParent() { return Optional.ofNullable(parent); }

    public M getManager() { return manager; }

}
