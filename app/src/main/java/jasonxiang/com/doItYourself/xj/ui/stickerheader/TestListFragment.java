package jasonxiang.com.doItYourself.xj.ui.stickerheader;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.base.BaseFragment;
import jasonxiang.com.doItYourself.xj.ui.main.Page2Fragment;

/**
 * Created by Administrator on 2016/11/30.
 */

public class TestListFragment extends BaseFragment {

    @BindView(R.id.listview)
    ListView listView;
    private ArrayList<String> list = new ArrayList<>();

    private MyAdapter myAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_listview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        for (int i = 0; i < 10; i++) {
            list.add("POSITION = " + i);
        }
        myAdapter = new MyAdapter(getContext(), list);
        listView.setAdapter(myAdapter);
    }

    public ListView getListView() {
        return listView;
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item, null);
                holder = new ViewHolder();
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
