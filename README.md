# TabsGroup
My substitution for android Tabs control

This is my plan, I wish I can stick to it :)

Version  | Action
-------- | ------
0.1 | able to display
0.2 | design necessary interfaces
0.3 | implement necessary interfaces
0.4 | design advanced interfaces
0.5 | implement advanced interfaces
0.6 | refactor
0.7 | basic functionality complete
0.8 | UI improvement
0.9 | Test
1.0 | stable version

#How to use TabsGroup
##In XML file
```html
<gmail.henryzhefeng.library.TabsGroup
        android:id="@+id/tabs"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        tabs:bar_height="3dip">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginRight="5dip"
            android:text="Hello"
            android:textColor="@android:color/black"
            android:textSize="20sp" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="World"
            android:textColor="@android:color/black"
            android:textSize="20sp" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="TabsGroup"
            android:textColor="@android:color/black"
            android:textSize="20sp" />

</gmail.henryzhefeng.library.TabsGroup>
```

##In JAVA file

Normally, you use setCurrentTabWithOffset(...) to move the bar among tabs.

In the example, I use ViewPager:
<pre><code>
private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mTabs.setCurrentTabWithOffset(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            // ignored
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // ignore now
        }
    }
</code></pre>

And if you want to listen the click event, just implement the interface
<code>TabsGroup.OnTabsSelectListener</code>


# If you have any advice or suggestion, please contact me by gmail: henryzhefeng@gmail.com
