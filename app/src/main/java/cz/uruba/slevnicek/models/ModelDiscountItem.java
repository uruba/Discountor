package cz.uruba.slevnicek.models;

import android.content.Context;

import java.util.ArrayList;

import cz.uruba.slevnicek.helpers.SavedDiscountsHelper;
import cz.uruba.slevnicek.models.item_definitions.DiscountItem;

/**
 * Created by Vaclav on 6.7.2014.
 */
public class ModelDiscountItem {
    SavedDiscountsHelper helper;

    public ModelDiscountItem(Context context){
        this.helper = new SavedDiscountsHelper(context);
    }

    public static ArrayList<DiscountItem> getAll(Context context){
        SavedDiscountsHelper helper = new SavedDiscountsHelper(context);

        ArrayList<DiscountItem> items = (ArrayList) helper.retrieveAll();

        /* ...
        items.add(new DiscountItem(true, 25.0, 5, "Hovno"));
        items.add(new DiscountItem(false, 125.3, 25));
        items.add(new DiscountItem(true, 10.0, 65, "Prdel"));
        */

        return items;
    }



    public void addNew(float price, boolean isPriceBeforeAfter, int discount, String displayedName){
        DiscountItem insertedItem = new DiscountItem(isPriceBeforeAfter, price, discount, displayedName);

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
