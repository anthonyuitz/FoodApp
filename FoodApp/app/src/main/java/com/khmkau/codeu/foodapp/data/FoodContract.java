package com.khmkau.codeu.foodapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

public class FoodContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.khmkau.codeu.foodapp.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_CURRENT = "current";
    public static final String PATH_CONSUMED = "consumed";
    public static final String PATH_THROWN = "thrownaway";
    public static final String PATH_INFO = "foodinfo";

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    /* Inner class that defines the table contents of the location table */
    public static final class CurrentEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CURRENT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENT;

        // Table name
        public static final String TABLE_NAME = "current";

        public static final String COLUMN_FOOD_KEY = "food_key";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_DATE_PURCHASED = "date_purchased";
        public static final String COLUMN_EXPIRATION_DATE = "expiration_date";
        public static final String COLUMN_VALUE = "value";

        public static Uri buildCurrentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildCurrentExpiration(long expiration) {
            long normalizedDate = normalizeDate(expiration);
            return CONTENT_URI.buildUpon().appendPath(Long.toString(normalizedDate)).build();
        }

        public static Uri buildCurrentFoodGroup(String foodgroup) {
            return CONTENT_URI.buildUpon().appendPath(foodgroup).build();
        }

        public static Uri buildCurrentCalories(int min_calories, int max_calories) {
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(min_calories))
                    .appendPath(Integer.toString(max_calories)).build();
        }

        public static long getExpirationFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }

        public static String getFoodGroupFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String[] getCaloriesFromUri(Uri uri) {
            String[] calories = new String[2];
            calories[0] = uri.getPathSegments().get(1);
            calories[1] = uri.getPathSegments().get(2);
            return calories;
        }
    }

    /* Inner class that defines the table contents of the weather table */
    public static final class ConsumedEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONSUMED).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONSUMED;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONSUMED;

        public static final String TABLE_NAME = "consumed";

        // Column with the foreign key into the location table.
        public static final String COLUMN_FOOD_KEY = "food_key";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_DATE_CONSUMED = "date_consumed";
        public static final String COLUMN_EXPIRATION_DATE = "expiration_date";
        public static final String COLUMN_DATE_PURCHASED = "date_purchased";
        public static final String COLUMN_VALUE = "value";


        public static Uri buildConsumedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildConsumedDate(long date) {
            long normalizedDate = normalizeDate(date);
            return CONTENT_URI.buildUpon().appendPath(Long.toString(normalizedDate)).build();
        }

        public static Uri buildConsumedDateRange(long min_date, long max_date) {
            long normalizedMinDate = normalizeDate(min_date);
            long normalizedMaxDate = normalizeDate(max_date);
            return CONTENT_URI.buildUpon().appendPath(Long.toString(normalizedMinDate))
                    .appendPath(Long.toString(normalizedMaxDate)).build();
        }

        public static Uri buildConsumedFoodGroup(String foodgroup) {
            return CONTENT_URI.buildUpon().appendPath(foodgroup).build();
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }

        public static long[] getDateRangeFromUri(Uri uri) {
            long[] daterange = new long[2];
            daterange[0] = Long.parseLong(uri.getPathSegments().get(1));
            daterange[1] = Long.parseLong(uri.getPathSegments().get(2));
            return daterange;
        }

        public static String getFoodGroupFromUri(Uri uri) {
            return uri.getPathSegments().get(3);
        }

    }

    public static final class ThrownEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_THROWN).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_THROWN;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_THROWN;

        public static final String TABLE_NAME = "thrown_away";

        // Column with the foreign key into the location table.
        public static final String COLUMN_FOOD_KEY = "food_key";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_DATE_THROWN = "date_thrown";
        public static final String COLUMN_EXPIRATION_DATE = "expiration_date";
        public static final String COLUMN_DATE_PURCHASED = "date_purchased";
        public static final String COLUMN_VALUE = "value";


        public static Uri buildThrownUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildThrownDate(long date) {
            long normalizedDate = normalizeDate(date);
            return CONTENT_URI.buildUpon().appendPath(Long.toString(normalizedDate)).build();
        }

        public static Uri buildThrownDateRange(long min_date, long max_date) {
            long normalizedMinDate = normalizeDate(min_date);
            long normalizedMaxDate = normalizeDate(max_date);
            return CONTENT_URI.buildUpon().appendPath(Long.toString(normalizedMinDate))
                    .appendPath(Long.toString(normalizedMaxDate)).build();
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }

        public static long[] getDateRangeFromUri(Uri uri) {
            long[] daterange = new long[2];
            daterange[0] = Long.parseLong(uri.getPathSegments().get(1));
            daterange[1] = Long.parseLong(uri.getPathSegments().get(2));
            return daterange;
        }

    }

    public static final class InfoEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INFO).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INFO;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INFO;

        public static final String TABLE_NAME = "food_info";

        // Column with the foreign key into the location table.
        public static final String COLUMN_FOOD_NAME = "food_name";
        public static final String COLUMN_FOOD_GROUP = "food_group";
        public static final String COLUMN_SERVING_UNIT = "serving_unit";
        public static final String COLUMN_CALORIES = "calories";
        public static final String COLUMN_SATURATED_FAT = "saturated_fat";
        public static final String COLUMN_TRANS_FAT = "trans_fat";
        public static final String COLUMN_TOTAL_FAT = "total_fat";
        public static final String COLUMN_CHOLESTEROL = "cholesterol";
        public static final String COLUMN_SODIUM = "sodium";
        public static final String COLUMN_TOTAL_CARBOHYDRATE = "total_carbohydrate";
        public static final String COLUMN_SUGAR = "sugar";
        public static final String COLUMN_PROTEIN = "protein";
        public static final String COLUMN_VIT_A = "vit_a";
        public static final String COLUMN_VIT_D = "vit_d";
        public static final String COLUMN_VIT_E = "vit_e";
        public static final String COLUMN_VIT_C = "vit_c";
        public static final String COLUMN_THIAMIN = "thiamin";
        public static final String COLUMN_RIBOFLAVIN = "riboflavin";
        public static final String COLUMN_NIACIN = "niacin";
        public static final String COLUMN_VIT_B6 = "vit_b6";
        public static final String COLUMN_VIT_B12 = "vit_b12";
        public static final String COLUMN_CALCIUM = "calcium";
        public static final String COLUMN_PHOSPHOROUS = "phosphorous";
        public static final String COLUMN_MAGNESIUM = "magnesium";
        public static final String COLUMN_IRON = "iron";
        public static final String COLUMN_ZINC = "zinc";
        public static final String COLUMN_IODINE = "iodine";

        public static Uri buildInfoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
