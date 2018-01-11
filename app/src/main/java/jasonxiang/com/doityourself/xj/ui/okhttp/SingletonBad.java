package jasonxiang.com.doityourself.xj.ui.okhttp;

import android.content.Context;

/**
 * Created by JasonXiang on 2017/2/28.
 */

public class SingletonBad{
    private static SingletonBad singletonBad;
    private Context context;

    private SingletonBad(Context context){
        this.context = context;
    }

    public static SingletonBad getInstance(Context context){
        if(singletonBad == null){
            singletonBad = new SingletonBad(context);
        }
        return  singletonBad;
    }

}
