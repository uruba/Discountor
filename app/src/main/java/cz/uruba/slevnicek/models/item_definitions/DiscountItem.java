package cz.uruba.slevnicek.models.item_definitions;

import cz.uruba.slevnicek.constants.Constants;
import cz.uruba.slevnicek.utils.DisCalc;

/**
 * Created by Vaclav on 6.7.2014.
 */
public class DiscountItem {

    final int DEFAULT_DB_ID = -1;

    private double priceBefore, priceAfter;
    private int discountValue;
    private boolean isPriceBefore;
    private String discountName;
    private int db_id;

    // START constructors
    public DiscountItem(boolean isPriceBefore, double priceValue, int discountValue){
        this.isPriceBefore = isPriceBefore;

        if(isPriceBefore) {
            this.priceBefore = priceValue;
        } else {
            this.priceAfter = priceValue;
        }

        this.discountValue = discountValue;
        this.db_id = DEFAULT_DB_ID;
    }

    public DiscountItem(boolean isPriceBefore, double priceValue, int discountValue, String discountName){
        this(isPriceBefore, priceValue, discountValue);
        this.setDiscountName(discountName);
    }

    public DiscountItem(int db_id, boolean isPriceBefore, double priceValue, int discountValue, String discountName){
        this(isPriceBefore, priceValue, discountValue, discountName);
        this.db_id = db_id;
    }

    // END constructors

    // START Getters and setters

    public double getPriceBefore() {
        if (priceBefore == Constants.DEFAULT_DOUBLE && !isPriceBefore){
           return DisCalc.origFromPercentage(priceAfter, discountValue);
        }

        return priceBefore;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public double getPriceAfter() {
        if (priceAfter == Constants.DEFAULT_DOUBLE){
            return DisCalc.discFromPercentage(priceBefore, discountValue);
        }

        return priceAfter;
    }

    public String getDiscountName() {
        return discountName;
    }

    public boolean isPriceBefore() {
        return isPriceBefore;
    }

    public void setDiscountName(String name) {
        this.discountName = name;
    }

    public double getSavings(){
        return DisCalc.amountSavedFromOrigAndDisc(this.getPriceBefore(), this.getPriceAfter());
    }

    public int getDB_ID(){
        return db_id;
    }
    // END Getters and setters
}
