package com.khmkau.codeu.foodapp;

/**
 * Created by Melissa on 7/26/2015.
 */

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment containing a simple view of the Food items.
 */
public class FoodFragment extends Fragment {

    private ArrayAdapter<String> foodAdapter;

    public FoodFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fridge, container, false);

        String[] foodArray = {
                "Apple",
                "Banana",
                "Celery",
                "Water",
                "Omgthisisasuperlongname"
        };

        List<String> foodItems = new ArrayList<String>(Arrays.asList(foodArray));

        // binds the data we want to show to the layout of the list items
        // and a text view (how the text is formatted in a single element
        // of the list). Params: context (global info about the app), ID of
        // list item layout, ID of text view to populate, list of food data
        foodAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_food, // the ID of the overall layout
                R.id.list_item_food_textview, // ID of text view to populate
                foodItems);

        // ORIGINAL CODE!!!!!
//        // obtains the listView layout defined in fragment_main
//        ListView listView = (ListView)rootView.findViewById(R.id.ListViewFood);
//        // sets the connection between the overall list layout to its indv. items
//        // basically sets the (text) list item layouts to the overall list layout
//        listView.setAdapter(foodAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
//
//                                                // displays a momentary "toast" message... demonstrates that an action will
//                                                // occur on item click
//                                                String foodDetails = foodAdapter.getItem(index);
//                                                // Toast.makeText(getActivity(), foodDetails, Toast.LENGTH_SHORT).show();
//
//                                                // launchs an explicit intent to switch to the nutritional detail activity
//                                                // nutritional detail activity to be implemented by Kelly
//                                                Intent intent = new Intent(getActivity(), NutritionalDetailActivity.class)
//                                                        .putExtra(Intent.EXTRA_TEXT, foodDetails); // passes in the food detail info to the detail activity
//                                                startActivity(intent);
//                                            }
//                                        }
//        );

        SwipeMenuListView listView = (SwipeMenuListView)rootView.findViewById(R.id.ListViewFood);

        // old stuff
        listView.setAdapter(foodAdapter);
        // end old stuff

        // creates a new instance of a SwipeMenuCreator
        // and initializes swipe boxes
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(70));

                openItem.setIcon(R.drawable.eat);

//                // INITIAL IMPLEMENTATION ONLY HAS TEXT... set item title
//                openItem.setTitle("Eat");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);

                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(70));
                // set a icon
                deleteItem.setIcon(R.drawable.trash);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set menu creator
        listView.setMenuCreator(creator);

        // sets click/swipe listener
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String item = foodAdapter.getItem(position);
                switch (index) {
                    case 0: // eat
                        // TODO: implement dialog fragment and update database accordingly
                        Toast.makeText(getActivity(), "OPEN UP OPTION DIALOG for eat", Toast.LENGTH_SHORT).show();



                        break;
                    case 1: // trash food
                        // TODO update database to remove item amount
                        foodAdapter.remove(item);
                        foodAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        Spinner spinner = (Spinner)rootView.findViewById(R.id.spinner);
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

//    Can perform tasks in the background thread so that the UI thread doesn't stutter
//    Used in the sunshine app since they were parsing data taken from API... but may not
//    be applicable to our FoodApp.

//    public class FetchWeatherTask extends AsyncTask<Void, Void, Void> {
//
//      @Override
//      protected Void doInBackground(Void... params) { }
//    }

}
