package live.mcparty.inventory;

import java.util.function.Consumer;

public class ClickableItem<I, E> {

    private I item;
    private Runnable consumer;

    public ClickableItem(I item, Runnable consumer) {
        this.item = item;
        this.consumer = consumer;
    }

    public void run() {
        if (consumer != null)
            consumer.run();
    }

    public I getItem() { return item; }

}
