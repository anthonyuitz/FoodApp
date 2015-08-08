package com.khmkau.codeu.foodapp;

/**
 * Created by Melissa on 7/26/2015.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
public class FoodFragment extends Fragment {

//    private ArrayAdapter<String> foodAdapter;
    private FoodAdapter foodAdapter;
    private SwipeMenuListView listView;

    // For the forecast view we're showing only a small subset of the stored data.
    // Specify the columns we need.
    private static final String[] FOOD_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            FoodContract.CurrentEntry.TABLE_NAME + "." + FoodContract.CurrentEntry._ID,
            FoodContract.InfoEntry.COLUMN_FOOD_NAME,
            FoodContract.CurrentEntry.COLUMN_QUANTITY,
            FoodContract.CurrentEntry.COLUMN_EXPIRATION_DATE,
            FoodContract.InfoEntry.COLUMN_FOOD_GROUP
    };

    // These indices are tied to FORECAST_COLUMNS.  If FORECAST_COLUMNS changes, these
    // must change.
    static final int COL_FOOD_ID = 0;
    static final int COL_FOOD_NAME = 1;
    static final int COL_QUANTITY = 2;
    static final int COL_EXPIRATION_DATE = 3;
    static final int COL_FOOD_GROUP = 4;


    public FoodFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO layout uses fridge or main? This is what sunny uses:
        // View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        View rootView = inflater.inflate(R.layout.fragment_fridge, container, false);

        // The ForecastAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        foodAdapter = new FoodAdapter(getActivity(), null, 0);

        // Get a reference to the ListView, and attach this adapter to it.
        listView = (SwipeMenuListView) rootView.findViewById(R.id.ListViewFood);

        // DEMO PURPOSES
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
//         binds the data we want to show to the layout of the list items
//         and a text view (how the text is formatted in a single element
//         of the list). Params: context (global info about the app), ID of
//         list item layout, ID of text view to populate, list of food data
//        foodAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                R.layout.list_item_food, // the ID of the overall layout
//                R.id.list_item_food_textview, // ID of text view to populate
//                foodItems);
//
//         SwipeMenuListView listView = (SwipeMenuListView)rootView.findViewById(R.id.ListViewFood);

        /* Original code
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                                                // displays a momentary "toast" message... demonstrates that an action will
                                                // occur on item click
                                                String foodDetails = foodAdapter.getItem(index);
                                                // Toast.makeText(getActivity(), foodDetails, Toast.LENGTH_SHORT).show();

                                                // launchs an explicit intent to switch to the nutritional detail activity
                                                // nutritional detail activity to be implemented by Kelly
                                                Intent intent = new Intent(getActivity(), NutritionalDetailActivity.class)
                                                        .putExtra(Intent.EXTRA_TEXT, foodDetails); // passes in the food detail info to the detail activity
                                                startActivity(intent);
                                            }
                                        }
        ); */

        listView.setAdapter(foodAdapter);

        // creates a new instance of a SwipeMenuCreator
        // and initializes swipe boxes
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
                // String item = foodAdapter.getItem(position);
                // Intent intent = new Intent(getActivity(), NutritionalDetailActivity.class)
                // .putExtra(Intent.EXTRA_TEXT, foodDetails); // passes in the food detail info to the detail activity
                // startActivity(intent);

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                // launchs an explicit intent to switch to the nutritional detail activity
                Intent intent = new Intent(getActivity(), NutritionalDetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, "TEST"); // passes in the food detail info to the detail activity
                startActivity(intent);
            }
        });


        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = parentView.getSelectedItemPosition();
                // TODO: change foodAdapter with appropriate SORT QUERY and notify that the adapter has changed!
                Toast.makeText(getActivity(), "You have selected item : " + index, Toast.LENGTH_SHORT).show();
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

}
