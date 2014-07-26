package cz.uruba.slevnicek.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Václav on 24.7.2014.
 */
public class SavedDiscountsHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "discountor_data";

    private static final String TABLE_DISCOUNT_RECORDS = "discount_records";
    private static final String COLUMN_ID = "_id";
    private static final String PRICE_BEFORE = "price_before";
    private static final String PRICE_VALUE = "price_value";
    private static final String DISCOUNT_VALUE = "discount_value";
    private static final String DISPLAYED_NAME = "displayed_name";
    private static final String DATE_CREATED = "date_created";


    private static final String QUERY_CREATE_DB
            = "CREATE TABLE" + TABLE_DISCOUNT_RECORDS
            + "("
               + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
               + PRICE_BEFORE
               + PRICE_VALUE
               + DISCOUNT_VALUE
               + DISPLAYED_NAME + " TEXT,"
               + DATE_CREATED
            + ")";



    public SavedDiscountsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SavedDiscountsHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
