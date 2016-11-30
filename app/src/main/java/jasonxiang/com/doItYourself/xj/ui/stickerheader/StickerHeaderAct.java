package jasonxiang.com.doItYourself.xj.ui.stickerheader;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.base.BaseActivity;
import jasonxiang.com.doItYourself.xj.common.widget.TouchScrollLinearLayout;

/**
 * Created by xiangjian on 2016/11/30.
 */

public class StickerHeaderAct extends BaseActivity {

    @BindView(R.id.touchScroll)
    TouchScrollLinearLayout touchScrollLinearLayout;
    @BindView(R.id.banner_layout)
    LinearLayout banner_layout;
    @BindView(R.id.tab1)
    RadioButton tab1;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;

    @BindView(R.id.listview)
    ListView listView;
    private ArrayList<String> list = new ArrayList<>();

    private MyAdapter myAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sticker_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        touchScrollLinearLayout.setHeaderView(banner_layout);
        touchScrollLinearLayout.setChildListView(listView);

        for (int i = 0; i < 30; i++) {
            list.add("POSITION = " + i);
        }
        myAdapter = new MyAdapter(StickerHeaderAct.this, list);
        listView.setAdapter(myAdapter);
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private ArrayList<String> list = new ArrayList<>();

        public MyAdapter(Context context, ArrayList<String> list) {
            this.mInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item, null);
                holder = new MyAdapter.ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(getItem(position).toString());
            return convertView;
        }

        public final class ViewHolder {
            public TextView title;
        }
    }

}
