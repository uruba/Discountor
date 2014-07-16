package cz.uruba.slevnicek;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.os.Build;
import android.preference.PreferenceManager;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new DiscountCalculatorFragment()).commit();
		}
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		ArrayList<String> itemList = new ArrayList<String>();
		for(String[] item: Values.modes){
			itemList.add(this.getString(getResources().getIdentifier(item[1], "string", MainActivity.this.getPackageName())));
		}
		SpinnerAdapter spinAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
		
		actionBar.setListNavigationCallbacks(spinAdapt, this);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	 //   getSupportFragmentManager().putFragment(outState, "mainFragment", mainFragment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
            case R.id.action_list_discounts:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new SavedDiscountsFragment())
                        .addToBackStack(null)
                        .commit();

                break;
			case R.id.action_settings:
				
				Class<?> targetClass;			
				targetClass = Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB ?
						SettingsActivityAPI11plus.class :
						SettingsActivityAPI8to10.class;				
				startActivity(new Intent(this, targetClass));
				
				break;
			default: return super.onOptionsItemSelected(item);
		}

		return true;
		
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		
		Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
			
		return false;
	}

}
