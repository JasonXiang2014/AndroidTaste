package jasonxiang.com.doItYourself.xj.app;

import android.app.Application;

/**
 * Created by xiangjian on 2016/11/11.
 */

public class XJApplication extends Application {

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
