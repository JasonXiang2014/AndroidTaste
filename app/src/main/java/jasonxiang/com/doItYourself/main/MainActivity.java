package jasonxiang.com.doItYourself.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.view.IndicatorView;

public class MainActivity extends Activity {

    private ViewPager mViewPager;
    private IndicatorView indicatorview;
    private View vLine;

    private int indicatorWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        vLine = findViewById(R.id.line);
        indicatorview = (IndicatorView) findViewById(R.id.indicatorview);

        int screenWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();


        LayoutParams lp = vLine.getLayoutParams();
        indicatorWidth = lp.width = screenWidth / 3;
        lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        vLine.setLayoutParams(lp);

        List<View> viewList = new ArrayList<>();
        View view1 = getLayoutInflater().inflate(R.layout.layout_item_1, null, false);
        Button recycleButton = (Button) view1.findViewById(R.id.recycleButton);
        recycleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecycleActivity.class);
                startActivity(intent);
            }
        });
        View view2 = getLayoutInflater().inflate(R.layout.layout_item_2, null, false);
        View view3 = getLayoutInflater().inflate(R.layout.layout_item_3, null, false);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        MyPagerAdapter mPagerAdapter = new MyPagerAdapter(viewList);
        mViewPager.setAdapter(mPagerAdapter);
        indicatorview.setViewPager(mViewPager);
        PageChangeListener pageChangeListener = new PageChangeListener();
        indicatorview.setOnPageChangeListener(pageChangeListener);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    public void doSwicth(View view) {
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

    class MyPagerAdapter extends PagerAdapter {

        List<View> mViewList;

        private MyPagerAdapter(List<View> viewList) {
            mViewList = viewList;
        }

        @Override
        public int getCount() {
            return mViewList == null || mViewList.size() <= 0 ? 0 : mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

    }

}
