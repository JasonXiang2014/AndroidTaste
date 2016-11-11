package jasonxiang.com.doItYourself.xj.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.ButterKnife;
import jasonxiang.com.doItYourself.R;

/**
 * Created by xiangjian on 2016/11/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindString(R.string.crash)
    String crashMessage;

    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = getLayoutInflater().inflate(getContentViewId(), null);
        setContentView(root);
        ButterKnife.bind(this);
        try {
            init(savedInstanceState);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), crashMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
