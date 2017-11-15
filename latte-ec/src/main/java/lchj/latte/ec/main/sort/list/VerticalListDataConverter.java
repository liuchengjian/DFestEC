package lchj.latte.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import lchj.latte.ui.recycler.DataConverter;
import lchj.latte.ui.recycler.ItemType;
import lchj.latte.ui.recycler.MultipleFields;
import lchj.latte.ui.recycler.MultipleItemEntity;


/**
 * Created by XiMiTwo on 2017/10/19.
 */

public class VerticalListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> datalist = new ArrayList<>();
        final JSONArray dataArray = JSON
                .parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");
        final int size =dataArray.size();
        for (int i=0;i<size;i++){
           final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.TAG, false)
                    .build();

            datalist.add(entity);
            //设置第一个被选中
            datalist.get(0).setField(MultipleFields.TAG, true);
        }

        return datalist;
    }
}
