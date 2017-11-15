package lchj.latte.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import lchj.latte.delegates.LatteDelegate;
import lchj.latte.ec.R;
import lchj.latte.ec.R2;
import lchj.latte.net.RestClient;
import lchj.latte.net.callback.ISuccess;
import lchj.latte.utils.allurl.AllUrl;

/**
 * Created by XiMiTwo on 2017/10/19.
 */

public class ContentDelegate extends LatteDelegate {

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;
    @BindView(R2.id.rv_content)
    RecyclerView mRecyclerView = null;

    private List<SectionBean>mData = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if(args !=null){
            mContentId = args .getInt(ARG_CONTENT_ID);
        }
    }

    public static ContentDelegate newInstance(int contentId){
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID,contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_content;
    }

    private void initData(){
        RestClient.builder()
                .url(AllUrl.SORT_CONTENT_LIST+ mContentId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mData = new SectionDataConverter().convert(response);
                        final SectionAdapter adapter = new SectionAdapter(R.layout.section_item_content, R.layout.section_item_header,mData);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //瀑布流
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        initData();
    }
}
