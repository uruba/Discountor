package cz.uruba.discountor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public abstract class AbstractCalculatorFragment extends Fragment {
	protected SharedPreferences sharedPref;
    protected InputMethodManager imm;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		setHasOptionsMenu(true);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		inflater.inflate(R.menu.discount, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
			case R.id.action_reset:
				boolean showKeyboard = sharedPref.getBoolean("settings_show_keyboard_on_delete", true);				
				resetEditValues(showKeyboard);
				break;
			default: return super.onOptionsItemSelected(item);
		}

		return true;
		
	}
		
	protected void focusAndShowKeyboard(EditText edit, boolean showKeyboard){
        edit.requestFocus();


		if(showKeyboard){
			imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
            // imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
		}
	}

	abstract void setTextResult();
	abstract void resetEditValues(boolean showKeyboard);
}
