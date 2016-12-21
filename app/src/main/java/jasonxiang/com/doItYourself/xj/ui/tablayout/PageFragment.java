package jasonxiang.com.doItYourself.xj.ui.tablayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.base.BaseFragment;

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
        View root = getRoot();
        TextView textView = (TextView) root.findViewById(R.id.tv);
        textView.setText("Fragment #" + mPage);
        TestViewGroup container = (TestViewGroup) root.findViewById(R.id.container);
        TextView errorView = new TextView(getContext());
        errorView.setClickable(true);
        errorView.setTextColor(0xffff6600);
        errorView.setGravity(Gravity.CENTER);
        errorView.setTextSize(20);
        errorView.setText("Just for test");
        container.setHeader(errorView);
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