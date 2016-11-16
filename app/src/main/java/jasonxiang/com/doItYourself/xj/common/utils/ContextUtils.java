package jasonxiang.com.doItYourself.xj.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import jasonxiang.com.doItYourself.xj.app.XJApplication;


public class ContextUtils {

    // 以此channel标记GooglePlay应用
    private static final String CHANNEL_GOOGLE = "play.google.com";

    private static final String PREFERENCES_NAME = "CONTEXT";

    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            Log.logStackTrace(e);
        }
        return null;
    }

    public static Bundle getMetaData() {
        try {
            return XJApplication.getInstance().getPackageManager().getApplicationInfo(
                    XJApplication.getInstance().getPackageName(), PackageManager.GET_META_DATA).metaData;
        } catch (NameNotFoundException e) {
            Log.logStackTrace(e);
        }
        return null;
    }

    public static String getClientType() {
        String clientType = getMetaData().getString("client_type");
        if (clientType != null) {
            return clientType;
        }
        return "unknow";
    }

    public static String getReferralName() {
        String channel = getMetaData().getString("REFERRER_NAME");
        if (channel == null) {
            channel = "unknow";
        }
        return channel;
    }

    public static String getUmengChannel() {
        String channel = getMetaData().getString("UMENG_CHANNEL");
        if (channel == null) {
            channel = "unknow";
        }
        return channel;
    }

    public static boolean isGoogleApp() {
        return CHANNEL_GOOGLE.equals(getUmengChannel());
    }

    public static void showKeyboard(Context context, View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideKeyboard(Context context, View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getDeviceName() {
        return Build.MODEL;
    }

    public static String getSystemVersion() {
        return "" + Build.VERSION.SDK_INT;
    }

    public static String getImei() {
        String deviceId = null;
        try {
            deviceId = ((TelephonyManager) XJApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            Log.logStackTrace(e);
        }
        if (TextUtils.isEmpty(deviceId)) {
            return "";
        }
        return deviceId;
    }
}
