package jasonxiang.com.doItYourself.xj.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.ptr.OnLoadMoreListener;
import jasonxiang.com.doItYourself.ptr.PullToRefreshBase;
import jasonxiang.com.doItYourself.ptr.PullToRefreshListView;
import jasonxiang.com.doItYourself.xj.base.BaseFragment;

/**
 * Created by xiangjain on 2016/11/10.
 */

public class Page2Fragment extends BaseFragment {

    @BindView(R.id.ptrLv)
    PullToRefreshListView ptrLv;
    private MyAdapter myAdapter;
    private ArrayList<String> list = new ArrayList<>();
    private static final int PAGE_COUNT = 10;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_page_2;
    }

    protected void init(Bundle savedInstanceState) {
        initData();
        ptrLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshBase refreshView) {
                refreshRequest();
                ptrLv.onRefreshComplete();
            }
        });
        ptrLv.showLoadMoreLoading();
        ptrLv.setTotalAmount(50);
        ptrLv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addList();
                        ptrLv.onLoadMoreComplete(myAdapter.getCount() > 50 ? true : false);
                    }
                }, 2000);
            }
        });
        myAdapter = new MyAdapter(getContext(), list);
        ptrLv.setAdapter(myAdapter);
    }

    private void refreshRequest() {
        list.clear();
        initData();
        notifyDataSetChanged();
    }

    private void initData() {
        for (int i = 0; i < PAGE_COUNT; i++) {
            list.add("POSITION = " + i);
        }
        notifyDataSetChanged();
    }

    private void addList() {
        for (int i = 0; i < PAGE_COUNT; i++) {
            list.add("New POSITION = " + i);
        }
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        if (myAdapter == null) {
            return;
        }
        myAdapter.notifyDataSetChanged();
    }

    public ListView getListView() {
        return ptrLv.getRefreshableView();
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
