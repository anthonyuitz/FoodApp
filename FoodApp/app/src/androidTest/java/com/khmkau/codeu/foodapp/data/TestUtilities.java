package com.khmkau.codeu.foodapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import com.khmkau.codeu.foodapp.utils.PollingCheck;

import java.util.Map;
import java.util.Set;

/*
    Students: These are functions and some test data to make it easier to test your database and
    Content Provider.  Note that you'll want your WeatherContract class to exactly match the one
    in our solution to use these as-given.
 */
public class TestUtilities extends AndroidTestCase {
    static final String TEST_LOCATION = "99705";
    static final long TEST_DATE = 1419033600L;  // December 20th, 2014

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    /*
        Students: Use this to create some default weather values for your database tests.
     */
    static ContentValues createCurrentValues(long infoRowId) {
        ContentValues currentValues = new ContentValues();
        currentValues.put(FoodContract.CurrentEntry.COLUMN_FOOD_KEY, infoRowId);
        currentValues.put(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED, TEST_DATE);
        currentValues.put(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE, TEST_DATE);
        currentValues.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, 3);

        return currentValues;
    }

    static ContentValues createThrownValues(long infoRowId) {
        ContentValues thrownValues = new ContentValues();
        thrownValues.put(FoodContract.ThrownEntry.COLUMN_FOOD_KEY, infoRowId);
        thrownValues.put(FoodContract.ThrownEntry.COLUMN_DATE_PURCHASED, TEST_DATE);
        thrownValues.put(FoodContract.ThrownEntry.COLUMN_EXPIRATION_DATE, TEST_DATE);
        thrownValues.put(FoodContract.ThrownEntry.COLUMN_QUANTITY, 3);
        thrownValues.put(FoodContract.ThrownEntry.COLUMN_DATE_THROWN, TEST_DATE);

        return thrownValues;
    }

    static ContentValues createConsumedValues(long infoRowId) {
        ContentValues consumedValues = new ContentValues();
        consumedValues.put(FoodContract.ConsumedEntry.COLUMN_FOOD_KEY, infoRowId);
        consumedValues.put(FoodContract.ConsumedEntry.COLUMN_DATE_PURCHASED, TEST_DATE);
        consumedValues.put(FoodContract.ConsumedEntry.COLUMN_EXPIRATION_DATE, TEST_DATE);
        consumedValues.put(FoodContract.ConsumedEntry.COLUMN_QUANTITY, 3);
        consumedValues.put(FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED, TEST_DATE);

        return consumedValues;
    }

    static ContentValues createFoodInfoValues() {
        ContentValues infoValues = new ContentValues();
        infoValues.put(FoodContract.InfoEntry.COLUMN_FOOD_NAME, "Apple");
        infoValues.put(FoodContract.InfoEntry.COLUMN_FOOD_GROUP, "Fruit");
        infoValues.put(FoodContract.InfoEntry.COLUMN_SERVING_UNIT, "large fruit");
        infoValues.put(FoodContract.InfoEntry.COLUMN_CALORIES, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_SATURATED_FAT, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_TRANS_FAT, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_TOTAL_FAT, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_CHOLESTEROL, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_SODIUM, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_TOTAL_CARBOHYDRATE, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_SUGAR, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_PROTEIN, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_A, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_D, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_E, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_C, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_THIAMIN, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_RIBOFLAVIN, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_NIACIN, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_B6, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_B12, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_CALCIUM, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_PHOSPHOROUS, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_MAGNESIUM, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_IRON, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_ZINC, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_IODINE, 5);

        return infoValues;
    }


//    /*
//        Students: You can uncomment this helper function once you have finished creating the
//        LocationEntry part of the WeatherContract.
//     */
//    static ContentValues createFoodInfoValues() {
//        // Create a new map of values, where column names are the keys
//        ContentValues testValues = new ContentValues();
//        testValues.put(FoodContract.InfoEntry.COLUMN_SERVING_UNIT, "large fruit");
//        testValues.put(FoodContract.InfoEntry.COLUMN_FOOD_NAME, "Orange");
//        testValues.put(FoodContract.InfoEntry.COLUMN_CALORIES, 150);
//
//        return testValues;
//    }

    /*
        Students: You can uncomment this function once you have finished creating the
        LocationEntry part of the WeatherContract as well as the WeatherDbHelper.
     */
    static long insertFoodInfoValues(Context context) {
        // insert our test records into the database
        FoodDbHelper dbHelper = new FoodDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createFoodInfoValues();

        long locationRowId;
        locationRowId = db.insert(FoodContract.InfoEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue("Error: Failure to insert food info values", locationRowId != -1);

        return locationRowId;
    }

    /*
        Students: The functions we provide inside of TestProvider use this utility class to test
        the ContentObserver callbacks using the PollingCheck class that we grabbed from the Android
        CTS tests.
        Note that this only tests that the onChange function is called; it does not test that the
        correct Uri is returned.
     */
    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}