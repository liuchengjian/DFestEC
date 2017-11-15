package lchj.latte.botton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import lchj.latte.delegates.LatteDelegate;


/**
 * Created by XiMiTwo on 2017/10/17.
 * 每个界面
 */

public abstract class BottonItemDelegate extends LatteDelegate implements View.OnKeyListener{
    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

//    @Override
//    public void onResume() {
//        super.onResume();
//        final View rootView = getView();
//        if(rootView != null){
//            rootView.setFocusableInTouchMode(true);
//            rootView.requestFocus();
//            rootView.setOnKeyListener(this);
//        }
//    }
    //解决双击失灵的方法，把onResume()的东西放到onActivityCreated里面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View rootView = getView();
        if(rootView != null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ){
            if((System.currentTimeMillis()-mExitTime)> EXIT_TIME){
                Toast.makeText(getContext(),"双击退出",Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            }else{
                _mActivity.finish();
                if(mExitTime!=0){
                    mExitTime = 0;
                }
            }
            return true;
        }
        return false;
    }
}
