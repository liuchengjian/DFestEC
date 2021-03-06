package lchj.latte.ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import lchj.latte.ec.R;

/**
 * Created by XiMiTwo on 2017/10/19.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean,BaseViewHolder> {
    private static final RequestOptions OPTIONS= new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        //头数据转换
        helper.setText(R.id.tv_header,item.header);
        helper.setVisible(R.id.tv_more,item.isMore());
        helper.addOnClickListener(R.id.tv_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        // item.t返回类型
        final String thumb = item.t.getGoodsThumb();
        final String name = item.t.getGoodsName();
        final int goodsId = item.t.getGoodsId();
        final SectionContentItemEntity entity = item.t;
        helper.setText(R.id.tv,name);
        final AppCompatImageView goodsImage = helper.getView(R.id.iv);
        Glide.with(mContext).load(thumb)
                .into(goodsImage);
    }

}