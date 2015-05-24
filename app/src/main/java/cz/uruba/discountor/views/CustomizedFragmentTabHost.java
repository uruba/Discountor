package cz.uruba.discountor.views;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TabWidget;

import java.util.ArrayList;

/**
 * Created by vasek on 23/08/14.
 */
public class CustomizedFragmentTabHost extends FragmentTabHost {

    private ArrayList<String> fragmentTags;

    public CustomizedFragmentTabHost(Context context) {
        super(context);
        initCustomizedFragmentTabHost();
    }

    public CustomizedFragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomizedFragmentTabHost();
    }

    private void initCustomizedFragmentTabHost() {
        setFocusableFalse();
        fragmentTags = new ArrayList<>();
    }

    private void setFocusableFalse() {
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    @Override
    public TabSpec newTabSpec(String tag) {
        fragmentTags.add(tag);
        return super.newTabSpec(tag);
    }

    public ArrayList<String> getFragmentTags() {
        return this.fragmentTags;
    }

}
