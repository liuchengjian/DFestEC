package lchj.latte.ec.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import lchj.latte.ui.recycler.DataConverter;
import lchj.latte.ui.recycler.MultipleFields;
import lchj.latte.ui.recycler.MultipleItemEntity;


/**
 * Created by XiMiTwo on 2017/10/20.
 */

public class ShopCartDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String desc = data.getString("desc");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final int count = data.getInteger("count");
            final double price = data.getDouble("price");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ShopCartItemType.SHOP_CART_ITEM)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(ShopCartFields.TITLE, title)
                    .setField(ShopCartFields.DESC, desc)
                    .setField(ShopCartFields.COUNT, count)
                    .setField(ShopCartFields.PRICE, price)
                    .setField(ShopCartFields.IS_SELECTED, false)//默认没有被点击，左侧按钮
                    .setField(ShopCartFields.POSITION, i)
                    .build();

            dataList.add(entity);
        }

        return  dataList;
    }
}
