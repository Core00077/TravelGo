package cn.corechan.travel.json;

import java.util.List;

public class ListObject extends Status {
    private List<?> items;

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }
}
