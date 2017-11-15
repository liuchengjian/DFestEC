package lchj.dfestec;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;

import lchj.latte.app.Latte;
import lchj.latte.ec.database.DatabaseManager;
import lchj.latte.ec.icon.FontEcModule;
import lchj.latte.net.interceptors.DebugInterceptor;


/**
 *
 */
public class ExampleApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())

                .withApiHost("http://192.168.58.134/").withLoaderDelayed(1000)
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withJavascriptInterface("latte")
//                .withWeChatAppId("你的微信AppKey")
//                .withWeChatAppSecret("你的微信AppSecret")
//                .withWebEvent("test", new TestEvent())
//                .withWebEvent("share", new ShareEvent())
                .configure();
//        initStetho();
        //初始化数据表
        DatabaseManager.getInstance().init(this);
//
//        //开启极光推送
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//
//        CallbackManager.getInstance()
//                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
//                    @Override
//                    public void executeCallback(@Nullable Object args) {
//                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())) {
//                            //开启极光推送
//                            JPushInterface.setDebugMode(true);
//                            JPushInterface.init(Latte.getApplicationContext());
//                        }
//                    }
//                })
//                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
//                    @Override
//                    public void executeCallback(@Nullable Object args) {
//                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext())) {
//                            JPushInterface.stopPush(Latte.getApplicationContext());
//                        }
//                    }
//                });
//    }

//    private void initStetho() {
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());
//    }
    }
}
