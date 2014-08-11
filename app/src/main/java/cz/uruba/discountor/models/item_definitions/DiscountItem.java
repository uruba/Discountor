package cz.uruba.discountor.models.item_definitions;

/**
 * Created by vasek on 11/08/14.
 */
public abstract class DiscountItem {
    final int DEFAULT_DB_ID = -1;

    protected int db_id;
    protected String discountName;

    public int getDB_ID(){
        return db_id;
    }

    public void setDiscountName(String name) {
        this.discountName = name;
    }
    public String getDiscountName() {
        return discountName;
    }

    abstract public double getPriceBefore();
    abstract public double getPriceAfter();

}
