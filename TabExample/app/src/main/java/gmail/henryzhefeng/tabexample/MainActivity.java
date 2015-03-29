package gmail.henryzhefeng.tabexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import gmail.henryzhefeng.library.TabsGroup;
import gmail.henryzhefeng.tabexample.Fragments.ContentFragment;

public class MainActivity extends FragmentActivity implements TabsGroup.OnTabsSelectListener {

    private ViewPager mPager;
    private TabsGroup mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mPager.setOnPageChangeListener(new MyPageChangeListener());

        mTabs = (TabsGroup) findViewById(R.id.tabs);
        mTabs.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelect(int from, int to, View view) {
        mPager.setCurrentItem(to - 1);
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // ignore now
        }

        @Override
        public void onPageSelected(int position) {
            mTabs.setCurrentTab(position + 1);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // ignore now
        }
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        private ContentFragment[] fragments = new ContentFragment[3];

        {
            ContentFragment frag;
            frag = new ContentFragment();
            frag.setBgColorId(android.R.color.holo_green_light);
            fragments[0] = frag;
            frag = new ContentFragment();
            frag.setBgColorId(android.R.color.holo_blue_bright);
            fragments[1] = frag;
            frag = new ContentFragment();
            frag.setBgColorId(android.R.color.holo_purple);
            fragments[2] = frag;
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
