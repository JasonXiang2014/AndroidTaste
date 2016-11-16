package jasonxiang.com.doItYourself.xj.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.base.BaseActivity;
import jasonxiang.com.doItYourself.xj.base.BaseFragment;
import jasonxiang.com.doItYourself.xj.common.widget.IndicatorView;

public class MainActivity extends BaseActivity {

    private int indicatorWidth = 0;

    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.line)
    View vLine;
    @BindView(R.id.indicatorview)
    IndicatorView indicatorview;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        int screenWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();

        LayoutParams lp = vLine.getLayoutParams();
        indicatorWidth = lp.width = screenWidth / 3;
        lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        vLine.setLayoutParams(lp);

        BaseFragment page1Fragment = new Page1Fragment();
        BaseFragment page2Fragment = new Page2Fragment();
        BaseFragment page3Fragment = new Page3Fragment();
        fragmentArrayList.add(page1Fragment);
        fragmentArrayList.add(page2Fragment);
        fragmentArrayList.add(page3Fragment);
        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        indicatorview.setViewPager(mViewPager);
        PageChangeListener pageChangeListener = new PageChangeListener();
        indicatorview.setOnPageChangeListener(pageChangeListener);
    }

    @OnClick({R.id.fade_anim_left, R.id.fade_anim_middle, R.id.fade_anim_right})
    void doSwitch(View view) {
        switch (view.getId()) {
            case R.id.fade_anim_left:
                mViewPager.setCurrentItem(0, true);
                vLine.setTranslationX(indicatorWidth * 0);
                findViewById(R.id.fade_anim_left).setPressed(true);
                break;
            case R.id.fade_anim_middle:
                mViewPager.setCurrentItem(1, true);
                vLine.setTranslationX(indicatorWidth * 1);
                findViewById(R.id.fade_anim_middle).setPressed(true);
                break;
            case R.id.fade_anim_right:
                mViewPager.setCurrentItem(2, true);
                vLine.setTranslationX(indicatorWidth * 2);
                findViewById(R.id.fade_anim_right).setPressed(true);
                break;
            default:
                break;
        }
    }

    class PageChangeListener implements OnPageChangeListener {
        private boolean isAnim = false;
        private int pos = 0;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 0) {
                isAnim = false;
                vLine.setTranslationX(indicatorWidth * pos);
            } else if (arg0 == 1) {
                isAnim = true;
            } else if (arg0 == 2) {
                isAnim = false;
                vLine.setTranslationX(indicatorWidth * pos);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (isAnim && arg1 != 0) {
                vLine.setTranslationX(indicatorWidth * (arg0 + arg1));
            }
        }

        @Override
        public void onPageSelected(int arg0) {
            vLine.setTranslationX(indicatorWidth * arg0);
            pos = arg0;
        }

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
