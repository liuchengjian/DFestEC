package lchj.latte.ec.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by XiMiTwo on 2017/10/19.
 */

public class SectionBean extends SectionEntity<SectionContentItemEntity>{

    private boolean mIsMore = false;
    private int mId = -1;

    public boolean isMore() {
        return mIsMore;
    }
    public void setIsMore(boolean mIsMore) {
        this.mIsMore = mIsMore;
    }
    public int getId() {
        return mId;
    }
    public void setId(int mId) {
        this.mId = mId;
    }

    public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }

    public SectionBean(boolean isHeader, String header) {

        super(isHeader, header);
    }


}
