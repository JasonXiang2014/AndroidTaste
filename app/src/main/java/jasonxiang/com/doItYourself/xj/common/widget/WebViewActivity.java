package jasonxiang.com.doItYourself.xj.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindView;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.base.BaseActivity;
import jasonxiang.com.doItYourself.xj.common.utils.ContextUtils;
import jasonxiang.com.doItYourself.xj.common.utils.Log;
import jasonxiang.com.doItYourself.xj.common.widget.webview.JavaCallJsAct;

/*
 * Define a web view template activity
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_TITLE_STRING = "title";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_LINK = "link";
    public static final String EXTRA_SHARE_LINK = "shareLink";
    public static final String EXTRA_SHARE_ICON_URL = "shareIconUrl";
    public static final String EXTRA_SHOW_FOOTER_BAR = "showFooterbar";
    public static final String EXTRA_ENABLE_SHARE = "enableShare";
    public static final String EXTRA_POST_DATA = "postData";
    public static final String EXTRA_SHOULD_CAPTURE_LOGIN_URL = "shouldCaptureLoginUrl";
    protected static final String TAG = "WebView";

    protected String title;
    protected String description;
    protected String link;
    protected String postData;
    protected String shareLink;
    protected String shareIconUrl;
    protected boolean showFooterbar;
    protected WebView webview;
    protected ProgressBar progressBar;
    protected boolean enableShare;
    protected boolean shouldCaptureLoginUrl;
    @BindView(R.id.layoutFooter)
    View layoutFooter;
    @BindView(R.id.btnGoBack)
    ImageView btnGoBack;
    @BindView(R.id.btnGoForward)
    ImageView btnGoForward;
    @BindView(R.id.btnShare)
    ImageView btnShare;
    @BindView(R.id.btnRefresh)
    ImageView btnRefresh;
    @BindView(R.id.btnStop)
    ImageView btnStop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static void openLink(Context context, String link, String title) {
        openLink(context, link, title, null);
    }

    public static void openLink(Context context, String link, String title, String shareLink) {
        openLink(context, link, title, shareLink, title);
    }

    public static void openLink(Context context, String link, String title, String shareLink, String description) {
        openLink(context, link, title, shareLink, description, true);
    }

    public static void openLink(Context context, String link, String title, String shareLink, String description, boolean showFooterbar) {
        openLink(context, link, title, shareLink, description, showFooterbar, true);
    }

    public static void openLink(Context context, String link, String title, String shareLink, String description, boolean showFooterbar, boolean enableShare) {

        Intent intent = new Intent(context, WebViewActivity.class);

        intent.putExtra(EXTRA_TITLE_STRING, title);
        intent.putExtra(EXTRA_LINK, link);
        intent.putExtra(EXTRA_SHARE_LINK, shareLink);
        if (description == null) {
            description = title;
        }
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_SHOW_FOOTER_BAR, showFooterbar);
        intent.putExtra(EXTRA_ENABLE_SHARE, enableShare);

        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.web_display;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        title = getIntent().getStringExtra(EXTRA_TITLE_STRING);
        description = getIntent().getStringExtra(EXTRA_DESCRIPTION);
        link = getIntent().getStringExtra(EXTRA_LINK);
        postData = getIntent().getStringExtra(EXTRA_POST_DATA);
        shareLink = getIntent().getStringExtra(EXTRA_SHARE_LINK);
        shareIconUrl = getIntent().getStringExtra(EXTRA_SHARE_ICON_URL);
        showFooterbar = getIntent().getBooleanExtra(EXTRA_SHOW_FOOTER_BAR, false);
        enableShare = getIntent().getBooleanExtra(EXTRA_ENABLE_SHARE, false);
        shouldCaptureLoginUrl = getIntent().getBooleanExtra(EXTRA_SHOULD_CAPTURE_LOGIN_URL, false);

        this.progressBar = (ProgressBar) this.findViewById(R.id.webdisplay_progress_bar);

        this.webview = (WebView) this.findViewById(R.id.web_page);
        this.webview.getSettings().setBuiltInZoomControls(false);
        this.webview.getSettings().setJavaScriptEnabled(true);
        this.webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.webview.setWebViewClient(this.getWebViewClient());
        this.webview.setWebChromeClient(this.getWebChromeClient());
        addJavascriptInterface(webview);
        String userAgent = this.webview.getSettings().getUserAgentString();
        userAgent +=
                "Android/" + ContextUtils.getSystemVersion() + " XJ/" + ContextUtils.getVersionCode(this) + " ClientType/" + ContextUtils
                        .getClientType() + " ChannelId/" + ContextUtils.getUmengChannel();
        webview.getSettings().setUserAgentString(userAgent);
        if (Build.VERSION.SDK_INT > 19) {
            webview.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webview.getSettings().setLoadsImagesAutomatically(false);
        }
        webview.getSettings().setLoadsImagesAutomatically(false);
        toolbar.setTitle(getWebTitle());

        loadPage();
        Log.i(TAG, "link: " + link);

        if (showFooterbar) {
            btnGoBack.setOnClickListener(this);
            btnGoForward.setOnClickListener(this);
            btnShare.setOnClickListener(this);
            btnRefresh.setOnClickListener(this);
            btnStop.setOnClickListener(this);

            btnGoBack.setEnabled(false);
            btnGoForward.setEnabled(false);

            btnRefresh.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(shareLink) || enableShare) {
                btnShare.setEnabled(true);
            } else {
                btnShare.setEnabled(false);
            }
        } else {
            layoutFooter.setVisibility(View.GONE);
        }

//         TODO 始终显示或隐藏footer bar，不随滑动手势变化
        final GestureDetector gestureDetector = new GestureDetector(this, new MyGestureListener());
        gestureDetector.setIsLongpressEnabled(false);
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

    protected void addJavascriptInterface(WebView webView) {
        // nothing
    }

    @Override
    protected void onDestroy() {
        webview.getSettings().setBuiltInZoomControls(false);
        webview.setVisibility(View.GONE);
        webview.destroy();
        super.onDestroy();
    }

    public String getWebTitle() {
        String title = this.getIntent().getStringExtra(EXTRA_TITLE_STRING);
        if (title == null) {
            title = getString(R.string.app_name);
        }
        return title;
    }

    protected WebChromeClient getWebChromeClient() {
        return new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    WebViewActivity.this.progressBar.setVisibility(View.GONE);
                } else {
                    if (WebViewActivity.this.progressBar.getVisibility() == View.GONE) {
                        WebViewActivity.this.progressBar.setVisibility(View.VISIBLE);
                    }
                    WebViewActivity.this.progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        };
    }

    protected WebViewClient getWebViewClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return WebViewActivity.this.shouldOverrideUrlLoading(view, url);
            }

            //处理https请求，为 WebView 处理 ssl证书 设置 WebView 默认是不处理https请求的，
            //需要在 WebViewClient 子类中重写父类的 onReceivedSslError 函数
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                WebViewActivity.this.onPageFinished(view, url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                WebViewActivity.this.onPageStarted(view, url, favicon);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

        };
    }

    /**
     * Notify the host application that a page has started loading. This method
     * is called once for each main frame load so a page with iframes or
     * framesets will call onPageStarted one time for the main frame. This also
     * means that onPageStarted will not be called when the contents of an
     * embedded frame changes, i.e. clicking a link whose target is an iframe.
     *
     * @param view    The WebView that is initiating the callback.
     * @param url     The url to be loaded.
     * @param favicon The favicon for this page if it already exists in the
     *                database.
     */
    protected boolean onPageStarted(WebView view, String url, Bitmap favicon) {
        return false;
    }

    /**
     * Notify the host application that a page has finished loading. This method
     * is called only for main frame. When onPageFinished() is called, the
     * rendering picture may not be updated yet. To get the notification for the
     * new Picture, use {@link WebView.PictureListener#onNewPicture}.
     *
     * @param view The WebView that is initiating the callback.
     * @param url  The url of the page.
     */
    public void onPageFinished(WebView view, String url) {
        Log.d(TAG, "onPageFinished: " + url);
        refreshStatus(true);
        if (!webview.getSettings().getLoadsImagesAutomatically()) {
            webview.getSettings().setLoadsImagesAutomatically(true);
        }
    }

    /**
     * Give the host application a chance to take over the control when a new
     * url is about to be loaded in the current WebView. If WebViewClient is not
     * provided, by default WebView will ask Activity Manager to choose the
     * proper handler for the url. If WebViewClient is provided, return true
     * means the host application handles the url, while return false means the
     * current WebView handles the url. This method is not called for requests
     * using the POST "method".
     * <p>
     * Overrides: shouldOverrideUrlLoading(...) in WebViewClient
     *
     * @param view The WebView that is initiating the callback.
     * @param url  The url to be loaded.
     * @return True if the host application wants to leave the current WebView
     * and handle the url itself, otherwise return false.
     */
    protected boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            Log.e(TAG, "onShouldOverrideUrl: " + url);
            link = url;
        } catch (Exception e) {
            Log.logStackTrace(e);
        }

        // 某些情况需要在内部浏览器打开第三方页面，可以考虑建立白名单
        // try {
        // String host = new URL(url).getHost();
        // String baseHost = new URL(URLChooser.getBaseURL()).getHost();
        // if (!host.equals(baseHost)) {
        // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        // startActivity(intent);
        // return true;
        // }
        // } catch (Exception e) {
        // Log.logStackTrace(e);
        // }

        return false;
    }

    protected void share(String title, String description, String shareLink, String shareIconUrl) {
        if (shareLink == null) {
            shareLink = webview.getUrl();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnGoBack) {
            webview.goBack();
        } else if (v == btnGoForward) {
            webview.goForward();
        } else if (v == btnShare) {
            share(title, description, shareLink, shareIconUrl);
        } else if (v == btnRefresh) {
            webview.reload();
            refreshStatus(false);
        } else if (v == btnStop) {
            webview.stopLoading();
            refreshStatus(true);
        }
    }

    private void refreshStatus(boolean loadFinished) {
        btnGoBack.setEnabled(webview.canGoBack());
        btnGoForward.setEnabled(webview.canGoForward());
        btnRefresh.setVisibility(loadFinished ? View.VISIBLE : View.GONE);
        btnStop.setVisibility(loadFinished ? View.GONE : View.VISIBLE);
    }

    private void loadPage() {
        webview.loadUrl(link);
        /*if (TextUtils.isEmpty(postData)) {
            webview.loadUrl(link);
        } else {
            webview.postUrl(link, EncodingUtils.getBytes(postData, "BASE64"));
        }*/
    }

    private class MyGestureListener implements GestureDetector.OnGestureListener {

        private final float flyingVelocityY = getResources().getDimension(R.dimen.flyingVelocityY);
        private Animation animationIn = AnimationUtils.loadAnimation(WebViewActivity.this, R.anim.footerbar_in);
        private Animation animationOut = AnimationUtils.loadAnimation(WebViewActivity.this, R.anim.footerbar_out);
        private int animState = 0;// 1消失, 2弹出

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (animState != 1 && distanceY > 0) {
                layoutFooter.clearAnimation();
                layoutFooter.startAnimation(animationOut);
                animState = 1;
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (animState != 2 && (velocityY > flyingVelocityY || webview.getScrollY() == 0)) {
                layoutFooter.clearAnimation();
                layoutFooter.startAnimation(animationIn);
                animState = 2;
            }
            return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            webview.saveState(savedInstanceState);
        } else {
            loadPage();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webview.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_script, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.script:
                startActivity(new Intent(WebViewActivity.this, JavaCallJsAct.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
