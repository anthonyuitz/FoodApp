package com.khmkau.codeu.foodapp;

import android.content.ContentUris;
import android.content.ContentValues;

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

    public static String[] randomfoods = {"Apple", "Bagel", "Banana", "Beer", "Water", "Juice", "Spaghetti",
                            "Carrot", "Coffee", "Cookie", "Rice", "Bread", "Corn", "Chicken",
                            "Egg", "Steak", "Ham", "Oatmeal", "Shrimp", "Butter"};

    public static String[] foodGroups = {"Dairy", "Fruit", "Grains", "Liquid", "Vegetable", "Protein"};

    public static String[] servingUnits = {"g", "mg", "large", "medium", "small", "lbs", "cups"};

    public static String formatDate(long dateInMilliseconds) {
        Date date = new Date(dateInMilliseconds);
        return DateFormat.getDateInstance().format(date);
    }

    public static boolean isNumeric(String str)
    {
        if(str.equals(""))
            return true;
        return str.matches("^-?\\d+$"); //match a number with optional '-' and no decimal.
    }

    public static boolean isDouble(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?"); //match a number with optional '-' and no decimal.
    }

    public static int[] validateDate(String str) {
        String[] dateValues = str.split("/");
        int[] dates = {-1, -1, -1};

        if(dateValues.length != 3) {
            return dates;
        }
        else {
            int month = -1;
            int day = -1;
            int year = -1;
            if(isNumeric(dateValues[0])){
                month = Integer.parseInt(dateValues[0]) - 1;
            }
            if(isNumeric(dateValues[1])){
                day = Integer.parseInt(dateValues[1]);
            }
            if(isNumeric(dateValues[2])){
                year = Integer.parseInt(dateValues[2]) - 1900;
            }
            if(month >= 0 && month <= 11 && day >= 1 && day <= 31 && year != -1) {
                dates[0] = month;
                dates[1] = day;
                dates[2] = year;
            }
            return dates;
        }
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

    public static ContentValues generateTableValues(String tablename) {
        ContentValues tableValues = new ContentValues();
        Date date1 = new Date(115, (int)(Math.random()*4+7), (int)(Math.random()*28+1));
        Date date2 = new Date(115, (int)(Math.random()*4+7), (int)(Math.random()*28+1));
        Date date3 = new Date(115, (int)(Math.random()*4+7), (int)(Math.random()*28+1));
        if(tablename.equals("Current")) {
            tableValues.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, (int)(Math.random()*25+1));
            tableValues.put(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED, FoodContract.normalizeDate(date1.getTime()));
            tableValues.put(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE, FoodContract.normalizeDate(date2.getTime()));
            tableValues.put(FoodContract.CurrentEntry.COLUMN_VALUE, (int)(Math.random()*1000+500));
        }
        else if(tablename.equals("Consumed")) {
            tableValues.put(FoodContract.ConsumedEntry.COLUMN_QUANTITY, (int)(Math.random()*25+1));
            tableValues.put(FoodContract.ConsumedEntry.COLUMN_DATE_PURCHASED, FoodContract.normalizeDate(date1.getTime()));
            tableValues.put(FoodContract.ConsumedEntry.COLUMN_EXPIRATION_DATE, FoodContract.normalizeDate(date2.getTime()));
            tableValues.put(FoodContract.ConsumedEntry.COLUMN_VALUE, (int)(Math.random()*1000+500));
            tableValues.put(FoodContract.ConsumedEntry.COLUMN_DATE_CONSUMED, FoodContract.normalizeDate(date3.getTime()));
        }
        else {
            tableValues.put(FoodContract.ThrownEntry.COLUMN_QUANTITY, (int)(Math.random()*25+1));
            tableValues.put(FoodContract.ThrownEntry.COLUMN_DATE_PURCHASED, FoodContract.normalizeDate(date1.getTime()));
            tableValues.put(FoodContract.ThrownEntry.COLUMN_EXPIRATION_DATE, FoodContract.normalizeDate(date2.getTime()));
            tableValues.put(FoodContract.ThrownEntry.COLUMN_VALUE, (int)(Math.random()*1000+500));
            tableValues.put(FoodContract.ThrownEntry.COLUMN_DATE_THROWN, FoodContract.normalizeDate(date3.getTime()));
        }
        return tableValues;
    }

    public static ContentValues generateFoodValues() {
        ContentValues foodValues = new ContentValues();
        int nameIndex = (int)(Math.random()*randomfoods.length);
        int groupIndex = (int)(Math.random()*foodGroups.length);
        int servingIndex = (int)(Math.random()*servingUnits.length);
        foodValues.put(FoodContract.InfoEntry.COLUMN_FOOD_NAME, randomfoods[nameIndex]);
        foodValues.put(FoodContract.InfoEntry.COLUMN_FOOD_GROUP, foodGroups[groupIndex]);
        foodValues.put(FoodContract.InfoEntry.COLUMN_SERVING_UNIT, servingUnits[servingIndex]);
        foodValues.put(FoodContract.InfoEntry.COLUMN_CALORIES, (int)(310*(Math.random()+1)));
        foodValues.put(FoodContract.InfoEntry.COLUMN_TOTAL_FAT, (int)(12*(Math.random()+1)));
        foodValues.put(FoodContract.InfoEntry.COLUMN_TOTAL_CARBOHYDRATE, (int)(36*(Math.random()+1)));
        foodValues.put(FoodContract.InfoEntry.COLUMN_CHOLESTEROL, (int)(20*(Math.random()+1)));
        foodValues.put(FoodContract.InfoEntry.COLUMN_SODIUM, (int)(290*(Math.random()+1)));
        foodValues.put(FoodContract.InfoEntry.COLUMN_TRANS_FAT, (int)(2*(Math.random()+1)));
        foodValues.put(FoodContract.InfoEntry.COLUMN_SATURATED_FAT, (int)(4*(Math.random()+1)));
        foodValues.put(FoodContract.InfoEntry.COLUMN_PROTEIN, (int)(17*(Math.random()+1)));
        foodValues.put(FoodContract.InfoEntry.COLUMN_SUGAR, (int)(6*(Math.random()+1)));

        return foodValues;
    }

}
