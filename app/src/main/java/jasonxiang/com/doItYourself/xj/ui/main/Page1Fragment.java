package jasonxiang.com.doItYourself.xj.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.base.BaseFragment;
import jasonxiang.com.doItYourself.xj.common.widget.WebViewActivity;
import jasonxiang.com.doItYourself.xj.ui.DemoActivity;
import jasonxiang.com.doItYourself.xj.ui.stickerheader.StickerHeaderAct;
import jasonxiang.com.doItYourself.xj.ui.fab.FloatingActionButtonAct;
import jasonxiang.com.doItYourself.xj.ui.navigationdrawer.NavigationDrawerAct;
import jasonxiang.com.doItYourself.xj.ui.recycler.PinterestMasonryAct;
import jasonxiang.com.doItYourself.xj.ui.recycler.RecyclerActivity;
import jasonxiang.com.doItYourself.xj.ui.tablayout.TabLayoutAct;
import jasonxiang.com.doItYourself.xj.ui.toolbar.ToolbarAct;

/**
 * Created by xiangjain on 2016/11/10.
 */

public class Page1Fragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_page_1;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.recyclerButton, R.id.fab, R.id.tabLayout, R.id.navigation_drawer,
            R.id.Masonry, R.id.toolbar, R.id.webview, R.id.stickerHeader,R.id.HorizontalScrollViewEx})
    void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.recyclerButton:
                intent = new Intent(getActivity(), RecyclerActivity.class);
                break;
            case R.id.fab:
                intent = new Intent(getActivity(), FloatingActionButtonAct.class);
                break;
            case R.id.tabLayout:
                intent = new Intent(getActivity(), TabLayoutAct.class);
                break;
            case R.id.navigation_drawer:
                intent = new Intent(getActivity(), NavigationDrawerAct.class);
                break;
            case R.id.Masonry:
                intent = new Intent(getActivity(), PinterestMasonryAct.class);
                break;
            case R.id.toolbar:
                intent = new Intent(getActivity(), ToolbarAct.class);
                break;
            case R.id.webview:
                WebViewActivity.openLink(getActivity(), "http://www.baidu.com", "WebView Title");
                break;
            case R.id.stickerHeader:
                intent = new Intent(getActivity(), StickerHeaderAct.class);
                break;
            case R.id.HorizontalScrollViewEx:
                intent = new Intent(getActivity(), DemoActivity.class);
                break;
            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

}
