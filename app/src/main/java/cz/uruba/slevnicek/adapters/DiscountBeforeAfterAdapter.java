package cz.uruba.slevnicek.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by Temp on 20.7.2014.
 */
public class DiscountBeforeAfterAdapter extends ArrayAdapter implements SpinnerAdapter {
    Context context;
    int resource;
    ArrayList objects;

    public DiscountBeforeAfterAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

}
