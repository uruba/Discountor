package cz.uruba.discountor.listeners;

import android.view.View;
import android.widget.EditText;

import cz.uruba.discountor.AbstractCalculatorFragment;

/**
 * Created by Vaclav on 20.5.2015.
 */
public class ListenerEditTextInFragmentFocus implements EditText.OnFocusChangeListener {

    private AbstractCalculatorFragment fragment;

    public ListenerEditTextInFragmentFocus(AbstractCalculatorFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!(v instanceof EditText) || v == null) {
            return;
        }

        if(hasFocus) {
            fragment.setActiveEditText((EditText) v);
        }
    }

}
