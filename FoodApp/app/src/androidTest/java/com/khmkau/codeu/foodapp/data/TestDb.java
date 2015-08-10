package com.khmkau.codeu.foodapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(FoodDbHelper.DATABASE_NAME);
    }

    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */
    public void setUp() {
        deleteTheDatabase();
    }

    /*
        Students: Uncomment this test once you've written the code to create the Location
        table.  Note that you will have to have chosen the same column names that I did in
        my solution for this test to compile, so if you haven't yet done that, this is
        a good time to change your column names to match mine.
        Note that this only tests that the Location table has the correct columns, since we
        give you the code for the weather table.  This test does not look at the
     */
    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(FoodContract.InfoEntry.TABLE_NAME);
        tableNameHashSet.add(FoodContract.ConsumedEntry.TABLE_NAME);
        tableNameHashSet.add(FoodContract.ThrownEntry.TABLE_NAME);
        tableNameHashSet.add(FoodContract.CurrentEntry.TABLE_NAME);

        mContext.deleteDatabase(FoodDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new FoodDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + FoodContract.InfoEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> locationColumnHashSet = new HashSet<String>();
        locationColumnHashSet.add(FoodContract.InfoEntry._ID);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_FOOD_NAME);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_FOOD_GROUP);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_SERVING_UNIT);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_CALORIES);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_SATURATED_FAT);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_TRANS_FAT);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_TOTAL_FAT);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_CHOLESTEROL);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_SODIUM);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_TOTAL_CARBOHYDRATE);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_SUGAR);
        locationColumnHashSet.add(FoodContract.InfoEntry.COLUMN_PROTEIN);
        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            locationColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The Food Info database doesn't contain all of the entry columns",
                locationColumnHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + FoodContract.CurrentEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> currentColumnHashSet = new HashSet<String>();
        currentColumnHashSet.add(FoodContract.CurrentEntry._ID);
        currentColumnHashSet.add(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED);
        currentColumnHashSet.add(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE);
        currentColumnHashSet.add(FoodContract.CurrentEntry.COLUMN_FOOD_KEY);
        currentColumnHashSet.add(FoodContract.CurrentEntry.COLUMN_QUANTITY);
        currentColumnHashSet.add(FoodContract.CurrentEntry.COLUMN_VALUE);

        columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            currentColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The Current database doesn't contain all of the entry columns",
                currentColumnHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + FoodContract.ThrownEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> thrownColumnHashSet = new HashSet<String>();
        thrownColumnHashSet.add(FoodContract.ThrownEntry._ID);
        thrownColumnHashSet.add(FoodContract.ThrownEntry.COLUMN_DATE_PURCHASED);
        thrownColumnHashSet.add(FoodContract.ThrownEntry.COLUMN_EXPIRATION_DATE);
        thrownColumnHashSet.add(FoodContract.ThrownEntry.COLUMN_FOOD_KEY);
        thrownColumnHashSet.add(FoodContract.ThrownEntry.COLUMN_QUANTITY);
        thrownColumnHashSet.add(FoodContract.ThrownEntry.COLUMN_VALUE);
        thrownColumnHashSet.add(FoodContract.ThrownEntry.COLUMN_DATE_THROWN);

        columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            thrownColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The Current database doesn't contain all of the entry columns",
                thrownColumnHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + FoodContract.ConsumedEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> consumedColumnHashSet = new HashSet<String>();
        consumedColumnHashSet.add(FoodContract.ConsumedEntry._ID);
        consumedColumnHashSet.add(FoodContract.ConsumedEntry.COLUMN_DATE_PURCHASED);
        consumedColumnHashSet.add(FoodContract.ConsumedEntry.COLUMN_EXPIRATION_DATE);
        consumedColumnHashSet.add(FoodContract.ConsumedEntry.COLUMN_FOOD_KEY);
        consumedColumnHashSet.add(FoodContract.ConsumedEntry.COLUMN_QUANTITY);
        consumedColumnHashSet.add(FoodContract.ConsumedEntry.COLUMN_VALUE);
        consumedColumnHashSet.add(FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED);

        columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            consumedColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The Current database doesn't contain all of the entry columns",
                consumedColumnHashSet.isEmpty());


        db.close();
    }

    /*
        Students:  Here is where you will build code to test that we can insert and query the
        location database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can uncomment out the "createNorthPoleLocationValues" function.  You can
        also make use of the ValidateCurrentRecord function from within TestUtilities.
    */
    public void testFoodInfoTable() {
        insertFoodInfo();
    }

    /*
        Students:  Here is where you will build code to test that we can insert and query the
        database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can use the "createWeatherValues" function.  You can
        also make use of the validateCurrentRecord function from within TestUtilities.
     */
    public void testWeatherTable() {
        // First insert the location, and then use the locationRowId to insert
        // the weather. Make sure to cover as many failure cases as you can.

        // Instead of rewriting all of the code we've already written in testLocationTable
        // we can move this code to insertLocation and then call insertLocation from both
        // tests. Why move it? We need the code to return the ID of the inserted location
        // and our testLocationTable can only return void because it's a test.

        long infoRowId = insertFoodInfo();

        // Make sure we have a valid row ID.
        assertFalse("Error: Location Not Inserted Correctly", infoRowId == -1L);

        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        FoodDbHelper dbHelper = new FoodDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step (Weather): Create weather values
        ContentValues consumedValues = TestUtilities.createConsumedValues(infoRowId);

        // Third Step (Weather): Insert ContentValues into database and get a row ID back
        long consumedRowId = db.insert(FoodContract.ConsumedEntry.TABLE_NAME, null, consumedValues);
        assertTrue(consumedRowId != -1);

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor consumedCursor = db.query(
                FoodContract.ConsumedEntry.TABLE_NAME,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );

        // Move the cursor to the first valid database row and check to see if we have any rows
        assertTrue( "Error: No Records returned from location query", consumedCursor.moveToFirst() );

        // Fifth Step: Validate the location Query
        TestUtilities.validateCurrentRecord("testInsertReadDb weatherEntry failed to validate",
                consumedCursor, consumedValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse( "Error: More than one record returned from weather query",
                consumedCursor.moveToNext() );

        // Sixth Step: Close cursor and database
        consumedCursor.close();
        dbHelper.close();
    }

    public void testThrownTable() {
        // First insert the location, and then use the locationRowId to insert
        // the weather. Make sure to cover as many failure cases as you can.

        // Instead of rewriting all of the code we've already written in testLocationTable
        // we can move this code to insertLocation and then call insertLocation from both
        // tests. Why move it? We need the code to return the ID of the inserted location
        // and our testLocationTable can only return void because it's a test.

        long infoRowId = insertFoodInfo();

        // Make sure we have a valid row ID.
        assertFalse("Error: Location Not Inserted Correctly", infoRowId == -1L);

        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        FoodDbHelper dbHelper = new FoodDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step (Weather): Create weather values
        ContentValues thrownValues = TestUtilities.createThrownValues(infoRowId);

        // Third Step (Weather): Insert ContentValues into database and get a row ID back
        long thrownRowId = db.insert(FoodContract.ThrownEntry.TABLE_NAME, null, thrownValues);
        assertTrue(thrownRowId != -1);

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor thrownCursor = db.query(
                FoodContract.ThrownEntry.TABLE_NAME,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );

        // Move the cursor to the first valid database row and check to see if we have any rows
        assertTrue( "Error: No Records returned from location query", thrownCursor.moveToFirst() );

        // Fifth Step: Validate the location Query
        TestUtilities.validateCurrentRecord("testInsertReadDb weatherEntry failed to validate",
                thrownCursor, thrownValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse( "Error: More than one record returned from weather query",
                thrownCursor.moveToNext() );

        // Sixth Step: Close cursor and database
        thrownCursor.close();
        dbHelper.close();
    }

    public void testCurrentTable() {
        // First insert the location, and then use the locationRowId to insert
        // the weather. Make sure to cover as many failure cases as you can.

        // Instead of rewriting all of the code we've already written in testLocationTable
        // we can move this code to insertLocation and then call insertLocation from both
        // tests. Why move it? We need the code to return the ID of the inserted location
        // and our testLocationTable can only return void because it's a test.

        long infoRowId = insertFoodInfo();

        // Make sure we have a valid row ID.
        assertFalse("Error: Food Info Not Inserted Correctly", infoRowId == -1L);

        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        FoodDbHelper dbHelper = new FoodDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step (Weather): Create weather values
        ContentValues currentValues = TestUtilities.createCurrentValues(infoRowId);

        // Third Step (Weather): Insert ContentValues into database and get a row ID back
        long currentRowId = db.insert(FoodContract.ConsumedEntry.TABLE_NAME, null, currentValues);
        assertTrue(currentRowId != -1);

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor currentCursor = db.query(
                FoodContract.ConsumedEntry.TABLE_NAME,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );

        // Move the cursor to the first valid database row and check to see if we have any rows
        assertTrue( "Error: No Records returned from location query", currentCursor.moveToFirst() );

        // Fifth Step: Validate the location Query
        TestUtilities.validateCurrentRecord("testInsertReadDb weatherEntry failed to validate",
                currentCursor, currentValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse( "Error: More than one record returned from weather query",
                currentCursor.moveToNext() );

        // Sixth Step: Close cursor and database
        currentCursor.close();
        dbHelper.close();
    }


    /*
        Students: This is a helper method for the testWeatherTable quiz. You can move your
        code from testLocationTable to here so that you can call this code from both
        testWeatherTable and testLocationTable.
     */
    public long insertFoodInfo() {
        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        FoodDbHelper dbHelper = new FoodDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // Second Step: Create ContentValues of what you want to insert
        // (you can use the createNorthPoleLocationValues if you wish)
        ContentValues testValues = TestUtilities.createFoodInfoValues();

        // Third Step: Insert ContentValues into database and get a row ID back
        long infoRowId;
        infoRowId = db.insert(FoodContract.InfoEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue(infoRowId != -1);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                FoodContract.InfoEntry.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // Move the cursor to a valid database row and check to see if we got any records back
        // from the query
        assertTrue( "Error: No Records returned from food info query", cursor.moveToFirst() );

        // Fifth Step: Validate data in resulting Cursor with the original ContentValues
        // (you can use the validateCurrentRecord function in TestUtilities to validate the
        // query if you like)
        TestUtilities.validateCurrentRecord("Error: Food Info Query Validation Failed",
                cursor, testValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse( "Error: More than one record returned from food info query",
                cursor.moveToNext() );

        // Sixth Step: Close Cursor and Database
        cursor.close();
        db.close();
        return infoRowId;
    }
}