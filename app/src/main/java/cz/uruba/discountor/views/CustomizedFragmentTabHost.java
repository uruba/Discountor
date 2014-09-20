package cz.uruba.discountor.views;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

/**
 * Created by vasek on 23/08/14.
 */
public class CustomizedFragmentTabHost extends FragmentTabHost {

    public CustomizedFragmentTabHost(Context context) {
        super(context);
        setFocusableFalse();
    }

    public CustomizedFragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusableFalse();
    }

    private void setFocusableFalse(){
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

}
