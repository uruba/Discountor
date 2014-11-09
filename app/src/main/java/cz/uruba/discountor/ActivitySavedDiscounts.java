package cz.uruba.discountor;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import cz.uruba.discountor.listeners.ListenerSavedDiscountsTab;

/**
 * Created by vasek on 10/08/14.
 */
public class ActivitySavedDiscounts extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_discounts);

        initializeTabs();
    }

    private void initializeTabs(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        int fragmentContainer = R.id.container;

        ActionBar.Tab discountListTab = actionBar
                .newTab()
                .setText(getResources().getString(R.string.saved_discounts_list_tab_label))
                .setTabListener(ListenerSavedDiscountsTab.createInstanceList(fragmentContainer, this, "discount_list"));

        ActionBar.Tab discountStatsTab = actionBar
                .newTab()
                .setText(getResources().getString(R.string.saved_discounts_stats_tab_label))
                .setTabListener(ListenerSavedDiscountsTab.createInstanceStats(fragmentContainer, this, "discount_stats"));

        actionBar.addTab(discountListTab);
        actionBar.addTab(discountStatsTab);

        actionBar.selectTab(discountListTab);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
