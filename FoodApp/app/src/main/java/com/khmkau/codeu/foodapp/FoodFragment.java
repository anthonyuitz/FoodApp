package com.khmkau.codeu.foodapp;

/**
 * Created by Melissa on 7/26/2015.
 */

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.khmkau.codeu.foodapp.data.FoodContract;

/**
 * A fragment containing a simple view of the Food items.
 */
public class FoodFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
//    public class FoodFragment extends Fragment {
//    private ArrayAdapter<String> foodAdapter; // HARD CODED ARRAY EXAMPLE
    private FoodAdapter foodAdapter;
    private SwipeMenuListView listView;
    private static final int FOOD_LOADER = 0;

    // We're showing only a small subset of the stored data.
    // Specify the columns we need.
    private static final String[] FOOD_COLUMNS = {
            FoodContract.CurrentEntry.TABLE_NAME + "." + FoodContract.CurrentEntry._ID,
            FoodContract.InfoEntry.COLUMN_FOOD_NAME,
            FoodContract.CurrentEntry.COLUMN_QUANTITY,
            FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE,
            FoodContract.InfoEntry.COLUMN_FOOD_GROUP
    };

    // These indices are tied to FOOD_COLUMNS. If FOOD_COLUMNS changes, these must change.
    static final int COL_FOOD_ID = 0;
    static final int COL_FOOD_NAME = 1;
    static final int COL_QUANTITY = 2;
    static final int COL_EXPIRATION_DATE = 3;
    static final int COL_FOOD_GROUP = 4;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri);
    }

    public FoodFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        FoodDbHelper dbHelper = new FoodDbHelper(getActivity());
//        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues infoValues = new ContentValues();
        infoValues.put(FoodContract.InfoEntry.COLUMN_FOOD_NAME, "Apple");
        infoValues.put(FoodContract.InfoEntry.COLUMN_FOOD_GROUP, "Fruit");
        infoValues.put(FoodContract.InfoEntry.COLUMN_SERVING_UNIT, "large fruit");
        infoValues.put(FoodContract.InfoEntry.COLUMN_CALORIES, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_SATURATED_FAT, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_TRANS_FAT, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_TOTAL_FAT, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_CHOLESTEROL, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_SODIUM, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_TOTAL_CARBOHYDRATE, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_SUGAR, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_PROTEIN, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_A, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_D, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_E, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_C, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_THIAMIN, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_RIBOFLAVIN, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_NIACIN, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_B6, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_VIT_B12, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_CALCIUM, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_PHOSPHOROUS, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_MAGNESIUM, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_IRON, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_ZINC, 5);
        infoValues.put(FoodContract.InfoEntry.COLUMN_IODINE, 5);

        Uri insertedUri = getActivity().getContentResolver().insert(FoodContract.InfoEntry.CONTENT_URI, infoValues);

        // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
        long infoRowId = ContentUris.parseId(insertedUri);

//        // Third Step: Insert ContentValues into database and get a row ID back
//        long infoRowId = db.insert(FoodContract.InfoEntry.TABLE_NAME, null, infoValues);

        ContentValues currentValues = new ContentValues();
        currentValues.put(FoodContract.CurrentEntry.COLUMN_FOOD_KEY, infoRowId);
        currentValues.put(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED, 1419033600L);
        currentValues.put(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE, 1419033600L);
        currentValues.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, 3);

        getActivity().getContentResolver().insert(FoodContract.CurrentEntry.CONTENT_URI, currentValues);

        // long currentRowId = db.insert(FoodContract.ConsumedEntry.TABLE_NAME, null, currentValues);

        // dbHelper.close();
        // db.close();

        // PROBLEM: the join isn't registered with this cursor adapter



        // TODO layout uses fridge or main? This is what sunny uses:
        // View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        View rootView = inflater.inflate(R.layout.fragment_fridge, container, false);

        // The FoodAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        foodAdapter = new FoodAdapter(getActivity(), null, 0);

        // Get a reference to the ListView, and attach this adapter to it.
        listView = (SwipeMenuListView) rootView.findViewById(R.id.ListViewFood);

        // HARD CODED ARRAY EXAMPLE
//        String[] foodArray = {
//                "Apple",
//                "Banana",
//                "Celery",
//                "Water",
//                "Omgthisisasuperlongname"
//        };
//
//        List<String> foodItems = new ArrayList<String>(Arrays.asList(foodArray));
//
//
//         // Binds the data we want to show to the layout of the list items
//         // and a text view (how the text is formatted in a single element
//         // of the list). Params: context (global info about the app), ID of
//         // list item layout, ID of text view to populate, list of food data
//        foodAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                R.layout.list_item_food, // the ID of the overall layout
//                R.id.list_item_food_textview, // ID of text view to populate
//                foodItems);
//
//         SwipeMenuListView listView = (SwipeMenuListView)rootView.findViewById(R.id.ListViewFood);

        listView.setAdapter(foodAdapter);

        // creates a new instance of a SwipeMenuCreator and initializes swipe boxes
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity()); // create "eat" item
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE))); // set item background
                openItem.setWidth(dp2px(70)); // set item width
                openItem.setIcon(R.drawable.eat); // set an icon
                menu.addMenuItem(openItem); // add to menu
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity()); // create "delete" item
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25))); // set item background
                deleteItem.setWidth(dp2px(70)); // set item width
                deleteItem.setIcon(R.drawable.trash); // set a icon
                menu.addMenuItem(deleteItem); // add to menu
            }
        };

        // set menu creator
        listView.setMenuCreator(creator);

        // sets click/swipe listener
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0: // eat
                        // TODO: implement dialog fragment and update database accordingly
                        // Toast.makeText(getActivity(), "OPEN UP OPTION DIALOG for eat", Toast.LENGTH_SHORT).show();

                        DialogFragment eatFragment = new EatDialogFragment();
                        eatFragment.show(getFragmentManager(), "eat");


                        break;
                    case 1: // trash food
                        // TODO update database to remove item amount

                        DialogFragment trashFragment = new TrashDialogFragment();
                        trashFragment.show(getFragmentManager(), "t");

//                        foodAdapter.remove(item);
//                        foodAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        // TODO: extra functionality to list details of each item
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//                // TODO figure this out for cursors
//                // CursorAdapter returns a cursor at the correct position for getItem(), or null
//                // if it cannot seek to that position.
//                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
//                if (cursor != null) {
//                    Intent intent = new Intent(getActivity(), NutritionalDetailActivity.class)
//                            .setData(FoodContract.CurrentEntry.buildCurrentUri(cursor.getLong(COL_FOOD_ID)
//                            ));
//                    startActivity(intent);
//                }
//
//                // TODO delete this old code!
//                // launchs an explicit intent to switch to the nutritional detail activity
////                Intent intent = new Intent(getActivity(), NutritionalDetailActivity.class)
////                        .putExtra(Intent.EXTRA_TEXT, "TEST"); // passes in the food detail info to the detail activity
////                startActivity(intent);
//            }
//        });

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = parentView.getSelectedItemPosition();
                // TODO: change foodAdapter with appropriate SORT QUERY and notify that the adapter has changed!
                // listView.setAdapter(foodAdapter);

                Toast.makeText(getActivity(), "You have selected item " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return rootView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(FOOD_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // This is called when a new Loader needs to be created.  This
        // fragment only uses one loader, so we don't care about checking the id.

        // To only show current and future dates, filter the query to return weather only for
        // dates after or including today.

//        TODO: delete later
//        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
//
//        String locationSetting = Utility.getPreferredLocation(getActivity());
//        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
//                locationSetting, System.currentTimeMillis());
//
//        return new CursorLoader(getActivity(),
//                weatherForLocationUri,
//                FORECAST_COLUMNS, // FOOD_COLUMNS
//                null,
//                null,
//                sortOrder);

        ///////////////////// food implementation? /////////////////////////////////////
        // String sortOrder = FoodContract.InfoEntry.COLUMN_FOOD_NAME + " ASC";

        Uri currentFoodUri = FoodContract.CurrentEntry.CONTENT_URI;

        return new CursorLoader(getActivity(),
                currentFoodUri,
                FOOD_COLUMNS,
                null,
                null,
                null); //sortOrder

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        foodAdapter.swapCursor(cursor);
//        if (mPosition != ListView.INVALID_POSITION) {
//            // If we don't need to restart the loader, and there's a desired position to restore
//            // to, do so now.
//            mListView.smoothScrollToPosition(mPosition);
//        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        foodAdapter.swapCursor(null);
    }

}
