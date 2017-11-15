package lchj.latte.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import lchj.latte.app.Latte;
import lchj.latte.ec.R;
import lchj.latte.net.RestClient;
import lchj.latte.net.callback.ISuccess;
import lchj.latte.ui.recycler.MultipleFields;
import lchj.latte.ui.recycler.MultipleItemEntity;
import lchj.latte.ui.recycler.MultipleRecyclerAdapter;
import lchj.latte.ui.recycler.MultipleViewHolder;
import lchj.latte.utils.allurl.AllUrl;

/**
 * Created by XiMiTwo on 2017/10/20.
 */

public class ShopCartadapter extends MultipleRecyclerAdapter {
    private boolean mIsSelectedAll = false;

    private ICartItemListener mCartItemListener = null;
    private double mTotalPrice = 0.00;


    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();
    protected ShopCartadapter(List<MultipleItemEntity> data) {
        super(data);
        //初始化总价
        for (MultipleItemEntity entity : data) {
            final double price = entity.getField(ShopCartFields.PRICE);
            final int count = entity.getField(ShopCartFields.COUNT);
            final double total = price * count;
            mTotalPrice = mTotalPrice + total;
        }
        //添加购物车item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }
    public void setCartItemListener(ICartItemListener listener){
        this.mCartItemListener = listener;
    }
    public double getTotalPrice(){
        return mTotalPrice;
    }


    public void setIsSelectedAll(boolean isSelectedAll){
        this.mIsSelectedAll = isSelectedAll;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //先取出所有值
                final int id = entity.getField(MultipleFields.ID);
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(ShopCartFields.TITLE);
                final String desc = entity.getField(ShopCartFields.DESC);
                final int count = entity.getField(ShopCartFields.COUNT);
                final double price = entity.getField(ShopCartFields.PRICE);

                //取出所以控件
                final AppCompatImageView imgThumb = holder.getView(R.id.iv_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_shop_cart_title);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_shop_cart_price);
                final IconTextView iconMinus = holder.getView(R.id.iocn_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.iocn_item_plus);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_shop_cart_count);
                final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart);

                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imgThumb);

                //在左侧勾勾渲染之前改变全选与否状态
                entity.setField(ShopCartFields.IS_SELECTED, mIsSelectedAll);
                final boolean isSelected = entity.getField(ShopCartFields.IS_SELECTED);

                //根据数据状态显示左侧勾勾
                if(isSelected){
                    iconIsSelected.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.colorAccent));
                }else{
                    iconIsSelected.setTextColor(Color.GRAY);
                }
            //添加左侧勾勾点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = entity.getField(ShopCartFields.IS_SELECTED);
                        if (currentSelected) {
                            iconIsSelected.setTextColor(Color.GRAY);
                            entity.setField(ShopCartFields.IS_SELECTED, false);
                        } else {
                            //设置为被点击状态
                            iconIsSelected.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.colorAccent));
                            entity.setField(ShopCartFields.IS_SELECTED, true);
                        }
                    }
                });

                //添加加减号键
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentCount= entity.getField(ShopCartFields.COUNT);
                        if(Integer.parseInt(tvCount.getText().toString()) > 1 ){
                            RestClient.builder()
                                    .url(AllUrl.SHOP_CART_COUNT)
                                    .loader(mContext)
                                    .params("count",currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum--;
                                            tvCount.setText(String.valueOf(countNum));

                                            if(mCartItemListener !=null){
                                                mTotalPrice = mTotalPrice -price;
                                                final double itemTotal = countNum * price;
                                                mCartItemListener.onItemClick(itemTotal);
                                            }
                                        }
                                    })
                                    .build()
                                    .post();
                        }
                    }
                });
                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentCount = entity.getField(ShopCartFields.COUNT);
                        RestClient.builder()
                                .url(AllUrl.SHOP_CART_COUNT)
                                .loader(mContext)
                                .params("count", currentCount)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        int countNum = Integer.parseInt(tvCount.getText().toString());
                                        countNum++;
                                        tvCount.setText(String.valueOf(countNum));
                                        if(mCartItemListener !=null){
                                            mTotalPrice = mTotalPrice +price;
                                            final double itemTotal = countNum * price;
                                            mCartItemListener.onItemClick(itemTotal);
                                        }
                                    }
                                })
                                .build()
                                .post();
                    }
                });


                break;
            default:
                break;
        }
    }
}
