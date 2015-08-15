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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.khmkau.codeu.foodapp.data.FoodContract;

/**
 * A fragment containing a simple view of the Food items.
 */
public class FoodFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public static final String LOG_TAG = FoodFragment.class.getSimpleName();
    private FoodAdapter foodAdapter;

    private SwipeMenuListView listView;
    private static final int FOOD_LOADER = 0;
    private View rootView;
    private int mPosition = ListView.INVALID_POSITION;

    private static final String SELECTED_KEY = "selected_position";

    // We're showing only a small subset of the stored data.
    // Specify the columns we need.
    private static final String[] FOOD_COLUMNS = {
            FoodContract.CurrentEntry.TABLE_NAME + "." + FoodContract.CurrentEntry._ID,
            FoodContract.InfoEntry.COLUMN_FOOD_NAME,
            FoodContract.CurrentEntry.COLUMN_QUANTITY,
            FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE,
            FoodContract.InfoEntry.COLUMN_FOOD_GROUP,
            FoodContract.InfoEntry.COLUMN_SERVING_UNIT
    };

    // These indices are tied to FOOD_COLUMNS. If FOOD_COLUMNS changes, these must change.
    static final int COL_FOOD_ID = 0;
    static final int COL_FOOD_NAME = 1;
    static final int COL_QUANTITY = 2;
    static final int COL_EXPIRATION_DATE = 3;
    static final int COL_FOOD_GROUP = 4;
    static final int COL_SERVING_UNIT = 5;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView = inflater.inflate(R.layout.fragment_fridge, container, false);

        // The FoodAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        foodAdapter = new FoodAdapter(getActivity(), null, 0);

        // Get a reference to the ListView, and attach this adapter to it.
        listView = (SwipeMenuListView) rootView.findViewById(R.id.ListViewFood);
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
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                int foodID = cursor.getInt(cursor.getColumnIndex(FoodContract.CurrentEntry._ID));
                Bundle bundle = new Bundle();
                bundle.putInt("foodIDKey", foodID);

                switch (index) {
                    case 0: // eat
                        DialogFragment eatFragment = new EatDialogFragment();
                        eatFragment.show(getFragmentManager(), "t");
                        eatFragment.setArguments(bundle);
                        break;
                    case 1: // trash food
                        DialogFragment trashFragment = new TrashDialogFragment();
                        trashFragment.show(getFragmentManager(), "trash");
                        trashFragment.setArguments(bundle);

//                        foodAdapter.remove(item);
//                        foodAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        // We'll call our FridgeActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                int foodID = cursor.getInt(cursor.getColumnIndex(FoodContract.CurrentEntry._ID));
                if (cursor != null) {
                    ((Callback) getActivity())
                            .onItemSelected(FoodContract.CurrentEntry.CONTENT_URI);
                }
                mPosition = position;

            }
        });

        // If there's instance state, mine it for useful information.
        // The end-goal here is that the user never knows that turning their device sideways
        // does crazy lifecycle related things.  It should feel like some stuff stretched out,
        // or magically appeared to take advantage of room, but data or place in the app was never
        // actually *lost*.
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = parentView.getSelectedItemPosition();
                // updates the loader with appropriated sort order query
                resetLoader();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return rootView;
    }

    public void resetLoader() {
        getLoaderManager().restartLoader(FOOD_LOADER, null, this);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // used to populate an initial list of food; DEMO PURPOSES ONLY
        insertMockData();

        getLoaderManager().initLoader(FOOD_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private void insertMockData() {
        for (int i = 0; i < 3; i++) {
            ContentValues infoValues = new ContentValues();
            infoValues.put(FoodContract.InfoEntry.COLUMN_FOOD_NAME, "Apple");
            infoValues.put(FoodContract.InfoEntry.COLUMN_FOOD_GROUP, "Fruit");
            infoValues.put(FoodContract.InfoEntry.COLUMN_SERVING_UNIT, "large");
            infoValues.put(FoodContract.InfoEntry.COLUMN_CALORIES, 5);

            Uri insertedUri = getActivity().getContentResolver().insert(FoodContract.InfoEntry.CONTENT_URI, infoValues);

            // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
            long infoRowId = ContentUris.parseId(insertedUri);

            ContentValues currentValues = new ContentValues();
            currentValues.put(FoodContract.CurrentEntry.COLUMN_FOOD_KEY, infoRowId);
            currentValues.put(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED, 1419033600L);
            currentValues.put(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE, 1419033600L);
            currentValues.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, 3);
            currentValues.put(FoodContract.CurrentEntry.COLUMN_VALUE, 49);

            getActivity().getContentResolver().insert(FoodContract.CurrentEntry.CONTENT_URI, currentValues);
        }
        //////////////////////
//
//        ContentValues infoValues2 = new ContentValues();
//        infoValues2.put(FoodContract.InfoEntry.COLUMN_FOOD_NAME, "Pork");
//        infoValues2.put(FoodContract.InfoEntry.COLUMN_FOOD_GROUP, "Protein");
//        infoValues2.put(FoodContract.InfoEntry.COLUMN_SERVING_UNIT, "lbs");
//        infoValues2.put(FoodContract.InfoEntry.COLUMN_CALORIES, 5);
//
//        Uri insertedUri2 = getActivity().getContentResolver().insert(FoodContract.InfoEntry.CONTENT_URI, infoValues2);
//
//        // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
//        long infoRowId2 = ContentUris.parseId(insertedUri2);
//
//        ContentValues currentValues2 = new ContentValues();
//        currentValues2.put(FoodContract.CurrentEntry.COLUMN_FOOD_KEY, infoRowId2);
//        currentValues2.put(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED, 2222033600L);
//        currentValues2.put(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE, 2345033600L);
//        currentValues2.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, 11);
//
//        getActivity().getContentResolver().insert(FoodContract.CurrentEntry.CONTENT_URI, currentValues2);
//
//        //////////////////////
//
//        ContentValues infoValues3 = new ContentValues();
//        infoValues3.put(FoodContract.InfoEntry.COLUMN_FOOD_NAME, "Water");
//        infoValues3.put(FoodContract.InfoEntry.COLUMN_FOOD_GROUP, "Liquid");
//        infoValues3.put(FoodContract.InfoEntry.COLUMN_SERVING_UNIT, "Cups");
//        infoValues3.put(FoodContract.InfoEntry.COLUMN_CALORIES, 1);
//
//        Uri insertedUri3 = getActivity().getContentResolver().insert(FoodContract.InfoEntry.CONTENT_URI, infoValues3);
//
//        // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
//        long infoRowId3 = ContentUris.parseId(insertedUri3);
//
//        ContentValues currentValues3 = new ContentValues();
//        currentValues3.put(FoodContract.CurrentEntry.COLUMN_FOOD_KEY, infoRowId3);
//        currentValues3.put(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED, 1419033600L);
//        currentValues3.put(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE, 1550033600L);
//        currentValues3.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, 5);
//
//        getActivity().getContentResolver().insert(FoodContract.CurrentEntry.CONTENT_URI, currentValues3);
//
//        //////////////////////
//        ContentValues infoValues4 = new ContentValues();
//        infoValues4.put(FoodContract.InfoEntry.COLUMN_FOOD_NAME, "Cheese");
//        infoValues4.put(FoodContract.InfoEntry.COLUMN_FOOD_GROUP, "Dairy");
//        infoValues4.put(FoodContract.InfoEntry.COLUMN_SERVING_UNIT, "wedges");
//        infoValues4.put(FoodContract.InfoEntry.COLUMN_CALORIES, 22);
//
//        Uri insertedUri4 = getActivity().getContentResolver().insert(FoodContract.InfoEntry.CONTENT_URI, infoValues4);
//
//        // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
//        long infoRowId4 = ContentUris.parseId(insertedUri4);
//
//        ContentValues currentValues4 = new ContentValues();
//        currentValues4.put(FoodContract.CurrentEntry.COLUMN_FOOD_KEY, infoRowId4);
//        currentValues4.put(FoodContract.CurrentEntry.COLUMN_DATE_PURCHASED, 1111033600L);
//        currentValues4.put(FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE, 2334033600L);
//        currentValues4.put(FoodContract.CurrentEntry.COLUMN_QUANTITY, 7);
//
//        getActivity().getContentResolver().insert(FoodContract.CurrentEntry.CONTENT_URI, currentValues4);
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        // When tablets rotate, the currently selected list item needs to be saved.
//        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
//        // so check for that before storing.
//        if (mPosition != ListView.INVALID_POSITION) {
//            outState.putInt(SELECTED_KEY, mPosition);
//        }
//        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // String sortOrder = FoodContract.InfoEntry.COLUMN_FOOD_NAME + " ASC"; // initially sort by
        // String sortOrder = FoodContract.CurrentEntry.COLUMN_QUANTITY + " DESC";
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        String sortOrder = Utility.getSortOrderByName(spinner.getSelectedItem().toString()) + " ASC";

        Uri currentFoodUri = FoodContract.CurrentEntry.CONTENT_URI;

        return new CursorLoader(getActivity(),
                currentFoodUri,
                FOOD_COLUMNS,
                null,
                null,
                sortOrder); //sortOrder or null if order does not matter
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        foodAdapter.swapCursor(cursor);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            listView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        foodAdapter.swapCursor(null);
    }



}
