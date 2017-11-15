package lchj.latte.ec.main.index.search;

import android.support.v7.widget.AppCompatTextView;


import java.util.List;

import lchj.latte.ec.R;
import lchj.latte.ui.recycler.MultipleFields;
import lchj.latte.ui.recycler.MultipleItemEntity;
import lchj.latte.ui.recycler.MultipleRecyclerAdapter;
import lchj.latte.ui.recycler.MultipleViewHolder;

/**
 * Created by 傅令杰
 */

public class SearchAdapter extends MultipleRecyclerAdapter {

    protected SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()) {
            case SearchItemType.ITEM_SEARCH:
                final AppCompatTextView tvSearchItem = holder.getView(R.id.tv_search_item);
                final String history = entity.getField(MultipleFields.TEXT);
                tvSearchItem.setText(history);
                break;
            default:
                break;
        }
    }
}
