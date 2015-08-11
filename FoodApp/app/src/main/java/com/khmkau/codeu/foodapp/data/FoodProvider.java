package com.khmkau.codeu.foodapp.data;


import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class FoodProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FoodDbHelper mOpenHelper;

    static final int INFO = 100;
    static final int CURRENT = 200;
    static final int CURRENT_WITH_EXPIRATION = 201;
    static final int CURRENT_WITH_INFO_AND_FOODGROUP = 202;
    static final int CURRENT_WITH_INFO_AND_CALORIES = 203;
    static final int CONSUMED = 300;
    static final int CONSUMED_WITH_INFO_AND_DATE = 301;
    static final int CONSUMED_WITH_INFO_AND_DATERANGE = 302;
    static final int CONSUMED_WITH_INFO_AND_DATERANGE_AND_FOODGROUP = 303;
    static final int THROWN = 400;
    static final int THROWN_WITH_DATE = 401;
    static final int THROWN_WITH_DATERANGE = 402;

    private static final SQLiteQueryBuilder sCurrentWithInfoQueryBuilder;
    private static final SQLiteQueryBuilder sConsumedWithInfoQueryBuilder;

    static{
        sCurrentWithInfoQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sCurrentWithInfoQueryBuilder.setTables(
                FoodContract.CurrentEntry.TABLE_NAME + " INNER JOIN " +
                        FoodContract.InfoEntry.TABLE_NAME +
                        " ON " + FoodContract.CurrentEntry.TABLE_NAME +
                        "." + FoodContract.CurrentEntry.COLUMN_FOOD_KEY +
                        " = " + FoodContract.InfoEntry.TABLE_NAME +
                        "." + FoodContract.InfoEntry._ID);
    }

    static{
        sConsumedWithInfoQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sConsumedWithInfoQueryBuilder.setTables(
                FoodContract.ConsumedEntry.TABLE_NAME + " INNER JOIN " +
                        FoodContract.InfoEntry.TABLE_NAME +
                        " ON " + FoodContract.ConsumedEntry.TABLE_NAME +
                        "." + FoodContract.ConsumedEntry.COLUMN_FOOD_KEY +
                        " = " + FoodContract.InfoEntry.TABLE_NAME +
                        "." + FoodContract.InfoEntry._ID);
    }


    //current.expiration_date = ?
    private static final String sCurrentExpirationSelection =
            FoodContract.CurrentEntry.TABLE_NAME +
                    "." + FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE + " <= ?";

    //info.food_group = ?
    private static final String sInfoFoodGroupSelection =
            FoodContract.InfoEntry.TABLE_NAME+
                    "." + FoodContract.InfoEntry.COLUMN_FOOD_GROUP + " = ? ";

    //info.calories >= ? AND info.calories <= ?
    private static final String sInfoCaloriesSelection =
            FoodContract.InfoEntry.TABLE_NAME +
                    "." + FoodContract.InfoEntry.COLUMN_CALORIES + " >= ? AND " +
                    FoodContract.InfoEntry.COLUMN_CALORIES + " <= ? ";

    //consumed.consumed_date = ?
    private static final String sConsumedWithDateSelection =
            FoodContract.ConsumedEntry.TABLE_NAME +
                    "." + FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED + " = ? ";

    //consumed.consumed_date >= ? AND consumed.consumed_date <= ?
    private static final String sConsumedWithDateRangeSelection =
            FoodContract.ConsumedEntry.TABLE_NAME +
                    "." + FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED + " >= ? AND " +
                    FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED + " <= ? ";

    //consumed.consumed_date >= ? AND consumed.consumed_date <= ? AND info.food_group = ?
    private static final String sConsumedWithDateRangeAndInfoWithFoodGroupSelection =
            FoodContract.ConsumedEntry.TABLE_NAME +
                    "." + FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED + " >= ? AND " +
                    FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED + " <= ? AND " +
                    FoodContract.InfoEntry.TABLE_NAME + "." + FoodContract.InfoEntry.COLUMN_FOOD_GROUP +
                    " = ? ";

    //thrown.thrown_date = ?
    private static final String sThrownWithDateSelection =
            FoodContract.ThrownEntry.TABLE_NAME +
                    "." + FoodContract.ThrownEntry.COLUMN_DATE_THROWN + " = ? ";

    //thrown.thrown_date >= ? AND thrown.thrown_date <= ?
    private static final String sThrownWithDateRangeSelection =
            FoodContract.ThrownEntry.TABLE_NAME +
                    "." + FoodContract.ThrownEntry.COLUMN_DATE_THROWN + " >= ? AND " +
                    FoodContract.ThrownEntry.COLUMN_DATE_THROWN + " <= ? ";



    // TODO delete if necessary... Melissa's sandbox methond
    private Cursor getCurrentWithInfo(Uri uri, String[] projection, String sortOrder) {
        return sCurrentWithInfoQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCurrentByExpiration(Uri uri, String[] projection, String sortOrder) {
        long Expiration = FoodContract.CurrentEntry.getExpirationFromUri(uri);

        return mOpenHelper.getReadableDatabase().query(
                FoodContract.CurrentEntry.TABLE_NAME,
                projection,
                sCurrentExpirationSelection,
                new String[]{Long.toString(Expiration)},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCurrentByFoodGroup(Uri uri, String[] projection, String sortOrder) {
        String FoodGroup = FoodContract.CurrentEntry.getFoodGroupFromUri(uri);

        return sCurrentWithInfoQueryBuilder.query(
                mOpenHelper.getReadableDatabase(),
                projection,
                sInfoFoodGroupSelection,
                new String[]{FoodGroup},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCurrentByCalories(Uri uri, String[] projection, String sortOrder) {
        String[] calories = FoodContract.CurrentEntry.getCaloriesFromUri(uri);

        return sCurrentWithInfoQueryBuilder.query(
                mOpenHelper.getReadableDatabase(),
                projection,
                sInfoCaloriesSelection,
                new String[]{calories[0],calories[1]},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getConsumedByDate(Uri uri, String[] projection, String sortOrder) {
        long date = FoodContract.ConsumedEntry.getDateFromUri(uri);

        return sConsumedWithInfoQueryBuilder.query(
                mOpenHelper.getReadableDatabase(),
                projection,
                sConsumedWithDateSelection,
                new String[]{Long.toString(date)},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getConsumedByDateRange(Uri uri, String[] projection, String sortOrder) {
        long[] date = FoodContract.ConsumedEntry.getDateRangeFromUri(uri);

        return sConsumedWithInfoQueryBuilder.query(
                mOpenHelper.getReadableDatabase(),
                projection,
                sConsumedWithDateRangeSelection,
                new String[]{Long.toString(date[0]), Long.toString(date[1])},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getConsumedByDateRangeAndFoodGroup(Uri uri, String[] projection, String sortOrder) {
        long[] date = FoodContract.ConsumedEntry.getDateRangeFromUri(uri);
        String foodgroup = FoodContract.ConsumedEntry.getFoodGroupFromUri(uri);

        return sConsumedWithInfoQueryBuilder.query(
                mOpenHelper.getReadableDatabase(),
                projection,
                sConsumedWithDateRangeAndInfoWithFoodGroupSelection,
                new String[]{Long.toString(date[0]), Long.toString(date[1]), foodgroup},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getThrownByDate(Uri uri, String[] projection, String sortOrder) {
        long date = FoodContract.ThrownEntry.getDateFromUri(uri);

        return sConsumedWithInfoQueryBuilder.query(
                mOpenHelper.getReadableDatabase(),
                projection,
                sThrownWithDateSelection,
                new String[]{Long.toString(date)},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getThrownByDateRange(Uri uri, String[] projection, String sortOrder) {
        long[] date = FoodContract.ThrownEntry.getDateRangeFromUri(uri);

        return sConsumedWithInfoQueryBuilder.query(
                mOpenHelper.getReadableDatabase(),
                projection,
                sThrownWithDateRangeSelection,
                new String[]{Long.toString(date[0]), Long.toString(date[1])},
                null,
                null,
                sortOrder
        );
    }

    /*
        Students: Here is where you need to create the UriMatcher. This UriMatcher will
        match each URI to the WEATHER, WEATHER_WITH_LOCATION, WEATHER_WITH_LOCATION_AND_DATE,
        and LOCATION integer constants defined above.  You can test this by uncommenting the
        testUriMatcher test within TestUriMatcher.
     */
    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FoodContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, FoodContract.PATH_INFO, INFO);
        matcher.addURI(authority, FoodContract.PATH_CURRENT, CURRENT);
        matcher.addURI(authority, FoodContract.PATH_CURRENT + "/#", CURRENT_WITH_EXPIRATION);
        matcher.addURI(authority, FoodContract.PATH_CURRENT + "/*", CURRENT_WITH_INFO_AND_FOODGROUP);
        matcher.addURI(authority, FoodContract.PATH_CURRENT + "/#/#", CURRENT_WITH_INFO_AND_CALORIES);

        matcher.addURI(authority, FoodContract.PATH_CONSUMED, CONSUMED);
        matcher.addURI(authority, FoodContract.PATH_CONSUMED + "/#", CONSUMED_WITH_INFO_AND_DATE);
        matcher.addURI(authority, FoodContract.PATH_CONSUMED + "/#/#", CONSUMED_WITH_INFO_AND_DATERANGE);
        matcher.addURI(authority, FoodContract.PATH_CONSUMED + "/#/#/*", CONSUMED_WITH_INFO_AND_DATERANGE_AND_FOODGROUP);

        matcher.addURI(authority, FoodContract.PATH_THROWN, THROWN);
        matcher.addURI(authority, FoodContract.PATH_THROWN + "/*", THROWN_WITH_DATE);
        matcher.addURI(authority, FoodContract.PATH_THROWN + "/*/*", THROWN_WITH_DATERANGE);

        return matcher;
    }

    /*
        Students: We've coded this for you.  We just create a new WeatherDbHelper for later use
        here.
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new FoodDbHelper(getContext());
        return true;
    }

    /*
        Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
        test this by uncommenting testGetType in TestProvider.
     */
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case INFO:
                return FoodContract.InfoEntry.CONTENT_TYPE;
            case CURRENT:
                return FoodContract.CurrentEntry.CONTENT_TYPE;
            case CURRENT_WITH_EXPIRATION:
                return FoodContract.CurrentEntry.CONTENT_TYPE;
            case CURRENT_WITH_INFO_AND_CALORIES:
                return FoodContract.CurrentEntry.CONTENT_TYPE;
            case CURRENT_WITH_INFO_AND_FOODGROUP:
                return FoodContract.CurrentEntry.CONTENT_TYPE;
            case CONSUMED:
                return FoodContract.ConsumedEntry.CONTENT_TYPE;
            case CONSUMED_WITH_INFO_AND_DATE:
                return FoodContract.ConsumedEntry.CONTENT_TYPE;
            case CONSUMED_WITH_INFO_AND_DATERANGE:
                return FoodContract.ConsumedEntry.CONTENT_TYPE;
            case CONSUMED_WITH_INFO_AND_DATERANGE_AND_FOODGROUP:
                return FoodContract.ConsumedEntry.CONTENT_TYPE;
            case THROWN:
                return FoodContract.ThrownEntry.CONTENT_TYPE;
            case THROWN_WITH_DATE:
                return FoodContract.ThrownEntry.CONTENT_TYPE;
            case THROWN_WITH_DATERANGE:
                return FoodContract.ThrownEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "info"
            case INFO:
            {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        FoodContract.InfoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "current"
            case CURRENT: {
                retCursor = getCurrentWithInfo(uri, projection, sortOrder);
//                retCursor = mOpenHelper.getReadableDatabase().query(
//                        FoodContract.CurrentEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder
//                );
                break;
            }
            // "weather/#"
            case CURRENT_WITH_EXPIRATION: {
                retCursor = getCurrentByExpiration(uri, projection, sortOrder);
                break;
            }
            // "current/*"
            case CURRENT_WITH_INFO_AND_FOODGROUP: {
                retCursor = getCurrentByFoodGroup(uri, projection, sortOrder);
                break;
            }
            // "current/#/#"
            case CURRENT_WITH_INFO_AND_CALORIES: {
                retCursor = getCurrentByCalories(uri, projection, sortOrder);
                break;
            }
            // "consumed"
            case CONSUMED: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        FoodContract.ThrownEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //"consumed/#"
            case CONSUMED_WITH_INFO_AND_DATE: {
                retCursor = getConsumedByDate(uri, projection, sortOrder);
                break;
            }
            //"consumed/#/#"
            case CONSUMED_WITH_INFO_AND_DATERANGE: {
                retCursor = getConsumedByDateRange(uri, projection, sortOrder);
                break;
            }
            //"consumed/#/#/*"
            case CONSUMED_WITH_INFO_AND_DATERANGE_AND_FOODGROUP: {
                retCursor = getConsumedByDateRangeAndFoodGroup(uri, projection, sortOrder);
                break;
            }
            //"thrown"
            case THROWN: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        FoodContract.ThrownEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //"thrown/*"
            case THROWN_WITH_DATE: {
                retCursor = getThrownByDate(uri, projection, sortOrder);
                break;
            }
            //"thrown/*/*"
            case THROWN_WITH_DATERANGE: {
                retCursor = getThrownByDateRange(uri, projection, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
        Student: Add the ability to insert Locations to the implementation of this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case INFO: {
                long _id = db.insert(FoodContract.InfoEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FoodContract.InfoEntry.buildInfoUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CURRENT: {
                normalizeDate(values);
                long _id = db.insert(FoodContract.CurrentEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FoodContract.CurrentEntry.buildCurrentUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CONSUMED: {
                normalizeDate(values);
                long _id = db.insert(FoodContract.ConsumedEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FoodContract.ConsumedEntry.buildConsumedUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case THROWN: {
                normalizeDate(values);
                long _id = db.insert(FoodContract.ThrownEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FoodContract.ThrownEntry.buildThrownUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case INFO:
                rowsDeleted = db.delete(
                        FoodContract.InfoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CURRENT:
                rowsDeleted = db.delete(
                        FoodContract.CurrentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CONSUMED:
                rowsDeleted = db.delete(
                        FoodContract.ConsumedEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case THROWN:
                rowsDeleted = db.delete(
                        FoodContract.ThrownEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    private void normalizeDate(ContentValues values) {
        // normalize the date value
        if (values.containsKey(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE)) {
            long dateValue = values.getAsLong(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE);
            values.put(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE, FoodContract.normalizeDate(dateValue));
        }
        if (values.containsKey(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED)) {
            long dateValue = values.getAsLong(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED);
            values.put(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED, FoodContract.normalizeDate(dateValue));
        }
        if (values.containsKey(FoodContract.ConsumedEntry.COLUMN_EXPIRATION_DATE)) {
            long dateValue = values.getAsLong(FoodContract.ConsumedEntry.COLUMN_EXPIRATION_DATE);
            values.put(FoodContract.ConsumedEntry.COLUMN_EXPIRATION_DATE, FoodContract.normalizeDate(dateValue));
        }
        if (values.containsKey(FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED)) {
            long dateValue = values.getAsLong(FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED);
            values.put(FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED, FoodContract.normalizeDate(dateValue));
        }
        if (values.containsKey(FoodContract.ConsumedEntry.COLUMN_DATE_PURCHASED)) {
            long dateValue = values.getAsLong(FoodContract.ConsumedEntry.COLUMN_DATE_PURCHASED);
            values.put(FoodContract.ConsumedEntry.COLUMN_DATE_PURCHASED, FoodContract.normalizeDate(dateValue));
        }
        if (values.containsKey(FoodContract.ThrownEntry.COLUMN_EXPIRATION_DATE)) {
            long dateValue = values.getAsLong(FoodContract.ThrownEntry.COLUMN_EXPIRATION_DATE);
            values.put(FoodContract.ThrownEntry.COLUMN_EXPIRATION_DATE, FoodContract.normalizeDate(dateValue));
        }
        if (values.containsKey(FoodContract.ThrownEntry.COLUMN_DATE_THROWN)) {
            long dateValue = values.getAsLong(FoodContract.ThrownEntry.COLUMN_DATE_THROWN);
            values.put(FoodContract.ThrownEntry.COLUMN_DATE_THROWN, FoodContract.normalizeDate(dateValue));
        }
        if (values.containsKey(FoodContract.ThrownEntry.COLUMN_DATE_PURCHASED)) {
            long dateValue = values.getAsLong(FoodContract.ThrownEntry.COLUMN_DATE_PURCHASED);
            values.put(FoodContract.ThrownEntry.COLUMN_DATE_PURCHASED, FoodContract.normalizeDate(dateValue));
        }
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case INFO:
                rowsUpdated = db.update(FoodContract.InfoEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case CURRENT:
                normalizeDate(values);
                rowsUpdated = db.update(FoodContract.CurrentEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case CONSUMED:
                normalizeDate(values);
                rowsUpdated = db.update(FoodContract.ConsumedEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case THROWN:
                normalizeDate(values);
                rowsUpdated = db.update(FoodContract.ThrownEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount;
        switch (match) {
            case INFO:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(FoodContract.InfoEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case CURRENT:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        normalizeDate(value);
                        long _id = db.insert(FoodContract.CurrentEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case CONSUMED:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        normalizeDate(value);
                        long _id = db.insert(FoodContract.ConsumedEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case THROWN:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        normalizeDate(value);
                        long _id = db.insert(FoodContract.ThrownEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
