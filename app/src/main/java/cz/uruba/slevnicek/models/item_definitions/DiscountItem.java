package cz.uruba.slevnicek.models.item_definitions;

import cz.uruba.slevnicek.utils.DisCalc;

/**
 * Created by Vaclav on 6.7.2014.
 */
public class DiscountItem {
    final double DEFAULT_DOUBLE = 0.0d;

    private double priceBefore, priceAfter;
    private int discountValue;
    private boolean isPriceBefore;
    private String discountName;

    // START constructors
    public DiscountItem(boolean isPriceBefore, double priceValue, int discountValue){
        this.isPriceBefore = isPriceBefore;

        if(isPriceBefore) {
            this.priceBefore = priceValue;
        } else {
            this.priceAfter = priceValue;
        }

        this.discountValue = discountValue;
    }

    public DiscountItem(boolean isPriceBefore, double priceValue, int discountValue, String discountName){
        this(isPriceBefore, priceValue, discountValue);
        this.setDiscountName(discountName);
    }

    // END constructors

    // START Getters and setters

    public double getPriceBefore() {
        if (priceBefore == DEFAULT_DOUBLE && !isPriceBefore){
           return DisCalc.origFromPercentage(priceAfter, discountValue);
        }

        return priceBefore;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public double getPriceAfter() {
        if (priceAfter == DEFAULT_DOUBLE){
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

    // END Getters and setters
}
