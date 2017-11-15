package lchj.latte.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import lchj.latte.app.Latte;
import lchj.latte.botton.BottonItemDelegate;
import lchj.latte.ec.EcBottomDelegate;
import lchj.latte.ec.R;
import lchj.latte.ec.R2;
import lchj.latte.net.RestClient;
import lchj.latte.net.callback.ISuccess;
import lchj.latte.pay.FastPay;
import lchj.latte.pay.IAIPayResultListener;
import lchj.latte.ui.recycler.MultipleItemEntity;
import lchj.latte.utils.allurl.AllUrl;

/**
 * Created by XiMiTwo on 2017/10/26.
 */

public class ShopCartDelegate extends BottonItemDelegate implements ISuccess,ICartItemListener, IAIPayResultListener{
    private ShopCartadapter mAdapter = null;
    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;
    private double mTotalPrice = 0.00;
    private double mRemovePrice = 0.00;

    @BindView(R2.id.rv_top_shopcart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.itv_icon_cart)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat viewStubCompat = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;
    boolean isInflate = false;

    @OnClick(R2.id.itv_icon_cart)
    void onClickSelectAll() {
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            mIconSelectAll.setTextColor
                    (ContextCompat.getColor(getContext(), R.color.colorAccent));
            mIconSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新RecyclerView的显示状态
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }

    @OnClick(R2.id.tv_top_shopcart_remove)
    void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        //要删除的数据
        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartFields.IS_SELECTED);
            if (isSelected) {
                deleteEntities.add(entity);
            }
        }
        /**
         * 先获取要删除的position。然后通过mAdapter.remove把数据删除。注意这里不需要调用
         mAdapter.notifyItemRangeChanged.因为你在调用mAdapter.remove的时候，remove方法内部已经帮你调用了。
         然后通过for循环把删除item数据后面的item里面的position数据进行更新。
         也就是把后面的item的position数据值减一。
         */
        int size = deleteEntities.size();
        for(int i=0;i<size;i++){
            int DataCount = data.size();
            int CurrentPostion = deleteEntities.get(i).getField(ShopCartFields.POSITION);
            int currentCount = deleteEntities.get(i).getField(ShopCartFields.COUNT);
            Log.e("lchj", "currentCount"+String.valueOf(currentCount));
            double currentPrice = deleteEntities.get(i).getField(ShopCartFields.PRICE);
            if(CurrentPostion < DataCount){
                //获取当前删除的价格，把价格赋值给总价格
                mRemovePrice = currentCount * currentPrice;
                mTotalPrice = mTotalPrice-mRemovePrice;
                mTvTotalPrice.setText(String.valueOf(mTotalPrice));
                //此处可以上传到服务器
                mAdapter.remove(CurrentPostion);
                Log.e("lchj", "mRemovePrice"+String.valueOf(mRemovePrice));
                Log.e("lchj", "mTotalPrice"+String.valueOf(mTotalPrice));
                for (;CurrentPostion<DataCount-1;CurrentPostion++){
                    int rawItemPos = data.get(CurrentPostion).getField(ShopCartFields.POSITION);
                    data.get(CurrentPostion).setField(ShopCartFields.POSITION,rawItemPos-1);
                }
            }
        }

//        for (MultipleItemEntity entity : deleteEntities) {
//            int removePosition;
//            final int entityPosition = entity.getField(ShopCartFields.POSITION);
//            if (entityPosition > mCurrentCount - 1) {
//                removePosition = entityPosition - (mTotalCount - mCurrentCount);
//            } else {
//                removePosition = entityPosition;
//            }
//            if (removePosition <= mAdapter.getItemCount()) {
//                mAdapter.remove(removePosition);
//                mCurrentCount = mAdapter.getItemCount();
//                //更新数据
//                mAdapter.notifyItemRangeChanged(removePosition, mAdapter.getItemCount());
//            }
//        }
        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shopcart_clear)
    void onClickClear() {
        mAdapter.getData().clear();
        mTotalPrice = 0.0;
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        mAdapter.notifyDataSetChanged();
        checkItemCount();

    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay() {
//        FastPay.create(this).beginPayDialog();
//        createOrder();
    }

//    //创建订单，注意，和支付是没有关系的
    private void createOrder() {
        final String orderUrl = "你的生成订单的API";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        //加入你的参数
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付
//                        LatteLogger.d("ORDER", response);
                        final int orderId = JSON.parseObject(response).getInteger("result");
                        FastPay.create(ShopCartDelegate.this)
                                .setPayResultListener( ShopCartDelegate.this)
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();

    }

    @SuppressWarnings("RestrictedApi")
    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        /**
         * 点击多次情况出现解决ViewStub must have a non-null ViewGroup viewParent
         * 以下避免ViewStub重复填充问题
         */
        viewStubCompat.setOnInflateListener(new ViewStubCompat.OnInflateListener() {
            @Override
            public void onInflate(ViewStubCompat stub, View inflated) {
                isInflate = true;
            }
        });
        if (count == 0) {
            if(!isInflate){
                final View stubView = viewStubCompat.inflate();

                final AppCompatTextView tvToBuy =
                        (AppCompatTextView) stubView.findViewById(R.id.tv_go_buy);
                tvToBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "你该购物啦！", Toast.LENGTH_SHORT).show();
                        //切换到首页
                        final int indexTab = 0;
                        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
                        final BottonItemDelegate indexDelegate = ecBottomDelegate.getItemDelegates().get(indexTab);
                        ecBottomDelegate
                                .getSupportDelegate()
                                .showHideFragment(indexDelegate, ShopCartDelegate.this);
                        ecBottomDelegate.changeColor(indexTab);

                    }
                });
            }
                mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mIconSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url(AllUrl.SHOP_CART)
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter()
                        .setJsonData(response)
                        .convert();
        mAdapter = new ShopCartadapter(data);
        mAdapter.setCartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        checkItemCount();
    }
    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
