package cz.uruba.slevnicek.models;

import java.util.ArrayList;

/**
 * Created by Vaclav on 6.7.2014.
 */
public class ModelDiscountItem {
    public ModelDiscountItem(){

    }

    public static ArrayList<DiscountItem> getAll(){
        ArrayList<DiscountItem> items = new ArrayList<DiscountItem>();

        /* ... */
        items.add(new DiscountItem(25.0, true, 5, "Hovno"));
        items.add(new DiscountItem(125.3, false, 25));
        items.add(new DiscountItem(10.0, true, 65, "Prdel"));

        return items;
    }
/*
    public static DiscountItem getById(int id){
        DiscountItem item = new DiscountItem();
        ...

        return item;
    }
 */
}
