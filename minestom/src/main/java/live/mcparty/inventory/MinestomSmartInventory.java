package live.mcparty.inventory;

import live.mcparty.inventory.contents.MinestomInventoryProvider;
import live.mcparty.inventory.opener.ChestInventoryOpener;
import live.mcparty.inventory.opener.InventoryOpener;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

import java.util.Optional;

@Getter
@Setter
public class MinestomSmartInventory extends SmartInventory<Inventory, Player, MinestomInventoryManager> {
    private InventoryType type;

    public MinestomSmartInventory(MinestomInventoryManager manager) {
        super(manager);
    }

    @Override
    public Inventory open(Player player, int page) {
        Optional<MinestomSmartInventory> oldInv = this.getManager().getInventory(player);

        oldInv.ifPresent(inv -> {
            this.getManager().setInventory(player, null);
        });

        MinestomInventoryContents contents = new MinestomInventoryContents(this, player.getUuid());
        contents.pagination().page(page);

        this.getManager().setContents(player, contents);

        try {
            this.getProvider().init(player, contents);

            // If the current inventory has been closed or replaced within the init method, returns
//            if (!this.getManager().getContents(player).equals(Optional.of(contents))) {
//                return null;
//            }

            Inventory handle = new ChestInventoryOpener().open(this, player);

            this.getManager().setInventory(player, this);
            return handle;
        } catch (Exception e) {
            this.getManager().handleInventoryOpenError(this, player, e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close(Player player) {

    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String id = "unknown";
        private Component title = Component.empty();
        private InventoryType type = InventoryType.CHEST_3_ROW;
        private int rows = 6, columns = 9;
        private MinestomInventoryProvider provider;
        private boolean closeable = true;

        private MinestomInventoryManager manager;
        private SmartInventory parent;

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

        public Builder provider(MinestomInventoryProvider provider) {
            this.provider = provider;
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

        public Builder parent(SmartInventory parent) {
            this.parent = parent;
            return this;
        }

        public Builder manager(MinestomInventoryManager manager) {
            this.manager = manager;
            return this;
        }

        public MinestomSmartInventory build() {
            MinestomInventoryManager manager = this.manager != null ? this.manager : null;

            if(manager == null)
                throw new IllegalStateException("The manager of the SmartInventory.Builder must be set, "
                    + "or the SmartInvs should be loaded as a plugin.");

            MinestomSmartInventory inv = new MinestomSmartInventory(manager);
            inv.id = this.id;
            inv.title = this.title;
            inv.type = this.type;
            inv.rows = this.rows;
            inv.columns = this.columns;
            inv.closeable = this.closeable;
            inv.parent = this.parent;
            inv.provider = this.provider;

            return inv;
        }
    }

}
