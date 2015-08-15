package com.khmkau.codeu.foodapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.khmkau.codeu.foodapp.data.FoodContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class NutritionalDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = NutritionalDetailFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    private Uri mUri;

    private static final int DETAIL_LOADER = 0;

    private static final String[] DETAIL_COLUMNS = {
            FoodContract.CurrentEntry.TABLE_NAME + "." + FoodContract.CurrentEntry.COLUMN_FOOD_KEY,
            FoodContract.CurrentEntry.COLUMN_QUANTITY,
            FoodContract.InfoEntry.COLUMN_SERVING_UNIT,
            FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED,
            FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE,
            FoodContract.CurrentEntry.COLUMN_VALUE,
            FoodContract.InfoEntry.COLUMN_FOOD_GROUP,
            FoodContract.InfoEntry.COLUMN_CALORIES,
            FoodContract.InfoEntry.COLUMN_FOOD_NAME,
    };

    // These indices are tied to DETAIL_COLUMNS.  If DETAIL_COLUMNS changes, these
    // must change.
    public static final int COL_FOOD_ID = 0;
    public static final int COL_FOOD_QTY = 1;
    public static final int COL_FOOD_UNIT = 2;
    public static final int COL_DATE_PURCHASED = 3;
    public static final int COL_EXPIRATION_DATE = 4;
    public static final int COL_VALUE = 5;
    public static final int COL_FOOD_GROUP = 6;
    public static final int COL_CALORIES = 7;
    public static final int COL_FOOD_NAME = 8;

    private ImageView mIconView;
    private TextView mFoodName;
    private TextView mServingsSize;
    private TextView mFoodGroupView;
    private TextView mPurchaseDateView;
    private TextView mExpDateView;
    private TextView mCostView;
    private TextView mCaloriesView;

    public NutritionalDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(NutritionalDetailFragment.DETAIL_URI);
        }

        View rootView = inflater.inflate(R.layout.fragment_nutritional_detail, container, false);

        mFoodName = (TextView) rootView.findViewById(R.id.detail_food_name_textview);
        mServingsSize = (TextView) rootView.findViewById(R.id.detail_servings_size_textview);
        mIconView = (ImageView) rootView.findViewById(R.id.detail_icon);
        mFoodGroupView = (TextView) rootView.findViewById(R.id.detail_food_group_textview);
        mPurchaseDateView = (TextView) rootView.findViewById(R.id.detail_purchase_date_textview);
        mExpDateView = (TextView) rootView.findViewById(R.id.detail_exp_date_textview);
        mCostView = (TextView) rootView.findViewById(R.id.detail_cost_textview);
        mCaloriesView = (TextView) rootView.findViewById(R.id.detail_calories_textview);

        return rootView;
    }

    // delete? may be unnecessary
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       if ( null != mUri ) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {

            // Read weather condition ID from cursor
            int foodQty = data.getInt(COL_FOOD_QTY);
            String foodUnit = data.getString(COL_FOOD_UNIT);
            mServingsSize.setText(foodQty + " " + foodUnit);

            long datePurchased = data.getLong(COL_DATE_PURCHASED);
            mPurchaseDateView.setText("Purchase date: " + Utility.formatDate(datePurchased));

            long dateExp = data.getLong(COL_EXPIRATION_DATE);
            mExpDateView.setText("Expiration date: " + Utility.formatDate(dateExp));

            // food group art image
            String foodGroup = data.getString(COL_FOOD_GROUP);
            mFoodGroupView.setText(foodGroup);
            mIconView.setImageResource(Utility.getImgResourceForFoodGroup(foodGroup));

            // For accessibility, add a content description to the icon field
            mIconView.setContentDescription(foodGroup);

            String foodName = data.getString(COL_FOOD_NAME);
            mFoodName.setText(foodName);

            int cost = data.getInt(COL_VALUE);
            mCostView.setText("Cost per serving: " + cost);

            int calories = data.getInt(COL_CALORIES);
            mCaloriesView.setText("Calories per serving: " + calories);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }
}