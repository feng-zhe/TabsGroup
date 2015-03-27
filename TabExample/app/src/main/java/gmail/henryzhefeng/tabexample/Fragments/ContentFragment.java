package gmail.henryzhefeng.tabexample.Fragments;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gmail.henryzhefeng.tabexample.R;

/**
 * Created by 哲 on 3/27/2015.
 */
public class ContentFragment extends Fragment {

    private View mRoot;
    private int mColorRes = android.R.color.white;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.content_fragment_layout, container, false);
        mRoot.setBackgroundColor(getResources().getColor(mColorRes));
        return mRoot;
    }

    public void setBgColorId(@ColorRes int colorRes) {
        mColorRes = colorRes;
        if (mRoot != null) {
            mRoot.setBackgroundColor(getResources().getColor(colorRes));
        }
    }

}
