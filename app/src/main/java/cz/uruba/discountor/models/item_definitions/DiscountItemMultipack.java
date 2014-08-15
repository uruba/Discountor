package cz.uruba.discountor.models.item_definitions;

import cz.uruba.discountor.constants.Constants;
import cz.uruba.discountor.utils.DisCalc;

/**
 * Created by vasek on 15/08/14.
 */
public class DiscountItemMultipack extends DiscountItem {

    private double priceSingle, priceMulti, percentageDiscount;
    private int pcsMulti;

    public DiscountItemMultipack(double priceSingle, double priceMulti, int pcsMulti){
        this.priceSingle = priceSingle;
        this.priceMulti = priceMulti;

        this.pcsMulti = pcsMulti;

        this.percentageDiscount = DisCalc.roundToDecimals(
                                        DisCalc
                                                .percentageFromOrigAndDisc(this.getPriceBefore(), priceMulti),
                                        2);
        this.db_id = DEFAULT_DB_ID;
    }

    public DiscountItemMultipack(int db_id, double priceSingle, double priceMulti, int pcsMulti, String discountName){
        this(priceSingle, priceMulti, pcsMulti);

        this.db_id = db_id;
        this.setDiscountName(discountName);
    }

    public double getPriceSingle() {
        return this.priceSingle;
    }

    public double getPriceMulti() {
        return this.priceMulti;
    }

    public double getPriceMultiSinglePc(){
        return this.priceMulti / pcsMulti;
    }

    public double getSavingsAltogether() {
        return DisCalc
                .amountSavedFromOrigAndDisc(this.getPriceBefore(), priceMulti);
    }

    public double getSavingsAltogetherPercentage() {
        return this.percentageDiscount;
    }

    public double getSavingsPerPc(){
        return this.getSavingsAltogether() / pcsMulti;
    }

    public int getNoOfItems() { return this.pcsMulti; }

    @Override
    public double getPriceBefore() {
        return priceSingle * pcsMulti;

    }

    @Override
    public double getPriceAfter() {
        return getPriceMulti();
    }

    @Override
    public double getSavings() {
        return getSavingsAltogether();
    }
}
