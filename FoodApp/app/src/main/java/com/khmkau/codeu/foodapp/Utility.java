package com.khmkau.codeu.foodapp;

import com.khmkau.codeu.foodapp.data.FoodContract;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Melissa on 8/7/2015.
 */
public class Utility {

//    public static String getPreferredLocation(Context context) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        return prefs.getString(context.getString(R.string.pref_location_key),
//                context.getString(R.string.pref_location_default));
//    }

    public static String formatDate(long dateInMilliseconds) {
        Date date = new Date(dateInMilliseconds);
        return DateFormat.getDateInstance().format(date);
    }

    public static int getImgResourceForFoodGroup(String foodGroup) {
        if (foodGroup.equalsIgnoreCase("Dairy")) {
            return R.drawable.dairy;
        } else if (foodGroup.equalsIgnoreCase("Fruit")) {
            return R.drawable.fruit;
        } else if (foodGroup.equalsIgnoreCase("Grains")) {
            return R.drawable.grains;
        } else if (foodGroup.equalsIgnoreCase("Liquid")) {
            return R.drawable.liquid;
        } else if (foodGroup.equalsIgnoreCase("Protein")) {
            return R.drawable.protein;
        } else { // Vegetable
            return R.drawable.veg;
        }
    }

    public static String getSortOrderByName(String name) {
        if (name.equals("Name")) {
             return FoodContract.InfoEntry.COLUMN_FOOD_NAME;
        } else if (name.equals("Price")) {
            return FoodContract.CurrentEntry.COLUMN_VALUE;
        } else if (name.equals("Expiration Date")) {
            return FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE;
        } else { // sort order is purchase date
            return FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED;
        }
    }

}
