package live.mcparty.inventory.contents;

import live.mcparty.inventory.ClickableItem;
import live.mcparty.inventory.content.Pagination;
import live.mcparty.inventory.content.SlotIterator;

import java.util.Arrays;

public class MinestomPagination implements Pagination {

    private int currentPage;

    private ClickableItem[] items = new MinestomClickableItem[0];
    private int itemsPerPage = 5;

    @Override
    public ClickableItem[] getPageItems() {
        return Arrays.copyOfRange(items,
            currentPage * itemsPerPage,
            (currentPage + 1) * itemsPerPage);
    }

    @Override
    public int getPage() {
        return this.currentPage;
    }

    @Override
    public MinestomPagination page(int page) {
        this.currentPage = page;
        return this;
    }

    @Override
    public boolean isFirst() {
        return this.currentPage == 0;
    }

    @Override
    public boolean isLast() {
        int pageCount = (int) Math.ceil((double) this.items.length / this.itemsPerPage);
        return this.currentPage >= pageCount - 1;
    }

    @Override
    public MinestomPagination first() {
        this.currentPage = 0;
        return this;
    }

    @Override
    public MinestomPagination previous() {
        if(!isFirst())
            this.currentPage--;

        return this;
    }

    @Override
    public MinestomPagination next() {
        if(!isLast())
            this.currentPage++;

        return this;
    }

    @Override
    public MinestomPagination last() {
        this.currentPage = this.items.length / this.itemsPerPage;
        return this;
    }

    @Override
    public MinestomPagination addToIterator(SlotIterator iterator) {
        for(ClickableItem item : getPageItems()) {
            iterator.next().set(item);

            if(iterator.ended())
                break;
        }

        return this;
    }

    @Override
    public MinestomPagination setItems(ClickableItem... items) {
        this.items = items;
        return this;
    }

    @Override
    public MinestomPagination setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        return this;
    }

}