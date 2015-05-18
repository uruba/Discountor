package cz.uruba.discountor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import cz.uruba.discountor.dialogs.AboutApplicationDialog;
import cz.uruba.discountor.views.CustomizedFragmentTabHost;

public class ActivityMain extends ActionBarActivity {
    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int mask = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        if ((mask == Configuration.SCREENLAYOUT_SIZE_LARGE) ||
                (mask == Configuration.SCREENLAYOUT_SIZE_XLARGE)) {

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        }

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        tabHost = (CustomizedFragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                return;
            }
        });


        addTab(getResources().getString(R.string.mode_percentage), R.drawable.icon_percentage_discount, DiscountCalculatorPercentageFragment.class);
        addTab(getResources().getString(R.string.mode_difference), R.drawable.icon_difference_discount, DiscountCalculatorDifferenceFragment.class);
        addTab(getResources().getString(R.string.mode_multipack), R.drawable.icon_multipack_discount, DiscountCalculatorMultipackFragment.class);

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
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;

    }

}
