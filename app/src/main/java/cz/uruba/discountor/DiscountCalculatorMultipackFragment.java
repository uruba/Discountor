package cz.uruba.discountor;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class DiscountCalculatorMultipackFragment extends AbstractCalculatorFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		inflater.inflate(R.menu.discount, menu);
	}

	@Override
	void setTextResult() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void resetEditValues(boolean showKeyboard) {
		// TODO Auto-generated method stub
		
	}
	
	
}
