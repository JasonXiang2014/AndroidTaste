package jasonxiang.com.doItYourself.xj.app;

import android.support.multidex.MultiDexApplication;

/**
 * Created by xiangjian on 2016/11/11.
 */

public class XJApplication extends MultiDexApplication {

    private static XJApplication instance;

    public static synchronized XJApplication getInstance() {
        return XJApplication.instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
