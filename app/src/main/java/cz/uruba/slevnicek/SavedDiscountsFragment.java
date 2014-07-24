package cz.uruba.slevnicek;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.uruba.slevnicek.models.DiscountItem;
import cz.uruba.slevnicek.models.ModelDiscountItem;
import cz.uruba.slevnicek.utils.CurrencyProvider;

/**
 * Created by Vaclav on 4.7.2014.
 */



public class SavedDiscountsFragment extends Fragment {
    private ListView listView;
    private ArrayList<DiscountItem> discountList;

    public SavedDiscountsFragment(){    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // modify Action Bar
        android.support.v7.app.ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        setHasOptionsMenu(true);

        // initialize main view container
        View rootView = inflater.inflate(R.layout.fragment_list_saved_discounts, container,
                false);

        // initialize list view
        listView = (ListView) rootView.findViewById(R.id.listSavedDiscounts);

        this.listAll();

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);

        return rootView;
    }

    @Override
    public void onDestroyView(){
        // de-modify Action Bar
        ((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        super.onDestroyView();
    }

    public void listAll(){
        discountList = ModelDiscountItem.getAll();
        final DiscountListArrayAdapter adapter = new DiscountListArrayAdapter(this.getActivity(), discountList);

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class DiscountListArrayAdapter extends ArrayAdapter<DiscountItem>{
        private final Context context;
        private final ArrayList<DiscountItem> values;

        public DiscountListArrayAdapter(Context context, ArrayList<DiscountItem> values){
            super(context, R.layout.item_list_saved_discounts, values);

            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowSingle = inflater.inflate(R.layout.item_list_saved_discounts, parent, false);

            TextView itemName = (TextView) rowSingle.findViewById(R.id.name);
            TextView itemPriceBefore = (TextView) rowSingle.findViewById(R.id.price_before);
            TextView itemPriceAfter = (TextView) rowSingle.findViewById(R.id.price_after);
            TextView itemDiscountPercentage = (TextView) rowSingle.findViewById(R.id.discount_percentage);
            TextView itemSavings = (TextView) rowSingle.findViewById(R.id.savings);

            DiscountItem item = values.get(position);

            itemName.setText(item.getDiscountName());
            itemPriceBefore.setText(CurrencyProvider.getFormattedAmount(item.getPriceBefore()));
            itemPriceAfter.setText(CurrencyProvider.getFormattedAmount(item.getPriceAfter()));
            itemDiscountPercentage.setText(String.valueOf(item.getDiscountValue()));
            itemSavings.setText(CurrencyProvider.getFormattedAmount(item.getSavings()));

            return rowSingle;
        }
    }
}
