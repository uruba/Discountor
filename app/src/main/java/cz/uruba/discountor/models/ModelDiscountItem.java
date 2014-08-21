package cz.uruba.discountor.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cz.uruba.discountor.helpers.SavedDiscountsHelper;
import cz.uruba.discountor.models.item_definitions.DiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItemPercentage;

/**
 * Created by Vaclav on 6.7.2014.
 */
public class ModelDiscountItem {
    SavedDiscountsHelper helper;

    public ModelDiscountItem(Context context){
        this.helper = SavedDiscountsHelper.getInstance(context);
    }

    public static ArrayList<DiscountItem> getAll(Context context, boolean desc){
        SavedDiscountsHelper helper = SavedDiscountsHelper.getInstance(context);

        ArrayList<DiscountItem> items = (ArrayList) helper.retrieveAll();

        Collections.sort(items, new Comparator<DiscountItem>() {
            // compare by date of creation
            @Override
            public int compare(DiscountItem discountItem, DiscountItem discountItem2) {
                return Integer.signum(discountItem.getDateCreatedUNIXTimestamp() - discountItem2.getDateCreatedUNIXTimestamp());
            }
        });

        if(desc) {
            Collections.reverse(items);
        }
        /* ...
        items.add(new DiscountItem(true, 25.0, 5, "Hovno"));
        items.add(new DiscountItem(false, 125.3, 25));
        items.add(new DiscountItem(true, 10.0, 65, "Prdel"));
        */

        return items;
    }



    public void addNew(float price, boolean isPriceBeforeAfter, int discount, String displayedName){
        DiscountItemPercentage insertedItem = new DiscountItemPercentage(isPriceBeforeAfter, price, discount, displayedName);

        this.addNew(insertedItem);
    }

    public void addNew(DiscountItem insertedItem){
        helper.insertNew(insertedItem);
    }

/*
    public static DiscountItem getById(int id){
        DiscountItem item = new DiscountItem();
        ...

        return item;
    }
 */
}
