package jasonxiang.com.doityourself.xj.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import jasonxiang.com.doityourself.R;
import jasonxiang.com.doityourself.xj.base.BaseFragment;
import jasonxiang.com.doityourself.xj.common.widget.ScrollerView;

/**
 * Created by xiangjain on 2016/11/10.
 */

public class Page3Fragment extends BaseFragment {

    @BindView(R.id.scrollerView)
    ScrollerView scrollerView;
    @BindView(R.id.btnScroll)
    Button btnScroll;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_page_3;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        btnScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollerView.smoothScrollTo(-400, -400);
            }
        });
    }

}
