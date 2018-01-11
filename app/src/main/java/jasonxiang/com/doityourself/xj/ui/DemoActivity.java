package jasonxiang.com.doityourself.xj.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import jasonxiang.com.doityourself.R;
import jasonxiang.com.doityourself.xj.base.BaseActivity;
import jasonxiang.com.doityourself.xj.common.utils.UIUtils;
import jasonxiang.com.doityourself.xj.common.widget.HorizontalScrollViewEx;

/**
 * Created by JasonXiang on 2016/12/19.
 */

public class DemoActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_horizontal_scrollview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        int screenWidth = UIUtils.getScreenWidth();
        LayoutInflater layoutInflater = getLayoutInflater();
        HorizontalScrollViewEx container = (HorizontalScrollViewEx) findViewById(R.id.container);
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) layoutInflater.inflate(R.layout.content_layout, container, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            container.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListView lv = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        lv.setAdapter(adapter);
    }

}
