package cz.uruba.discountor.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import cz.uruba.discountor.DiscountCalculatorPercentageFragment;

public class ListenerSelectDiscount implements Spinner.OnItemSelectedListener {
    DiscountCalculatorPercentageFragment fragment;


    public ListenerSelectDiscount(DiscountCalculatorPercentageFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        fragment.setTextBeforeAfter(id);
        fragment.setTextResult();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
