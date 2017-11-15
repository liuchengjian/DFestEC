package lchj.latte.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import lchj.latte.botton.BottonItemDelegate;
import lchj.latte.ec.R;
import lchj.latte.ec.main.sort.content.ContentDelegate;

/**
 * Created by XiMiTwo on 2017/10/19.
 */

public class SortDelegate extends BottonItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate delegate = new VerticalListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container,delegate);
//        replaceFragment(R.id.rv_content, ContentDelegate.newInstance(1),false);
        //设置右侧第一个分类显示，默认显示分类一
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1));

    }
}
