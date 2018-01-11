package jasonxiang.com.doityourself.xj.app;

import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;

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

        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }
}
