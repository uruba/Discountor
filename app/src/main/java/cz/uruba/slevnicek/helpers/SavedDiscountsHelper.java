package cz.uruba.slevnicek.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Václav on 24.7.2014.
 */
public class SavedDiscountsHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "discountRecords";


    private static final String COLUMN_ID = "_id";
    private static final String PRICE_BEFORE = "price_before";
    private static final String PRICE_VALUE = "price_value";
    private static final String DISCOUNT_VALUE = "discount_value";
    private static final String DISPLAYED_NAME = "displayed_name";
    private static final String DATE_CREATED = "date_created";


    public SavedDiscountsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
