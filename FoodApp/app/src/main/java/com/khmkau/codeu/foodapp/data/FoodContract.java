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

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
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


        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        /*
        public static Uri buildWeatherLocation(String locationSetting) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
        }

        public static Uri buildWeatherLocationWithStartDate(
                String locationSetting, long startDate) {
            long normalizedDate = normalizeDate(startDate);
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendQueryParameter(COLUMN_DATE, Long.toString(normalizedDate)).build();
        }

        public static Uri buildWeatherLocationWithDate(String locationSetting, long date) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendPath(Long.toString(normalizeDate(date))).build();
        }

        public static String getLocationSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }

        public static long getStartDateFromUri(Uri uri) {
            String dateString = uri.getQueryParameter(COLUMN_DATE);
            if (null != dateString && dateString.length() > 0)
                return Long.parseLong(dateString);
            else
                return 0;
        } */
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


        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
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

        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
