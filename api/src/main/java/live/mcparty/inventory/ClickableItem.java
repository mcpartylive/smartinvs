package live.mcparty.inventory;

import java.util.function.Consumer;

public class ClickableItem<I, E> {

    private I item;
    private Consumer<E> consumer;

    public ClickableItem(I item, Consumer<E> consumer) {
        this.item = item;
        this.consumer = consumer;
    }

    public void run(E e) { consumer.accept(e); }

    public I getItem() { return item; }

}
