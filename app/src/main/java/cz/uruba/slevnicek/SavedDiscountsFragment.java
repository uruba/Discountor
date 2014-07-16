package cz.uruba.slevnicek;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.uruba.slevnicek.models.DiscountItem;
import cz.uruba.slevnicek.models.ModelDiscountItem;

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
        ((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initialize main view container
        View rootView = inflater.inflate(R.layout.fragment_list_saved_discounts, container,
                false);

        // initialize list view
        listView = (ListView) rootView.findViewById(R.id.listSavedDiscounts);

        this.listAll();


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
            itemPriceBefore.setText(String.valueOf(item.getPriceBefore()));
            itemPriceAfter.setText(String.valueOf(item.getPriceAfter()));
            itemDiscountPercentage.setText(String.valueOf(item.getDiscountValue()));
            itemSavings.setText(String.valueOf(item.getSavings()));

            return rowSingle;
        }
    }
}
