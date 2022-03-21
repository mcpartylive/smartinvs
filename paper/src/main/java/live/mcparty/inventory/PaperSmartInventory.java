package live.mcparty.inventory;

import live.mcparty.inventory.content.InventoryProvider;
import live.mcparty.inventory.opener.InventoryOpener;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class PaperSmartInventory extends SmartInventory<Inventory, Player, PaperInventoryManager> {

    private InventoryType type;
    private List<InventoryListener<? extends Event>> listeners;

    public PaperSmartInventory(PaperInventoryManager manager) {
        super(manager);
    }

    public Inventory open(Player player) { return open(player, 0); }
    public Inventory open(Player player, int page) {
        Optional<PaperSmartInventory> oldInv = this.getManager().getInventory(player);

        oldInv.ifPresent(inv -> {
            inv.getListeners().stream()
                    .filter(listener -> listener.getType() == InventoryCloseEvent.class)
                    .forEach(listener -> ((InventoryListener<InventoryCloseEvent>) listener)
                            .accept(new InventoryCloseEvent(player.getOpenInventory())));

            this.getManager().setInventory(player, null);
        });

        PaperInventoryContents contents = new PaperInventoryContents(this, player.getUniqueId());
        contents.pagination().page(page);

        this.getManager().setContents(player, contents);

        try {
            this.getProvider().init(player, contents);

            // If the current inventory has been closed or replaced within the init method, returns
            if (!this.getManager().getContents(player).equals(Optional.of(contents))) {
                return null;
            }

            InventoryOpener opener = this.getManager().findOpener(type)
                    .orElseThrow(() -> new IllegalStateException("No opener found for the inventory type " + type.name()));
            Inventory handle = (Inventory) opener.open(this, player);

            this.getManager().setInventory(player, this);

            return handle;
        } catch (Exception e) {
            this.getManager().handleInventoryOpenError(this, player, e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void close(Player player) {
        getListeners().stream()
                .filter(listener -> listener.getType() == InventoryCloseEvent.class)
                .forEach(listener -> ((InventoryListener<InventoryCloseEvent>) listener)
                        .accept(new InventoryCloseEvent(player.getOpenInventory())));

        this.getManager().setInventory(player, null);
        player.closeInventory();

        this.getManager().setContents(player, null);
    }

    List<InventoryListener<? extends Event>> getListeners() { return listeners; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String id = "unknown";
        private Component title = Component.empty();
        private InventoryType type = InventoryType.CHEST;
        private int rows = 6, columns = 9;
        private boolean closeable = true;

        private PaperInventoryManager manager;
        private InventoryProvider provider;
        private PaperSmartInventory parent;

        private List<InventoryListener<? extends Event>> listeners = new ArrayList<>();

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(Component title) {
            this.title = title;
            return this;
        }

        public Builder type(InventoryType type) {
            this.type = type;
            return this;
        }

        public Builder size(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
            return this;
        }

        public Builder closeable(boolean closeable) {
            this.closeable = closeable;
            return this;
        }

        public Builder provider(InventoryProvider provider) {
            this.provider = provider;
            return this;
        }

        public Builder parent(PaperSmartInventory parent) {
            this.parent = parent;
            return this;
        }

        public Builder listener(InventoryListener<? extends Event> listener) {
            this.listeners.add(listener);
            return this;
        }

        public Builder manager(PaperInventoryManager manager) {
            this.manager = manager;
            return this;
        }

        public PaperSmartInventory build() {
            if(this.provider == null)
                throw new IllegalStateException("The provider of the SmartInventory.Builder must be set.");

            PaperInventoryManager manager = this.manager != null ? this.manager : SmartInvsPlugin.manager();

            if(manager == null)
                throw new IllegalStateException("The manager of the SmartInventory.Builder must be set, "
                        + "or the SmartInvs should be loaded as a plugin.");

            PaperSmartInventory inv = new PaperSmartInventory(manager);
            inv.id = this.id;
            inv.title = this.title;
            inv.type = this.type;
            inv.rows = this.rows;
            inv.columns = this.columns;
            inv.closeable = this.closeable;
            inv.provider = this.provider;
            inv.parent = this.parent;
            inv.listeners = this.listeners;

            return inv;
        }
    }

}
