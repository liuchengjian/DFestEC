package lchj.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

import lchj.latte.app.AccountManager;
import lchj.latte.app.IUserChecker;
import lchj.latte.delegates.LatteDelegate;
import lchj.latte.ec.R;
import lchj.latte.ui.launcher.ILauncherListener;
import lchj.latte.ui.launcher.LauncherHolderCreator;
import lchj.latte.ui.launcher.OnLauncherFinishTag;
import lchj.latte.ui.launcher.ScrollLauncherTag;
import lchj.latte.utils.storage.LattePreference;

/**
 * Created by
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner = null;
    //这里不能用static。会导致轮播图点点不销毁，会叠加
    private final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ILauncherListener mILauncherListener = null;

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                //滑到最后一个跳转界面
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        if(position == INTEGERS.size()-1){
                            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
                            //检查用户是否已经登录
                            AccountManager.checkAccount(new IUserChecker() {
                                @Override
                                public void onSignIn() {
                                    if (mILauncherListener != null) {
                                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                                    }
                                }

                                @Override
                                public void onNotSignIn() {
                                    if (mILauncherListener != null) {
                                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onPageSelected(int position) {

                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                })
                .setCanLoop(false);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        getSupportDelegate().pop();
        //如果点击的是最后一个
        if (position == INTEGERS.size() - 1) {
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //检查用户是否已经登录
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }


}
