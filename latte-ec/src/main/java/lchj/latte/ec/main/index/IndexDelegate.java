package lchj.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;
import lchj.latte.botton.BottonItemDelegate;
import lchj.latte.ec.EcBottomDelegate;
import lchj.latte.ec.R;
import lchj.latte.ec.R2;
import lchj.latte.ec.main.index.search.SearchDelegate;
import lchj.latte.ui.recycler.BaseDecoration;
import lchj.latte.ui.refresh.RefreshHandler;
import lchj.latte.utils.allurl.AllUrl;


/**
 * Created by XiMiTwo on 2017/10/17.
 */

public class IndexDelegate extends BottonItemDelegate implements View.OnFocusChangeListener{

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search)
    AppCompatEditText mSearchView = null;

    private RefreshHandler mRefreshHandler = null;
    //二维码
    @OnClick(R2.id.icon_index_scan)
    void onClickScan(){

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout,mRecyclerView,new IndexDataConverter());
//        CallbackManager.getInstance()
//                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
//                    @Override
//                    public void executeCallback(@Nullable String args) {
//                        Toast.makeText(getContext(), "得到的二维码是" + args, Toast.LENGTH_LONG).show();
//                    }
//                });
        mSearchView.setOnFocusChangeListener(this);
    }

    private void initRefreshLayout(){
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshLayout.setProgressViewOffset(true,120,200);
    }
    private void initRecycleView(){
        final GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(BaseDecoration.create(
                ContextCompat.getColor(getContext(), R.color.app_background),5));
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecycleView();
        mRefreshHandler.firstPage(AllUrl.INDEX_URL);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            getParentDelegate().getSupportDelegate().start(new SearchDelegate());
        }
    }
}
