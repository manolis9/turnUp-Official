package com.example.manolis.googlemapspractice;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manolis on 2016-07-14.
 */
public class Pop extends Activity{

    ListView teammatesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop);

        /* Set the Pop-up to take 60 percent of the screen */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.6), (int) (height*0.6));

        /* Initialize the ListView of the teammates to take teammates from an ArrayList*/
        teammatesList = (ListView) findViewById(R.id.Teammates);

        List<String> teammates = new ArrayList<String>();
        teammates.add("Vaggelis Mantzios");
        teammates.add("Konstantinos Meggousidis");
        teammates.add("Tzole");
        teammates.add("Jack Sparrow");
        teammates.add("Nikos Charmeleon");
        teammates.add("Buddha");
        teammates.add("Stefania Veriopoulou");
        teammates.add("Matina Vardoulaki");
        teammates.add("Nasif Morris");


        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                teammates );

        teammatesList.setAdapter(arrayAdapter);
    }
}


