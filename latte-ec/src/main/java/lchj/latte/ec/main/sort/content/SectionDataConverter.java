package lchj.latte.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiMiTwo on 2017/10/19.
 */

public class SectionDataConverter {
    final List<SectionBean>convert(String json){
        final List<SectionBean> datalist = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");
        final int size = dataArray.size();
        for(int i = 0;i<size;i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final  int id = data.getInteger("id");
            final String title= data.getString("section");
            //添加title
            final SectionBean sectionBean = new SectionBean(true,title);
            sectionBean.setId(id);
            sectionBean.setIsMore(true);
            datalist.add(sectionBean);

            final JSONArray goods = data.getJSONArray("goods");
            //商品循环
            final int goodsize= goods.size();
            for(int j = 0;j<goodsize;j++){
                final JSONObject contentItem = goods.getJSONObject(j);
                final int goodsId = contentItem.getInteger("goods_id");
                final String goodsName = contentItem.getString("goods_name");
                final String goodsThumb = contentItem.getString("goods_thumb");
                //获取内容
                final SectionContentItemEntity entity = new SectionContentItemEntity();
                entity.setGoodsId(goodsId);
                entity.setGoodsName(goodsName);
                entity.setGoodsThumb(goodsThumb);
                //添加内容
                datalist.add(new SectionBean(entity));
            }
            //商品内容循环结束
        }
        return  datalist;
    }
}
