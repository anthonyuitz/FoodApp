//package com.khmkau.codeu.foodapp;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.content.Loader;
//import android.support.v4.view.MenuItemCompat;
//import android.support.v7.widget.ShareActionProvider;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.khmkau.codeu.foodapp.data.FoodContract;
//
///**
// * A fragment containing a simple view.
// */
//public class NutritionalDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
//
//    private static final String LOG_TAG = NutritionalDetailFragment.class.getSimpleName();
//    static final String DETAIL_URI = "URI";
//
//    private String mForecast;
//    private Uri mUri;
//
//    private static final int DETAIL_LOADER = 0;
//
//    private static final String[] DETAIL_COLUMNS = {
//            FoodContract.CurrentEntry.TABLE_NAME + "." + FoodContract.CurrentEntry.COLUMN_FOOD_KEY,
//            FoodContract.CurrentEntry.COLUMN_QUANTITY,
//            FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED,
//            FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE,
//            FoodContract.CurrentEntry.COLUMN_VALUE,
//            // This works because the FoodProvider returns currentfood data joined with
//            // foodinfo data, even though they're stored in two different tables.
//            FoodContract.InfoEntry.COLUMN_FOOD_GROUP,
//            FoodContract.InfoEntry.COLUMN_CALORIES,
//            FoodContract.InfoEntry.COLUMN_FOOD_NAME,
//            FoodContract.InfoEntry.COLUMN_SUGAR,
//            FoodContract.InfoEntry.COLUMN_SODIUM
//    };
//
//    // These indices are tied to DETAIL_COLUMNS.  If DETAIL_COLUMNS changes, these
//    // must change.
//    public static final int COL_FOOD_ID = 0;
//    public static final int COL_FOOD_QUANTITY = 1;
//    public static final int COL_DATE_PURCHASED = 2;
//    public static final int COL_EXPIRATION_DATE = 3;
//    public static final int COL_VALUE = 4;
//    public static final int COL_FOOD_GROUP = 5;
//    public static final int COL_CALORIES = 6;
//    public static final int COL_FOOD_NAME = 7;
//    public static final int COL_COLUMN_SUGAR = 8;
//    public static final int COL_COLUMN_SODIUM = 9;
//
//    private ImageView mIconView;
//    private TextView mFriendlyDateView;
//    private TextView mDateView;
//    private TextView mDescriptionView;
//    private TextView mHighTempView;
//    private TextView mLowTempView;
//    private TextView mHumidityView;
//    private TextView mWindView;
//    private TextView mPressureView;
//
//    public NutritionalDetailFragment() {
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        Bundle arguments = getArguments();
//        if (arguments != null) {
//            mUri = arguments.getParcelable(DetailFragment.DETAIL_URI);
//        }
//
//        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
//        mIconView = (ImageView) rootView.findViewById(R.id.detail_icon);
//        mDateView = (TextView) rootView.findViewById(R.id.detail_date_textview);
//        mFriendlyDateView = (TextView) rootView.findViewById(R.id.detail_day_textview);
//        mDescriptionView = (TextView) rootView.findViewById(R.id.detail_forecast_textview);
//        mHighTempView = (TextView) rootView.findViewById(R.id.detail_high_textview);
//        mLowTempView = (TextView) rootView.findViewById(R.id.detail_low_textview);
//        mHumidityView = (TextView) rootView.findViewById(R.id.detail_humidity_textview);
//        mWindView = (TextView) rootView.findViewById(R.id.detail_wind_textview);
//        mPressureView = (TextView) rootView.findViewById(R.id.detail_pressure_textview);
//        return rootView;
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        inflater.inflate(R.menu.detailfragment, menu);
//
//        // Retrieve the share menu item
//        MenuItem menuItem = menu.findItem(R.id.action_share);
//
//        // Get the provider and hold onto it to set/change the share intent.
//        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
//
//        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
//        if (mForecast != null) {
//            mShareActionProvider.setShareIntent(createShareForecastIntent());
//        }
//    }
//
//    private Intent createShareForecastIntent() {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//        shareIntent.setType("text/plain");
//        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecast + FORECAST_SHARE_HASHTAG);
//        return shareIntent;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
//        super.onActivityCreated(savedInstanceState);
//    }
//
//    void onLocationChanged( String newLocation ) {
//        // replace the uri, since the location has changed
//        Uri uri = mUri;
//        if (null != uri) {
//            long date = WeatherContract.WeatherEntry.getDateFromUri(uri);
//            Uri updatedUri = WeatherContract.WeatherEntry.buildWeatherLocationWithDate(newLocation, date);
//            mUri = updatedUri;
//            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
//        }
//    }
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        if ( null != mUri ) {
//            // Now create and return a CursorLoader that will take care of
//            // creating a Cursor for the data being displayed.
//            return new CursorLoader(
//                    getActivity(),
//                    mUri,
//                    DETAIL_COLUMNS,
//                    null,
//                    null,
//                    null
//            );
//        }
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        if (data != null && data.moveToFirst()) {
//            // Read weather condition ID from cursor
//            int weatherId = data.getInt(COL_WEATHER_CONDITION_ID);
//
//            // Use weather art image
//            mIconView.setImageResource(Utility.getArtResourceForWeatherCondition(weatherId));
//
//            // Read date from cursor and update views for day of week and date
//            long date = data.getLong(COL_WEATHER_DATE);
//            String friendlyDateText = Utility.getDayName(getActivity(), date);
//            String dateText = Utility.getFormattedMonthDay(getActivity(), date);
//            mFriendlyDateView.setText(friendlyDateText);
//            mDateView.setText(dateText);
//
//            // Read description from cursor and update view
//            String description = data.getString(COL_WEATHER_DESC);
//            mDescriptionView.setText(description);
//
//            // For accessibility, add a content description to the icon field
//            mIconView.setContentDescription(description);
//
//            // Read high temperature from cursor and update view
//            boolean isMetric = Utility.isMetric(getActivity());
//
//            double high = data.getDouble(COL_WEATHER_MAX_TEMP);
//            String highString = Utility.formatTemperature(getActivity(), high);
//            mHighTempView.setText(highString);
//
//            // Read low temperature from cursor and update view
//            double low = data.getDouble(COL_WEATHER_MIN_TEMP);
//            String lowString = Utility.formatTemperature(getActivity(), low);
//            mLowTempView.setText(lowString);
//
//            // Read humidity from cursor and update view
//            float humidity = data.getFloat(COL_WEATHER_HUMIDITY);
//            mHumidityView.setText(getActivity().getString(R.string.format_humidity, humidity));
//
//            // Read wind speed and direction from cursor and update view
//            float windSpeedStr = data.getFloat(COL_WEATHER_WIND_SPEED);
//            float windDirStr = data.getFloat(COL_WEATHER_DEGREES);
//            mWindView.setText(Utility.getFormattedWind(getActivity(), windSpeedStr, windDirStr));
//
//            // Read pressure from cursor and update view
//            float pressure = data.getFloat(COL_WEATHER_PRESSURE);
//            mPressureView.setText(getActivity().getString(R.string.format_pressure, pressure));
//
//            // We still need this for the share intent
//            mForecast = String.format("%s - %s - %s/%s", dateText, description, high, low);
//
//            // If onCreateOptionsMenu has already happened, we need to update the share intent now.
//            if (mShareActionProvider != null) {
//                mShareActionProvider.setShareIntent(createShareForecastIntent());
//            }
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) { }
//}