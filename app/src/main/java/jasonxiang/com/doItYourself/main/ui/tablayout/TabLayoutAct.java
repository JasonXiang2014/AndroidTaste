package jasonxiang.com.doItYourself.main.ui.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.base.BaseActivity;

/**
 * Created by xiangjian on 2016/11/11.
 */

//refer:https://github.com/codepath/android_guides/wiki/Google-Play-Style-Tabs-using-TabLayout
public class TabLayoutAct extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tablayout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), TabLayoutAct.this));

        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[]{"Tab1", "Tab2", "Tab3", "Tab4", "Tab5", "Tab6",
                "Tab7", "Tab8", "Tab9"};
        private Context context;

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position + 1);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

}
