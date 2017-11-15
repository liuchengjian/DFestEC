package lchj.latte.ec;

import android.graphics.Color;


import java.util.LinkedHashMap;

import lchj.latte.botton.BaseBottonDelegate;
import lchj.latte.botton.BottonItemDelegate;
import lchj.latte.botton.BottonTabBean;
import lchj.latte.botton.ItemBuilder;
import lchj.latte.ec.main.cart.ShopCartDelegate;
import lchj.latte.ec.main.discover.DiscoverDelegate;
import lchj.latte.ec.main.index.IndexDelegate;
import lchj.latte.ec.main.personal.PersonalDelegate;
import lchj.latte.ec.main.sort.list.SortDelegate;

/**
 *
 */

public class EcBottomDelegate extends BaseBottonDelegate{

    @Override
    public LinkedHashMap<BottonTabBean, BottonItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottonTabBean, BottonItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottonTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottonTabBean("{fa-sort}", "分类"), new SortDelegate());
        items.put(new BottonTabBean("{fa-compass}", "发现"), new DiscoverDelegate());
        items.put(new BottonTabBean("{fa-shopping-cart}", "购物车"), new ShopCartDelegate());
        items.put(new BottonTabBean("{fa-user}", "我的"), new PersonalDelegate());
//        items.put(new BottonTabBean(R.mipmap.ic_launcher, "主页"), new IndexDelegate());
//        items.put(new BottonTabBean(R.mipmap.ic_launcher, "分类"), new SortDelegate());
//        items.put(new BottonTabBean(R.mipmap.ic_launcher, "发现"), new DiscoverDelegate());
//        items.put(new BottonTabBean(R.mipmap.ic_launcher, "购物车"), new ShopCartDelegate());
//        items.put(new BottonTabBean(R.mipmap.ic_launcher, "我的"), new PersonalDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }



}
