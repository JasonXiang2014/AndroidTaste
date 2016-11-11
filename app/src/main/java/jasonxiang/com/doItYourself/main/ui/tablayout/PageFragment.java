package jasonxiang.com.doItYourself.main.ui.tablayout;

import android.os.Bundle;
import android.widget.TextView;

import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.base.BaseFragment;

/**
 * Created by xiangjian on 2016/11/11.
 */

// In this case, the fragment displays simple text based on the page
public class PageFragment extends BaseFragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_page;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        TextView textView = (TextView) getRoot();
        textView.setText("Fragment #" + mPage);
    }

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }
}