package cz.uruba.discountor.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import cz.uruba.discountor.R;


/**
 * Created by Temp on 5.8.2014.
 */
public class ActionBarSpinnerAdapter extends ArrayAdapter<String> implements SpinnerAdapter {

    private Context context;

    public ActionBarSpinnerAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setTextColor(context.getResources().getColor(R.color.white));

        return textView;
    }


}
