package cz.uruba.discountor.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cz.uruba.discountor.models.item_definitions.DiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItemDifference;
import cz.uruba.discountor.models.item_definitions.DiscountItemPercentage;

/**
 * Created by Václav on 24.7.2014.
 */
public class SavedDiscountsHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private SQLiteDatabase db;

    private static final String DB_NAME = "discountor_data";

    private static final String TABLE_PERCENTAGE_DISCOUNT_RECORDS = "percentage_discount_records";
    private static final String COLUMN_ID = "_id";
    private static final String PRICE_BEFORE = "price_before";
    private static final String PRICE_VALUE = "price_value";
    private static final String DISCOUNT_VALUE = "discount_value";
    private static final String DISPLAYED_NAME = "displayed_name";
    private static final String DATE_CREATED = "date_created";

    private static final String TABLE_DIFFERENCE_DISCOUNT_RECORDS = "difference_discount_records";
    private static final String PRICE_BEFORE_VALUE = "price_before_value";
    private static final String PRICE_AFTER_VALUE = "price_after_value";


    private static final String QUERY_CREATE_TABLE_PERCENTAGE_DISCOUNTS
            = "CREATE TABLE " + TABLE_PERCENTAGE_DISCOUNT_RECORDS
            + "("
               + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
               + PRICE_BEFORE + " INTEGER, "
               + PRICE_VALUE + " TEXT, "
               + DISCOUNT_VALUE + " INTEGER, "
               + DISPLAYED_NAME + " TEXT,"
               + DATE_CREATED + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    private static final String QUERY_READ_ALL_PERCENTAGE_DISCOUNTS
            = "SELECT "
            + COLUMN_ID + ", "
            + PRICE_BEFORE + ", "
            + PRICE_VALUE + ", "
            + DISCOUNT_VALUE + ", "
            + DISPLAYED_NAME + ", "
            + "strftime('%s', " + DATE_CREATED + ") "
            + "FROM " + TABLE_PERCENTAGE_DISCOUNT_RECORDS;


    private static final String QUERY_CREATE_TABLE_DIFFERENCE_DISCOUNTS
            = "CREATE TABLE " + TABLE_DIFFERENCE_DISCOUNT_RECORDS
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PRICE_BEFORE_VALUE + " TEXT, "
            + PRICE_AFTER_VALUE + " TEXT, "
            + DISPLAYED_NAME + " TEXT,"
            + DATE_CREATED + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    private static final String QUERY_READ_ALL_DIFFERENCE_DISCOUNTS
            = "SELECT "
            + COLUMN_ID + ", "
            + PRICE_BEFORE_VALUE + ", "
            + PRICE_AFTER_VALUE + ", "
            + DISPLAYED_NAME + ", "
            + "strftime('%s', " + DATE_CREATED + ") "
            + "FROM " + TABLE_DIFFERENCE_DISCOUNT_RECORDS;


    private static final String TABLE_VERSION_ONE_DEPRECATED = "discount_records";

    private static SavedDiscountsHelper mDBHelper;

    public static SavedDiscountsHelper getInstance(Context ctx) {

        if (mDBHelper == null) { //this will ensure no multiple instances out there.
            mDBHelper = new SavedDiscountsHelper(ctx.getApplicationContext());
        }

        return mDBHelper;
    }

    private SavedDiscountsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        this.db = getWritableDatabase();
    }

    private SavedDiscountsHelper(Context context){
        this(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(QUERY_CREATE_TABLE_PERCENTAGE_DISCOUNTS);
        db.execSQL(QUERY_CREATE_TABLE_DIFFERENCE_DISCOUNTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(SavedDiscountsHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VERSION_ONE_DEPRECATED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERCENTAGE_DISCOUNT_RECORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIFFERENCE_DISCOUNT_RECORDS);
        onCreate(db);
    }

    public void insertNew(DiscountItem item){
       if(item instanceof DiscountItemPercentage) {
           this.insertNew((DiscountItemPercentage) item);
       }
       if(item instanceof DiscountItemDifference) {
           this.insertNew((DiscountItemDifference) item);
       }

       return;
    }

    public void insertNew(DiscountItemPercentage item){
        ContentValues insertedValues = new ContentValues();
        insertedValues.put(PRICE_BEFORE, item.isPriceBefore() ?
                                            1 :
                                            0);
        insertedValues.put(PRICE_VALUE, item.isPriceBefore() ?
                                            item.getPriceBefore() :
                                            item.getPriceAfter());
        insertedValues.put(DISCOUNT_VALUE, item.getDiscountValue());
        insertedValues.put(DISPLAYED_NAME, item.getDiscountName());

        db.insert(TABLE_PERCENTAGE_DISCOUNT_RECORDS, null, insertedValues);
    }

    public void insertNew(DiscountItemDifference item){
        ContentValues insertedValues = new ContentValues();
        insertedValues.put(PRICE_BEFORE_VALUE, item.getPriceBefore());
        insertedValues.put(PRICE_AFTER_VALUE, item.getPriceAfter());
        insertedValues.put(DISPLAYED_NAME, item.getDiscountName());

        db.insert(TABLE_DIFFERENCE_DISCOUNT_RECORDS, null, insertedValues);
    }

    public List<DiscountItem> retrieveAll(){
        List<DiscountItem> discountItems = new ArrayList<DiscountItem>();

        String query = QUERY_READ_ALL_PERCENTAGE_DISCOUNTS;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                DiscountItemPercentage discountItem = new DiscountItemPercentage(cursor.getInt(0),
                                                             cursor.getInt(1) > 0,
                                                             cursor.getDouble(2),
                                                             cursor.getInt(3),
                                                             cursor.getString(4),
                                                             cursor.getInt(5)
                                                             );
                discountItems.add(discountItem);
            } while (cursor.moveToNext());
        }

        cursor.close();


        query = QUERY_READ_ALL_DIFFERENCE_DISCOUNTS;

        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                DiscountItemDifference discountItem = new DiscountItemDifference(cursor.getInt(0),
                        cursor.getDouble(1),
                        cursor.getDouble(2),
                        cursor.getString(3),
                        cursor.getInt(4));
                discountItems.add(discountItem);
            } while (cursor.moveToNext());
        }

        cursor.close();


        return discountItems;
    }

    private void deleteByID(int id, String tableName){
        db.delete(TABLE_PERCENTAGE_DISCOUNT_RECORDS,
                    COLUMN_ID + " = ?",
                    new String[] { String.valueOf(id) } );
    }

    public void deleteByID(DiscountItem item){
        String tableName;

        if(item instanceof DiscountItemPercentage){
            tableName = TABLE_PERCENTAGE_DISCOUNT_RECORDS;
        }
        else
        if(item instanceof DiscountItemDifference){
            tableName = TABLE_DIFFERENCE_DISCOUNT_RECORDS;
        }
        else {
            return;
        }


        deleteByID(item.getDB_ID(), tableName);
    }

}
