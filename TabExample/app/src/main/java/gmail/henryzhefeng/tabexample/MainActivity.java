package gmail.henryzhefeng.tabexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import gmail.henryzhefeng.tabexample.Fragments.ContentFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
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
