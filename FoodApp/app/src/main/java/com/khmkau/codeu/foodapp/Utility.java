package com.khmkau.codeu.foodapp;

/**
 * Created by Melissa on 8/7/2015.
 */
public class Utility {

//    public static String getPreferredLocation(Context context) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        return prefs.getString(context.getString(R.string.pref_location_key),
//                context.getString(R.string.pref_location_default));
//    }

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

}
