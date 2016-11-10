package jasonxiang.com.doItYourself.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiangjian on 2016/11/10.
 */

public abstract class BaseFragment extends Fragment {

    private View root;

    protected View getRoot() {
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(getContentViewId(), container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);


}
