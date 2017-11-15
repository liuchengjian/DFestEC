package lchj.latte.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.List;

import lchj.latte.app.Latte;
import lchj.latte.delegates.LatteDelegate;
import lchj.latte.ec.R;
import lchj.latte.ec.main.sort.content.ContentDelegate;
import lchj.latte.ui.recycler.ItemType;
import lchj.latte.ui.recycler.MultipleFields;
import lchj.latte.ui.recycler.MultipleItemEntity;
import lchj.latte.ui.recycler.MultipleRecyclerAdapter;
import lchj.latte.ui.recycler.MultipleViewHolder;
import me.yokeyword.fragmentation.SupportHelper;

/**
 * Created by XiMiTwo on 2017/10/19.
 */

public class SortRecyClerAdapter extends MultipleRecyclerAdapter {
    private final SortDelegate DELEGATE;
    //上一个的位置
    private int mPrePostion = 0;

    protected SortRecyClerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this. DELEGATE = delegate;
        //添加垂直布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);

    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ItemType.VERTICAL_MENU_LIST:
                final String text= entity.getField(MultipleFields.TEXT);
                final boolean isChecked = entity.getField(MultipleFields.TAG);
                final AppCompatTextView name = holder.getView(R.id.tv_vertical_list);
                final View line = holder.getView(R.id.view);
                final View itemView = holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentPostion = holder.getAdapterPosition();
                        if (mPrePostion != currentPostion){
                            //还原上个
                            getData().get(mPrePostion).setField(MultipleFields.TAG,false);
                            notifyItemChanged(mPrePostion);
                            //更新选中的item
                            entity.setField(MultipleFields.TAG,true);
                            notifyItemChanged(currentPostion);
                            mPrePostion = currentPostion;
                            final int contentId = getData().get(currentPostion).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });
                if (!isChecked){
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                }else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_vertical_list,text);
                break;
            default:
                break;
        }
    }
    private void showContent(int ContentId){
        final ContentDelegate delegate = ContentDelegate.newInstance(ContentId);
        switchContent(delegate);
    }
    private void switchContent(ContentDelegate delegate){
        final LatteDelegate contentDelegate =
                SupportHelper.findFragment(DELEGATE.getChildFragmentManager(), ContentDelegate.class);
        if (contentDelegate != null) {
            contentDelegate.getSupportDelegate().replaceFragment(delegate, false);
        }
    }
}
