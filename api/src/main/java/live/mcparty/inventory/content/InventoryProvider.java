package live.mcparty.inventory.content;

public interface InventoryProvider<P, C> {

    void init(P player, C contents);
    default void update(P player, C contents) {}

}
