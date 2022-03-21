package live.mcparty.inventory;

import live.mcparty.inventory.content.InventoryContents;
import live.mcparty.inventory.content.Pagination;
import live.mcparty.inventory.content.SlotIterator;
import live.mcparty.inventory.content.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PaperInventoryContents implements InventoryContents<Player, ItemStack> {

    private PaperSmartInventory inv;
    private UUID player;

    private PaperClickableItem[][] contents;

    private Pagination pagination = new Pagination.Impl();
    private Map<String, SlotIterator> iterators = new HashMap<>();
    private Map<String, Object> properties = new HashMap<>();

    public PaperInventoryContents(PaperSmartInventory inv, UUID player) {
        this.inv = inv;
        this.player = player;
        this.contents = new PaperClickableItem[inv.getRows()][inv.getColumns()];
    }

    @Override
    public PaperSmartInventory inventory() { return inv; }

    @Override
    public Pagination pagination() { return pagination; }

    @Override
    public Optional<SlotIterator> iterator(String id) {
        return Optional.ofNullable(this.iterators.get(id));
    }

    @Override
    public SlotIterator newIterator(String id, SlotIterator.Type type, int startRow, int startColumn) {
        SlotIterator iterator = new SlotIterator.Impl(this, inv,
            type, startRow, startColumn);

        this.iterators.put(id, iterator);
        return iterator;
    }

    @Override
    public SlotIterator newIterator(String id, SlotIterator.Type type, SlotPos startPos) {
        return newIterator(id, type, startPos.getRow(), startPos.getColumn());
    }

    @Override
    public SlotIterator newIterator(SlotIterator.Type type, int startRow, int startColumn) {
        return new SlotIterator.Impl(this, inv, type, startRow, startColumn);
    }

    @Override
    public SlotIterator newIterator(SlotIterator.Type type, SlotPos startPos) {
        return newIterator(type, startPos.getRow(), startPos.getColumn());
    }

    @Override
    public PaperClickableItem[][] all() { return contents; }

    @Override
    public Optional<SlotPos> firstEmpty() {
        for (int row = 0; row < contents.length; row++) {
            for(int column = 0; column < contents[0].length; column++) {
                if(!this.get(row, column).isPresent())
                    return Optional.of(new SlotPos(row, column));
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<ClickableItem> get(int row, int column) {
        if(row >= contents.length)
            return Optional.empty();
        if(column >= contents[row].length)
            return Optional.empty();

        return Optional.ofNullable(contents[row][column]);
    }

    @Override
    public Optional<ClickableItem> get(SlotPos slotPos) {
        return get(slotPos.getRow(), slotPos.getColumn());
    }

    @Override
    public InventoryContents set(int row, int column, ClickableItem item) {
        if(row >= contents.length)
            return this;
        if(column >= contents[row].length)
            return this;

        contents[row][column] = (PaperClickableItem) item;
        update(row, column, item != null ? (ItemStack) item.getItem() : null);
        return this;
    }

    @Override
    public InventoryContents set(SlotPos slotPos, ClickableItem item) {
        return set(slotPos.getRow(), slotPos.getColumn(), item);
    }

    @Override
    public InventoryContents<Player, ItemStack> add(ClickableItem item) {
        for(int row = 0; row < contents.length; row++) {
            for(int column = 0; column < contents[0].length; column++) {
                if(contents[row][column] == null) {
                    set(row, column, item);
                    return this;
                }
            }
        }

        return this;
    }

    @Override
    public InventoryContents fill(ClickableItem item) {
        for(int row = 0; row < contents.length; row++)
            for(int column = 0; column < contents[row].length; column++)
                set(row, column, item);

        return this;
    }

    @Override
    public InventoryContents fillRow(int row, ClickableItem item) {
        if(row >= contents.length)
            return this;

        for(int column = 0; column < contents[row].length; column++)
            set(row, column, item);

        return this;
    }

    @Override
    public InventoryContents fillColumn(int column, ClickableItem item) {
        for(int row = 0; row < contents.length; row++)
            set(row, column, item);

        return this;
    }

    @Override
    public InventoryContents fillBorders(ClickableItem item) {
        fillRect(0, 0, inv.getRows() - 1, inv.getColumns() - 1, item);
        return this;
    }

    @Override
    public InventoryContents fillRect(int fromRow, int fromColumn, int toRow, int toColumn, ClickableItem item) {
        for(int row = fromRow; row <= toRow; row++) {
            for(int column = fromColumn; column <= toColumn; column++) {
                if(row != fromRow && row != toRow && column != fromColumn && column != toColumn)
                    continue;

                set(row, column, item);
            }
        }

        return this;
    }

    @Override
    public InventoryContents fillRect(SlotPos fromPos, SlotPos toPos, ClickableItem item) {
        return fillRect(fromPos.getRow(), fromPos.getColumn(), toPos.getRow(), toPos.getColumn(), item);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T property(String name) {
        return (T) properties.get(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T property(String name, T def) {
        return properties.containsKey(name) ? (T) properties.get(name) : def;
    }

    @Override
    public PaperInventoryContents setProperty(String name, Object value) {
        properties.put(name, value);
        return this;
    }

    private void update(int row, int column, ItemStack item) {
        Player currentPlayer = Bukkit.getPlayer(player);
        if(!inv.getManager().getOpenedPlayers(inv).contains(currentPlayer))
            return;

        Inventory topInventory = currentPlayer.getOpenInventory().getTopInventory();
        topInventory.setItem(inv.getColumns() * row + column, item);
    }

}
