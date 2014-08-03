package cz.uruba.discountor.listeners;

import cz.uruba.discountor.DiscountCalculatorFragment;
import android.text.Editable;
import android.text.TextWatcher;

public class ListenerEditTextChange implements TextWatcher {

	private DiscountCalculatorFragment fragment;

	
	public ListenerEditTextChange(DiscountCalculatorFragment fragment) {
		this.fragment = fragment;
	}
	
	
	@Override
	public void afterTextChanged(Editable editable) {		
		fragment.setTextResult();	
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		 		
	}

}
