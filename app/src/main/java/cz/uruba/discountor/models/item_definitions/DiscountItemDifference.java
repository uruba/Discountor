package cz.uruba.discountor.models.item_definitions;

import cz.uruba.discountor.utils.DisCalc;

/**
 * Created by vasek on 11/08/14.
 */
public class DiscountItemDifference extends DiscountItem {

    private double priceBefore, priceAfter, percentageDiscount;

    public DiscountItemDifference(double priceBefore, double priceAfter){
        this.priceBefore = priceBefore;
        this.priceAfter = priceAfter;

        this.percentageDiscount = DisCalc.roundToDecimals(
                                            DisCalc
                                                .percentageFromOrigAndDisc(priceBefore, priceAfter),
                                            2);
        this.db_id = DEFAULT_DB_ID;
    }

    public DiscountItemDifference(int db_id, double priceBefore, double priceAfter, String discountName){
        this(priceBefore, priceAfter);
        this.db_id = db_id;
        this.setDiscountName(discountName);
    }


    public double getPriceBefore(){
        return this.priceBefore;
    }

    public double getPriceAfter(){
        return this.priceAfter;
    }

    public double getPercentageDiscount() {return this.percentageDiscount; }

    public double getSavings(){
        return this.priceBefore - this.priceAfter;
    }
}
