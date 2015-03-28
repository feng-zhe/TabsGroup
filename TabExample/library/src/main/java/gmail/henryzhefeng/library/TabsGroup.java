package gmail.henryzhefeng.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 哲 on 3/26/2015.
 */
public class TabsGroup extends ViewGroup {

    // the bar
    private View mBar;
    private int currTab = 1;

    public TabsGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        // add the bar
        mBar = new View(context);
        mBar.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        LayoutParams params = new LayoutParams(80, 10);
        this.addView(mBar, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // used to calculate children position.
        int width = getPaddingLeft();
        int height = getPaddingTop();
        final int childCnt = getChildCount();

        for (int i = 0; i < childCnt; ++i) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            // skip the bar
            if (child == mBar) {
                continue;
            }

            LayoutParams params = (LayoutParams) child.getLayoutParams();
            params.x = width += params.leftMargin;
            params.y = params.topMargin;
            // update next view's position
            width += child.getMeasuredWidth() + params.rightMargin;
            int tempHeight = 0;
            tempHeight = params.topMargin + params.bottomMargin + child.getMeasuredHeight();
            height = tempHeight > height ? tempHeight : height;
        }

        // get whole size of the layout
        width += getPaddingRight();
        height += getPaddingBottom();

        // set the bar
        LayoutParams barParams = (LayoutParams) mBar.getLayoutParams();
        View tabView = getChildAt(currTab /*the bar itself is the first child*/);
        LayoutParams tabParams = (LayoutParams) tabView.getLayoutParams();
        barParams.x = tabParams.x;
        barParams.y = height;
        barParams.width = tabView.getMeasuredWidth();
        height += mBar.getMeasuredHeight();

        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y + child.getMeasuredHeight());
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /**
     * The LayoutParams of TabsGroup
     */
    public static class LayoutParams extends MarginLayoutParams {

        int x;
        int y;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }
    }

    interface ITabsGroup {

    }
}


