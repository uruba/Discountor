package cz.uruba.discountor.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cz.uruba.discountor.helpers.SavedDiscountsHelper;
import cz.uruba.discountor.models.item_definitions.DiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItemPercentage;

public class ModelDiscountItem {
    SavedDiscountsHelper helper;

    public ModelDiscountItem(Context context) {
        this.helper = SavedDiscountsHelper.getInstance(context);
    }

    public static ArrayList<DiscountItem> getAll(Context context, boolean desc) {
        SavedDiscountsHelper helper = SavedDiscountsHelper.getInstance(context);

        ArrayList<DiscountItem> items = (ArrayList<DiscountItem>) helper.retrieveAll();

        Collections.sort(items, (discountItem, discountItem2) -> Integer.signum(discountItem2.getDateCreatedUNIXTimestamp() - discountItem.getDateCreatedUNIXTimestamp()));

        if (!desc) {
            Collections.reverse(items);
        }

        return items;
    }

    public void addNew(DiscountItem insertedItem) {
        helper.insertNew(insertedItem);
    }

}
