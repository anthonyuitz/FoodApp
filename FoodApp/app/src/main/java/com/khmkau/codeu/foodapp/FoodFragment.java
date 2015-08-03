package com.khmkau.codeu.foodapp;

/**
 * Created by Melissa on 7/26/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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

        // obtains the listView layout defined in fragment_main
        ListView listView = (ListView)rootView.findViewById(R.id.ListViewFood);
        // sets the connection between the overall list layout to its indv. items
        // basically sets the (text) list item layouts to the overall list layout
        listView.setAdapter(foodAdapter);

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
        );

        Spinner spinner = (Spinner)rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = parentView.getSelectedItemPosition();

                Toast.makeText(getActivity(), "You have selected item : " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return rootView;
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
