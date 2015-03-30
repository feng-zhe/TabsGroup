package gmail.henryzhefeng.library;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 哲 on 3/26/2015.
 */
public class TabsGroup extends ViewGroup {

    private View mBar; // the bar
    private int currTab = 0;
    private int tabCnt;
    private int mHSpacing;
    private int mBarColor;
    private int mBarHeight;
    private boolean mSmooth; // indicate whether the bar should move smoothly.
    private int mXOffset; // the x offset
    private int mWidthOffset; // the width delta
    private OnTabsSelectListener mListener;

    public TabsGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        // read custom attrs
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabsGroup);
        try {
            mHSpacing = array.getDimensionPixelSize(R.styleable.TabsGroup_horizontal_spacing,
                    getResources().getDimensionPixelSize(R.dimen.default_horizontal_spacing));
            mBarColor = array.getColor(R.styleable.TabsGroup_bar_color,
                    getResources().getColor(R.color.default_bar_color));
            mBarHeight = array.getDimensionPixelSize(R.styleable.TabsGroup_bar_height,
                    getResources().getDimensionPixelSize(R.dimen.default_bar_height));
            mSmooth = array.getBoolean(R.styleable.TabsGroup_smooth_mode, false);
        } finally {
            array.recycle();
        }

        // add the bar
        mBar = new View(context);
        mBar.setBackgroundColor(mBarColor);
        LayoutParams params = new LayoutParams(80, mBarHeight);
        this.addView(mBar, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // used to calculate children position.
        int width = getPaddingLeft();
        int height = getPaddingTop();
        final int childCnt = getChildCount();
        tabCnt = childCnt - 1;

        for (int i = 0; i < childCnt; ++i) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            // skip the bar
            if (child == mBar) {
                continue;
            }

            // set click listener
            final int childPos = i - 1;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onTabSelect(currTab, childPos, v);
                    }
                    setCurrentTab(childPos);
                }
            });
            // set position
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            width += params.leftMargin;
            params.x = width;
            params.y = params.topMargin;
            // update next view's position
            width += child.getMeasuredWidth() + params.rightMargin + mHSpacing;
            int tempHeight = 0;
            tempHeight = params.topMargin + params.bottomMargin + child.getMeasuredHeight();
            height = tempHeight > height ? tempHeight : height;
        }

        // get whole size of the layout
        width += getPaddingRight();
        height += getPaddingBottom();

        // set the bar
        LayoutParams barParams = (LayoutParams) mBar.getLayoutParams();
        View tabView = getChildAt(currTab + 1);// the bar itself is the first child
        LayoutParams tabParams = (LayoutParams) tabView.getLayoutParams();
        barParams.x = tabParams.x + mXOffset;
        barParams.y = height;
        barParams.width = tabView.getMeasuredWidth() + mWidthOffset;
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
     * Set the current tab index.
     *
     * @param i the new position
     * @return the previous position.
     */
    public int setCurrentTab(int i) {
        int old = currTab;
        if (i >= 0 && i <= tabCnt) {
            currTab = i;
            // if smooth is on, the we animate the x offset
            if (mSmooth) {
                final int maxOffset = ((LayoutParams) getChildAt(old + 1).getLayoutParams()).x -
                        ((LayoutParams) getChildAt(i + 1).getLayoutParams()).x;
                final int widthDelta = getChildAt(old).getMeasuredWidth() - getChildAt(i).getMeasuredWidth();
                ValueAnimator animator = ValueAnimator.ofInt(0, 1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float frac = animation.getAnimatedFraction();
                        mXOffset = (int) (maxOffset * (1.0f - frac));
                        mWidthOffset = (int) (widthDelta * (1.0f - frac));
                        requestLayout();
                    }
                });
                animator.start();
            }
            requestLayout();
        }
        return old;
    }

    public void setSmoothMode(boolean smooth) {
        mSmooth = smooth;
    }

    public void setOnTabSelectListener(OnTabsSelectListener listener) {
        mListener = listener;
    }

    /**
     * The tab event interface
     */
    public interface OnTabsSelectListener {
        // index starts from 0.
        void onTabSelect(int from, int to, View view);
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

}


