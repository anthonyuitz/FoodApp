package com.khmkau.codeu.foodapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.khmkau.codeu.foodapp.data.FoodContract.CurrentEntry;
import com.khmkau.codeu.foodapp.data.FoodContract.ThrownEntry;
import com.khmkau.codeu.foodapp.data.FoodContract.InfoEntry;
import com.khmkau.codeu.foodapp.data.FoodContract.ConsumedEntry;

public class FoodDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "food.db";

    public FoodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_INFO_TABLE = "CREATE TABLE " + InfoEntry.TABLE_NAME + " (" +
                InfoEntry._ID + " INTEGER PRIMARY KEY," +
                InfoEntry.COLUMN_FOOD_NAME + " TEXT NOT NULL, " +
                InfoEntry.COLUMN_FOOD_GROUP + " TEXT, " +
                InfoEntry.COLUMN_SERVING_UNIT + " TEXT NOT NULL, " +
                InfoEntry.COLUMN_CALORIES + " INTEGER NOT NULL, " +
                InfoEntry.COLUMN_SATURATED_FAT + " INTEGER, " +
                InfoEntry.COLUMN_TRANS_FAT + " INTEGER, " +
                InfoEntry.COLUMN_TOTAL_FAT + " INTEGER, " +
                InfoEntry.COLUMN_CHOLESTEROL + " INTEGER, " +
                InfoEntry.COLUMN_SODIUM + " INTEGER, " +
                InfoEntry.COLUMN_TOTAL_CARBOHYDRATE + " INTEGER, " +
                InfoEntry.COLUMN_SUGAR + " INTEGER, " +
                InfoEntry.COLUMN_PROTEIN + " INTEGER, " +
                InfoEntry.COLUMN_VIT_A + " INTEGER, " +
                InfoEntry.COLUMN_VIT_D + " INTEGER, " +
                InfoEntry.COLUMN_VIT_E + " INTEGER, " +
                InfoEntry.COLUMN_VIT_C + " INTEGER, " +
                InfoEntry.COLUMN_THIAMIN + " INTEGER, " +
                InfoEntry.COLUMN_RIBOFLAVIN + " INTEGER, " +
                InfoEntry.COLUMN_NIACIN + " INTEGER, " +
                InfoEntry.COLUMN_VIT_B6 + " INTEGER, " +
                InfoEntry.COLUMN_VIT_B12 + " INTEGER, " +
                InfoEntry.COLUMN_CALCIUM + " INTEGER, " +
                InfoEntry.COLUMN_PHOSPHOROUS + " INTEGER, " +
                InfoEntry.COLUMN_MAGNESIUM + " INTEGER, " +
                InfoEntry.COLUMN_IRON + " INTEGER, " +
                InfoEntry.COLUMN_ZINC + " INTEGER, " +
                InfoEntry.COLUMN_IODINE + " INTEGER " +
                " );";

        final String SQL_CREATE_CURRENT_TABLE = "CREATE TABLE " + CurrentEntry.TABLE_NAME + " (" +
                CurrentEntry._ID + " INTEGER PRIMARY KEY," +
                CurrentEntry.COLUMN_DATE_PURCHASED + " INTEGER, " +
                CurrentEntry.COLUMN_EXPIRATION_DATE + " INTEGER, " +
                CurrentEntry.COLUMN_FOOD_KEY + " INTEGER, " +
                CurrentEntry.COLUMN_QUANTITY + " INTEGER, " +
                " FOREIGN KEY (" + CurrentEntry.COLUMN_FOOD_KEY + ") REFERENCES " +
                InfoEntry.TABLE_NAME + " (" + InfoEntry._ID + "));";

        final String SQL_CREATE_THROWN_TABLE = "CREATE TABLE " + ThrownEntry.TABLE_NAME + " (" +
                ThrownEntry._ID + " INTEGER PRIMARY KEY," +
                ThrownEntry.COLUMN_DATE_PURCHASED + " INTEGER, " +
                ThrownEntry.COLUMN_EXPIRATION_DATE + " INTEGER, " +
                ThrownEntry.COLUMN_FOOD_KEY + " INTEGER, " +
                ThrownEntry.COLUMN_QUANTITY + " INTEGER, " +
                ThrownEntry.COLUMN_DATE_THROWN + " INTEGER, " +
                " FOREIGN KEY (" + ThrownEntry.COLUMN_FOOD_KEY + ") REFERENCES " +
                InfoEntry.TABLE_NAME + " (" + InfoEntry._ID + "));";

        final String SQL_CREATE_CONSUMED_TABLE = "CREATE TABLE " + ConsumedEntry.TABLE_NAME + " (" +
                ConsumedEntry._ID + " INTEGER PRIMARY KEY," +
                ConsumedEntry.COLUMN_DATE_PURCHASED + " INTEGER, " +
                ConsumedEntry.COLUMN_EXPIRATION_DATE + " INTEGER, " +
                ConsumedEntry.COLUMN_FOOD_KEY + " INTEGER, " +
                ConsumedEntry.COLUMN_QUANTITY + " INTEGER, " +
                ConsumedEntry.COLUMN_DATE_CONSUMED + " INTEGER, " +
                " FOREIGN KEY (" + ConsumedEntry.COLUMN_FOOD_KEY + ") REFERENCES " +
                InfoEntry.TABLE_NAME + " (" + InfoEntry._ID + "));";



        sqLiteDatabase.execSQL(SQL_CREATE_INFO_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CURRENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CONSUMED_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_THROWN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InfoEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ConsumedEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CurrentEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ThrownEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
