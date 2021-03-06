package jasonxiang.com.doityourself.xj.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import jasonxiang.com.doityourself.R;
import jasonxiang.com.doityourself.mvp.bean.UserLoginActivity;
import jasonxiang.com.doityourself.xj.base.BaseFragment;
import jasonxiang.com.doityourself.xj.common.widget.WebViewActivity;
import jasonxiang.com.doityourself.xj.model.Book;
import jasonxiang.com.doityourself.xj.model.User;
import jasonxiang.com.doityourself.xj.ui.DemoActivity;
import jasonxiang.com.doityourself.xj.ui.fab.FloatingActionButtonAct;
import jasonxiang.com.doityourself.xj.ui.graph.GraphActivity;
import jasonxiang.com.doityourself.xj.ui.list.UltraListActivity;
import jasonxiang.com.doityourself.xj.ui.navigationdrawer.NavigationDrawerAct;
import jasonxiang.com.doityourself.xj.ui.okhttp.OkhttpActivity;
import jasonxiang.com.doityourself.xj.ui.recycler.PinterestMasonryAct;
import jasonxiang.com.doityourself.xj.ui.recycler.RecyclerActivity;
import jasonxiang.com.doityourself.xj.ui.stickerheader.StickerHeaderAct;
import jasonxiang.com.doityourself.xj.ui.tablayout.TabLayoutAct;
import jasonxiang.com.doityourself.xj.ui.toolbar.ToolbarAct;

/**
 * Created by xiangjain on 2016/11/10.
 */

public class Page1Fragment extends BaseFragment {

    public static final String EXTRA_USER = "user";

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_page_1;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.recyclerButton, R.id.fab, R.id.tabLayout, R.id.navigation_drawer,
            R.id.Masonry, R.id.toolbar, R.id.webview, R.id.stickerHeader, R.id.HorizontalScrollViewEx,
            R.id.PullAndLoadListView, R.id.MVP, R.id.graph, R.id.okhttp})
    void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.recyclerButton:
                intent = new Intent(getActivity(), RecyclerActivity.class);
                Book book = new Book("Hello Parcelable", 1, false);
                User user = new User(10150063, "XiangJian", true, book);
                intent.putExtra(EXTRA_USER, user);
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
            case R.id.PullAndLoadListView:
                intent = new Intent(getActivity(), UltraListActivity.class);
                break;
            case R.id.MVP:
                intent = new Intent(getActivity(), UserLoginActivity.class);
                break;
            case R.id.graph:
                intent = new Intent(getActivity(), GraphActivity.class);
                break;
            case R.id.okhttp:
                intent = new Intent(getActivity(), OkhttpActivity.class);
                break;
            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

}
