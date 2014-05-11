package cz.uruba.slevnicek;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class SettingsActivityAPI8to10 
		extends PreferenceActivity 
		implements OnSharedPreferenceChangeListener {
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		
		// show the current value in the settings screen
	    for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
	      updateSummary(getPreferenceScreen().getPreference(i));
	    }
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onResume() {
	  super.onResume();
	  getPreferenceScreen().getSharedPreferences()
	      .registerOnSharedPreferenceChangeListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onPause() {
	  super.onPause();
	  getPreferenceScreen().getSharedPreferences()
	      .unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		updateSummary(findPreference(key));
	}
	
	
	private void updateSummary(Preference p) {
		if (p instanceof ListPreference) {
		  p.setSummary(((ListPreference) p).getEntry());
		}
	}
	
}
