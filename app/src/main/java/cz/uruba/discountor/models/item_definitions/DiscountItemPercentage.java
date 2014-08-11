package cz.uruba.discountor.models.item_definitions;

import cz.uruba.discountor.constants.Constants;
import cz.uruba.discountor.utils.DisCalc;

/**
 * Created by Vaclav on 6.7.2014.
 */
public class DiscountItemPercentage extends DiscountItem {

    private double priceBefore, priceAfter;
    private int discountValue;
    private boolean isPriceBefore;


    // START constructors
    public DiscountItemPercentage(boolean isPriceBefore, double priceValue, int discountValue){
        this.isPriceBefore = isPriceBefore;

        if(isPriceBefore) {
            this.priceBefore = priceValue;
        } else {
            this.priceAfter = priceValue;
        }

        this.discountValue = discountValue;
        this.db_id = DEFAULT_DB_ID;
    }

    public DiscountItemPercentage(boolean isPriceBefore, double priceValue, int discountValue, String discountName){
        this(isPriceBefore, priceValue, discountValue);
        this.setDiscountName(discountName);
    }

    public DiscountItemPercentage(int db_id, boolean isPriceBefore, double priceValue, int discountValue, String discountName){
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

    public boolean isPriceBefore() {
        return isPriceBefore;
    }

    public double getSavings(){
        return DisCalc.amountSavedFromOrigAndDisc(this.getPriceBefore(), this.getPriceAfter());
    }
    // END Getters and setters
}
