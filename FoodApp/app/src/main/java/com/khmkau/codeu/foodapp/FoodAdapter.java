package com.khmkau.codeu.foodapp;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * FoodAdapter exposes a list of food details from a Cursor to a ListView
 */
public class FoodAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    // Flag to determine if we want to use a separate view for "today".
    private boolean mUseTodayLayout = true;

    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView foodNameView;
        public final TextView servingsView;
        public final TextView expDateView;

        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            foodNameView = (TextView) view.findViewById(R.id.list_item_food_textview);
            servingsView = (TextView) view.findViewById(R.id.list_food_serving_textview);
            expDateView = (TextView) view.findViewById(R.id.list_item_exp_textview);
        }
    }

    public FoodAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        // TODO delete... this is used for sunshine
//        viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(
//                cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));

        viewHolder.iconView.setImageResource(Utility.getImgResourceForFoodGroup(cursor.getString(FoodFragment.COL_FOOD_GROUP)));

        // Read food name from cursor
        String foodName = cursor.getString(FoodFragment.COL_FOOD_NAME);
        // Find TextView and set name of food on it
        viewHolder.foodNameView.setText(foodName);

        // Read number and unit of servings for food
        String servings = cursor.getString(FoodFragment.COL_QUANTITY);
        // Find TextView and set servings on it
        viewHolder.servingsView.setText(servings);

        // For accessibility, add a food name to the icon field
        viewHolder.iconView.setContentDescription(foodName);

        // TODO: delete... from sunshine where sharedPrefs are used here for units
//        // Read user preference for metric or imperial temperature units
//        boolean isMetric = Utility.isMetric(context);

        // Read expiration date from cursor
        String expiration = cursor.getString(FoodFragment.COL_EXP_DATE);
          //  TODO: extra functionality: format date so that it displays, "today", "tomorrow", or the date... or # days left til exp
//        viewHolder.expDateView.setText(Utility.formatDate(expiration));
        viewHolder.expDateView.setText(expiration);

        // TODO: extra functionality... change expiration date color so that...
        // RED = expires today or tomorrow
        // Yellow/orange = expires in 2 days
        // Green = expires in >2 days
        // viewHolder.expDateView.setTextColor(Color.RED);
    }


    // new view for multiple list item types... from sunshine
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        // Choose the layout type
//        int viewType = getItemViewType(cursor.getPosition());
//        int layoutId = -1;
//        switch (viewType) {
//            case VIEW_TYPE_TODAY: {
//                layoutId = R.layout.list_item_forecast_today;
//                break;
//            }
//            case VIEW_TYPE_FUTURE_DAY: {
//                layoutId = R.layout.list_item_forecast;
//                break;
//            }
//        }
//
//        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
//
//        ViewHolder viewHolder = new ViewHolder(view);
//        view.setTag(viewHolder);
//
//        return view;
//    }

}