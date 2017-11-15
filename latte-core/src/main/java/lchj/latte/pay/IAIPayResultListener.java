package lchj.latte.pay;

/**
 * Created by XiMiTwo on 2017/10/27.
 */

public interface IAIPayResultListener {
    //支护成功
    void onPaySuccess();
    //支护中
    void onPaying();
    //支护失败
    void onPayFail();
    //支护取消
    void onPayCancel();
    //支护网络连接出现异常
    void onPayConnectError();
}
