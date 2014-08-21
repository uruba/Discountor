package cz.uruba.discountor;

import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;

import cz.uruba.discountor.dialogs.AboutApplicationDialog;

public class ActivityMain extends ActionBarActivity implements ActionBar.OnNavigationListener {
	private FragmentTabHost tabHost;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

/*		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new DiscountCalculatorPercentageFragment()).commit();
		}*/
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //tabHost.setup(this, getSupportFragmentManager(), R.id.container);


        addTab("Percentage", R.drawable.icon_percentage_discount, DiscountCalculatorPercentageFragment.class);
        addTab("Difference", R.drawable.icon_difference_discount, DiscountCalculatorDifferenceFragment.class);
        addTab("Multipack", R.drawable.icon_multipack_discount, DiscountCalculatorMultipackFragment.class);


        /*
		final ActionBar actionBar = getSupportActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		ArrayList<String> itemList = new ArrayList<String>();
		for(String[] item: Values.modes){
			itemList.add(this.getString(getResources().getIdentifier(item[1], "string", ActivityMain.this.getPackageName())));
		}
		ActionBarSpinnerAdapter spinAdapt = new ActionBarSpinnerAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
		
		actionBar.setListNavigationCallbacks(spinAdapt, this);
		*/

	}

    private void addTab(String label, int drawableId, Class<?> targetClass) {
        TabHost.TabSpec spec = tabHost.newTabSpec(label);

        View tabIndicator = getLayoutInflater().inflate(R.layout.tab_custom_indicator, null);
        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
        title.setText(label);
        ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
        icon.setImageResource(drawableId);

        spec.setIndicator(tabIndicator);

        tabHost.addTab(spec, targetClass, null);
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
                Intent intent = new Intent(this, ActivitySavedDiscounts.class);
                startActivity(intent);

                break;
            case R.id.action_about:
                AboutApplicationDialog dialog = new AboutApplicationDialog();
                dialog.show(getSupportFragmentManager(), null);
                break;
            /*
			case R.id.action_settings:
				Class<?> targetClass;
				targetClass = Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB ?
						SettingsActivityAPI11plus.class :
						SettingsActivityAPI8to10.class;
				startActivity(new Intent(this, targetClass));

				break; */
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
