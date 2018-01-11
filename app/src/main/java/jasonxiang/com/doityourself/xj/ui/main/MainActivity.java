package jasonxiang.com.doityourself.xj.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import jasonxiang.com.doityourself.R;
import jasonxiang.com.doityourself.xj.base.BaseActivity;
import jasonxiang.com.doityourself.xj.base.BaseFragment;
import jasonxiang.com.doityourself.xj.common.widget.ViewPagerIndicator;

public class MainActivity extends BaseActivity {

    private List<String> mDatas = Arrays.asList("Page1", "Page2", "Page3");
    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.indicator)
    ViewPagerIndicator indicator;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        BaseFragment page1Fragment = new Page1Fragment();
        BaseFragment page2Fragment = new Page2Fragment();
        BaseFragment page3Fragment = new Page3Fragment();
        fragmentArrayList.add(page1Fragment);
        fragmentArrayList.add(page2Fragment);
        fragmentArrayList.add(page3Fragment);
        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        //设置标题
        indicator.setTabItemTitles(mDatas);
        //关联viewpager
        indicator.setViewPager(mViewPager, 0 );
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentArrayList;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
            super(fm);
            this.fragmentArrayList = fragmentArrayList;
        }

        @Override
        public int getCount() {
            return fragmentArrayList != null ? fragmentArrayList.size() : 0;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }
    }

}
