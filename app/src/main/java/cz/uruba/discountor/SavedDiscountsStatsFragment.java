package cz.uruba.discountor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cz.uruba.discountor.models.ModelDiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItem;

/**
 * Created by Vaclav on 8.11.2014.
 */

public class SavedDiscountsStatsFragment extends Fragment {
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_stats_saved_discounts, container,
                false);

        return rootView;
    }

    private ArrayList<DiscountItem> getAllItems(){
        return ModelDiscountItem.getAll(this.getActivity(), true);
    }
}
