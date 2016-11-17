package jasonxiang.com.doItYourself.xj.common.widget.webview;

import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.base.BaseActivity;

/**
 * Created by xiangjian on 2016/11/17.
 */

public class JavaCallJsAct extends BaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout ll_root;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    private WebView webView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_java_call_js;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWebView();

    }


    private void initWebView() {

        webView = new WebView(getApplication());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(layoutParams);
        ll_root.addView(webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
        webView.addJavascriptInterface(new JSInterface(), "Android");
    }

    @OnClick(R.id.btnConfirm)
    public void onClick() {
        //java调用JS方法
        webView.loadUrl("javascript:javaCallJs(" + "'" + editText.getText().toString() + "'" + ")");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ll_root.removeView(webView);
        webView.stopLoading();
        webView.removeAllViews();
        webView.destroy();
        webView = null;
    }

    private class JSInterface {
        //JS 需要调用的方法
        @JavascriptInterface
        public void showToast(String args) {
            Toast.makeText(JavaCallJsAct.this, args, Toast.LENGTH_LONG).show();
        }
    }

}
