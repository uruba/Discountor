package cz.uruba.discountor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;

import cz.uruba.discountor.models.ModelDiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItem;

/**
 * Created by Vaclav on 8.11.2014.
 */

public class SavedDiscountsStatsFragment extends Fragment {
    private View rootView;
    private XYPlot chartView;

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

        chartView = (XYPlot) rootView.findViewById(R.id.chart_container);

        renderChart(getGraphSeriesFromDiscountItems(getAllItems()));

        return rootView;
    }

    private ArrayList<DiscountItem> getAllItems() {
        return ModelDiscountItem.getAll(this.getActivity(), false);
    }

    private XYSeries getGraphSeriesFromDiscountItems(ArrayList<DiscountItem> discountItems) {
        ArrayList<Integer> timeList = new ArrayList<Integer>();
        ArrayList<Double> savingsList = new ArrayList<Double>();

        for (DiscountItem item : discountItems) {
            timeList.add(item.getDateCreatedUNIXTimestamp());
            savingsList.add(item.getSavings());
        }

        return new SimpleXYSeries(timeList, savingsList, "");
    }

    private void renderChart(XYSeries graphSeries) {
        //noinspection ResourceType
        chartView.addSeries(graphSeries, new LineAndPointFormatter(Color.parseColor(getResources().getString(R.color.theme_red)), Color.parseColor(getResources().getString(R.color.theme_red)), Color.WHITE, new PointLabelFormatter(Color.GRAY)));
    }
}
