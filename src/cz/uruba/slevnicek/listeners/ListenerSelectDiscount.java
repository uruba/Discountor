package cz.uruba.slevnicek.listeners;

import cz.uruba.slevnicek.DiscountCalculatorFragment;
import android.widget.RadioGroup;

public class ListenerSelectDiscount implements RadioGroup.OnCheckedChangeListener {
	DiscountCalculatorFragment fragment;
	
	
	public ListenerSelectDiscount(DiscountCalculatorFragment fragment){
		this.fragment = fragment;
	}
	
	public void onCheckedChanged(RadioGroup radioGroup, int idButtonChecked){
		fragment.setTextBeforeAfter();
		fragment.setTextResult();
	}
}
