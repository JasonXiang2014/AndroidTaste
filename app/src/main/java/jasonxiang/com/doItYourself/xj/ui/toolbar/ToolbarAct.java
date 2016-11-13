package jasonxiang.com.doItYourself.xj.ui.toolbar;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.base.BaseActivity;

/**
 * Created by xj on 2016/11/13.
 */

public class ToolbarAct extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarCustom)
    Toolbar toolbarCustom;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tool_bar;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

//        setSupportActionBar(toolbarCustom);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        TextView mTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("Changed Runtime");

//        TODO  Transparent Status Bar
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

}
