package lchj.latte.botton;

import java.util.LinkedHashMap;

/**
 * Created by XiMiTwo on 2017/10/17.
 */

public final class ItemBuilder {
    private final LinkedHashMap<BottonTabBean,BottonItemDelegate>ITEMS = new LinkedHashMap<>();
    static ItemBuilder builder(){
        return new ItemBuilder();
    }
    public final ItemBuilder addItem(BottonTabBean bean,BottonItemDelegate delegate){
        ITEMS.put(bean,delegate);
        return this;
    }
    public final ItemBuilder addItems(LinkedHashMap<BottonTabBean,BottonItemDelegate> items){
        ITEMS.putAll(items);
        return this;
    }
    public final LinkedHashMap<BottonTabBean,BottonItemDelegate> build(){
        return ITEMS;
    }


}
