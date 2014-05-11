package cz.uruba.slevnicek;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

// Min. API version = 11
@SuppressLint("NewApi")
public class SettingsActivityAPI11plus extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, new SettingsFragment())
			.commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		switch(id){
			case android.R.id.home:
				finish();
			break;
			default: return super.onOptionsItemSelected(item);
		}
		
		
		return true;
	}
	
}

