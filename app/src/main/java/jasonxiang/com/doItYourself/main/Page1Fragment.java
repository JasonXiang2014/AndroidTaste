package jasonxiang.com.doItYourself.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.base.BaseFragment;

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

    @OnClick({R.id.recyclerButton, R.id.fab})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.recyclerButton:
                Intent intent = new Intent(getActivity(), RecyclerActivity.class);
                startActivity(intent);
                break;
            case R.id.fab:
                break;
        }
    }

}
