package cz.uruba.discountor.listeners;

import cz.uruba.discountor.DiscountCalculatorFragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class ListenerSelectDiscount implements Spinner.OnItemSelectedListener {
	DiscountCalculatorFragment fragment;
	
	
	public ListenerSelectDiscount(DiscountCalculatorFragment fragment){
		this.fragment = fragment;
	}

    /*
	public void onCheckedChanged(RadioGroup radioGroup, int idButtonChecked){
		fragment.setTextBeforeAfter();
		fragment.setTextResult();
	}
	*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        fragment.setTextBeforeAfter(id);
        fragment.setTextResult();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
