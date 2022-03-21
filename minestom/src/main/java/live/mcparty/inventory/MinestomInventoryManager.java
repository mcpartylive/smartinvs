package live.mcparty.inventory;

import live.mcparty.inventory.opener.ChestInventoryOpener;
import live.mcparty.inventory.opener.InventoryOpener;
import lombok.Getter;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;

import java.util.*;

@Getter
public class MinestomInventoryManager {

    private Map<UUID, MinestomSmartInventory> inventories;
    private Map<UUID, MinestomInventoryContents> contents;
    private List<InventoryOpener> openers;

    public MinestomInventoryManager() {
        this.inventories = new HashMap<>();
        this.contents = new HashMap<>();
    }

    public void init() {
        this.openers = Arrays.asList(new ChestInventoryOpener());
    }

    public Optional<InventoryOpener> findOpener(InventoryType type) {
        Optional<InventoryOpener> opInv = this.openers.stream()
            .filter(opener -> opener.supports(type))
            .findAny();

        return opInv;
    }

    public Optional<MinestomSmartInventory> getInventory(Player player) {
        return Optional.ofNullable(this.inventories.get(player.getUuid()));
    }

    protected void setInventory(Player player, MinestomSmartInventory inventory) {
        if (inventory == null) this.inventories.remove(player.getUuid());
        else this.inventories.put(player.getUuid(), inventory);
    }

    public Optional<MinestomInventoryContents> getContents(Player player) {
        return Optional.ofNullable(this.contents.get(player.getUuid()));
    }

    protected void setContents(Player player, MinestomInventoryContents contents) {
        if (contents == null) this.contents.remove(player.getUuid());
        else this.contents.put(player.getUuid(), contents);
    }

    public void handleInventoryOpenError(MinestomSmartInventory inventory, Player player, Exception exception) {
        inventory.close(player);
    }

    public void handleInventoryUpdateError(MinestomSmartInventory inventory, Player player, Exception exception) {
        inventory.close(player);
    }

}