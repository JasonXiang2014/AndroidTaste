package jasonxiang.com.doityourself.xj.ui.okhttp;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import jasonxiang.com.doityourself.R;
import jasonxiang.com.doityourself.xj.base.BaseActivity;

/**
 * Created by JasonXiang on 2016/12/24.
 */

public class OkhttpActivity extends BaseActivity {

    @BindView(R.id.content)
    TextView tvContent;

    public SingletonBad singletonBad;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_http;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        singletonBad = SingletonBad.getInstance(this);
    }




}
