package cz.uruba.slevnicek.models;

import java.util.ArrayList;

import cz.uruba.slevnicek.models.item_definitions.DiscountItem;

/**
 * Created by Vaclav on 6.7.2014.
 */
public class ModelDiscountItem {
    public ModelDiscountItem(){

    }

    public static ArrayList<DiscountItem> getAll(){
        ArrayList<DiscountItem> items = new ArrayList<DiscountItem>();

        /* ... */
        items.add(new DiscountItem(true, 25.0, 5, "Hovno"));
        items.add(new DiscountItem(false, 125.3, 25));
        items.add(new DiscountItem(true, 10.0, 65, "Prdel"));

        return items;
    }

    public static void addNew(float price, boolean isPriceBeforeAfter, int discount, String displayedName){
        DiscountItem insertedItem = new DiscountItem(isPriceBeforeAfter, price, discount, displayedName);
    }

/*
    public static DiscountItem getById(int id){
        DiscountItem item = new DiscountItem();
        ...

        return item;
    }
 */
}
