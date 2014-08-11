package cz.uruba.discountor;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cz.uruba.discountor.listeners.ListenerListSavedDiscountOnItemLongClick;
import cz.uruba.discountor.models.item_definitions.DiscountItem;
import cz.uruba.discountor.models.ModelDiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItemDifference;
import cz.uruba.discountor.models.item_definitions.DiscountItemPercentage;
import cz.uruba.discountor.utils.CurrencyProvider;

/**
 * Created by Vaclav on 4.7.2014.
 */



public class SavedDiscountsFragment extends Fragment {
    private ListView listView;
    private ArrayList<DiscountItem> discountList;
    private View rootView;

    public SavedDiscountsFragment(){    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // initialize main view container
        rootView = inflater.inflate(R.layout.fragment_list_saved_discounts, container,
                false);

        // initialize list view
        listView = (ListView) rootView.findViewById(R.id.listSavedDiscounts);
        listView.setOnItemLongClickListener(new ListenerListSavedDiscountOnItemLongClick(this));
        listView.setEmptyView(rootView.findViewById(R.id.emptyListSavedDiscounts));

        this.listAll();

        return rootView;
    }

    public void listAll(){
        discountList = ModelDiscountItem.getAll(this.getActivity(), true);

        DiscountListArrayAdapter adapter = new DiscountListArrayAdapter(this.getActivity(), discountList);

        listView.setAdapter(adapter);
    }

    public ListView getListView(){
        return this.listView;
    }


    public class DiscountListArrayAdapter extends ArrayAdapter<DiscountItem>{
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

            String discountName = item.getDiscountName();
            if(discountName != null){
                itemName.setText(discountName);
                itemName.setVisibility(View.VISIBLE);
            }

            String strPriceBefore = CurrencyProvider.getFormattedAmount(item.getPriceBefore(), true);
            String strPriceAfter = CurrencyProvider.getFormattedAmount(item.getPriceAfter(), true);
            String strDiscountPercentage = String.valueOf(
                    item instanceof DiscountItemPercentage ?
                            ((DiscountItemPercentage) item).getDiscountValue() :
                            ((DiscountItemDifference) item).getPercentageDiscount())
                    .concat(getResources().getString(R.string.percent_off));
            String strSavings = CurrencyProvider.getFormattedAmount(
                                    item.getSavings()
                                    , true);


                    itemPriceBefore.setPaintFlags(itemPriceBefore.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            itemPriceBefore.setText(strPriceBefore);

            itemPriceAfter.setText(strPriceAfter);

            itemDiscountPercentage.setText(strDiscountPercentage);

            itemSavings.setText(strSavings);

            return rowSingle;
        }
    }
}
