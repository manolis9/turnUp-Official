package com.example.manolis.googlemapspractice;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import com.google.android.gms.games.event.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import android.view.View.OnClickListener;
import android.widget.TimePicker;
import android.widget.Toast;


/**
 * Created by Manolis on 2016-07-07.
 */
public class CreateGame extends FragmentActivity {


    private GoogleMap googleMap;
    LatLng addressPos; //= new LatLng(50,-70);
    Marker addressMarker;
    EditText addressEditText;
    EditText dateEditText;
    EditText timeEditText;
    EditText descriptionEditText;
    EditText titleEditText;
    Spinner SportsSpinner;
    Spinner PlayersSpinner;
    private String eventDescription;
    private String eventAddress;
    private String eventTitle;
    private String eventDate;
    private String eventTime;
    private String maxNumPlayers;
    private String sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game);

        SportsSpinner = (Spinner) findViewById(R.id.sports_spinner);
        PlayersSpinner = (Spinner) findViewById(R.id.players_spinner);
        InitSpinners();

        dateEditText  = (EditText) findViewById(R.id.Date);
        timeEditText  = (EditText) findViewById(R.id.Time);
        descriptionEditText = (EditText) findViewById(R.id.Description);
        titleEditText = (EditText) findViewById(R.id.create_eventTitle);

        disableKeyPad(dateEditText);
        disableKeyPad(timeEditText);

        initDatePicker(dateEditText);
        initTimePicker(timeEditText);

        /* Pop-up window showing teammates*/

        Button teammatesButton = (Button) findViewById(R.id.showTeammates);

        teammatesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateGame.this, Pop.class));

            }
        });

        addressEditText = (EditText) findViewById(R.id.addressEditText);

        try {

            if(googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.create_map_fragment)).getMap();
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            googleMap.setMyLocationEnabled(true);
           // googleMap.setTrafficEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {

                    /* Place a marker by tapping on the map. Add that marker to markers ArrayList*/
                    MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(point.latitude, point.longitude)).title(String.valueOf(point.latitude)
                            + ", " + String.valueOf(point.longitude));
                    googleMap.clear();
                    googleMap.addMarker(marker);

                }
            });

            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng point) {

                    //Remove all the markers from the map when tapping and holding
                    googleMap.clear();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAddressMarker(View view) {

        String newAddress = addressEditText.getText().toString();

        if (newAddress != null) {
            new PlaceMarker().execute(newAddress);
        } //else System.out.print("Error - Address Null");

    }

class PlaceMarker extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {

        String startAddress = params[0];
        startAddress = startAddress.replace(" ", "%20");

        try {
            getLatLong(startAddress);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        googleMap.clear();
        addressMarker = googleMap.addMarker(new MarkerOptions().position(addressPos).title(String.valueOf(addressPos.latitude)
                + ", " + String.valueOf(addressPos.longitude)).draggable(true));

       // googleMap.moveCamera(CameraUpdateFactory.newLatLng(addressPos));
        float zoomLevel = (float) 12.0; //This goes up to 21
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addressPos, zoomLevel));
    }
}

    protected void getLatLong(String address) throws UnsupportedEncodingException {

        String query = URLEncoder.encode(address, "utf-8");

        String uri = "http://maps.google.com/maps/api/geocode/json?address="+query+"&sensor=false";

        HttpGet httpGet = new HttpGet(uri);

        HttpClient client = new DefaultHttpClient();

        HttpResponse response;

        StringBuilder stringBuilder = new StringBuilder();

        try {

            response = client.execute(httpGet);

            HttpEntity entity = response.getEntity();

            InputStream stream = entity.getContent();

            int byteData;

            while ((byteData = stream.read()) != -1) {
                stringBuilder.append((char) byteData);
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double lat = 0.0, lng = 0.0;

        // addressPos = new LatLng(lat, lng);

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            addressPos = new LatLng(lat, lng);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void InitSpinners(){

         /*Drop down list (spinner) to select a sport */

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> SportsAdapter = ArrayAdapter.createFromResource(this,
                R.array.sports_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        SportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        SportsSpinner.setAdapter(SportsAdapter);

        /*Drop down list (spinner) to select number of players */

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> PlayersAdapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        SportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        PlayersSpinner.setAdapter(PlayersAdapter);

    }

    private void disableKeyPad(final EditText editText){

        /* Disable the keyboard from popping up when tapping on the Date edittext*/
        editText.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = editText.getInputType(); // backup the input type
                editText.setInputType(InputType.TYPE_NULL); // disable soft input
                editText.onTouchEvent(event); // call native handler
                editText.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });
    }

    private void initDatePicker(final EditText editText){

         /* Initialize pop-up calendar */

        final Calendar calendar = Calendar.getInstance();
        final  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText, calendar);
            }

        };

        /* Pop-up calendar showing when tapping on the Date edittext */

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateGame.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(EditText editText, Calendar calendar) {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(calendar.getTime()));
    }

    private void initTimePicker(final EditText editText){

         /* Pop-up clock showing when tapping on the Time edittext */

        editText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar myTime = Calendar.getInstance();
                int hour = myTime.get(Calendar.HOUR_OF_DAY);
                int minute = myTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateGame.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editText.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

    }

    public void createGame(View v) {

        Event event = new Event();

        this.eventDescription = descriptionEditText.getText().toString();
        this.eventAddress = addressEditText.getText().toString();
        this.eventTitle = titleEditText.getText().toString();
        this.eventDate = dateEditText.getText().toString();
        this.eventTime = timeEditText.getText().toString();
        this.maxNumPlayers = PlayersSpinner.getSelectedItem().toString();
        this.sport = SportsSpinner.getSelectedItem().toString();

        if ((this.eventTitle != null) && (!eventTitle.isEmpty())) {

            if ((this.eventAddress != null) && (!eventAddress.isEmpty())) {

                if ((this.eventDate != null) && (!eventDate.isEmpty())) {

                    if ((this.eventTime != null) && (!eventTime.isEmpty())) {

                        if (!(PlayersSpinner.getSelectedItemPosition() == 0)) {

                            if (!(SportsSpinner.getSelectedItemPosition() == 0)) {

                                event.setDate(this.eventDate);
                                event.setTime(this.eventTime);
                                event.setDescription(this.eventDescription);
                                event.setAddress(this.eventAddress);
                                event.setTitle(this.eventTitle);
                                event.setNumberOfPlayers(this.maxNumPlayers);
                                event.setSport(this.sport);

                            }else{
                                Toast toast = Toast.makeText(this, "No Sport Selected", Toast.LENGTH_SHORT);
                                toast.show();
                                return;
                            }
                        }else{
                            Toast toast = Toast.makeText(this, "Number of Players Not Specified", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }
                    }else{
                        Toast toast = Toast.makeText(this, "Event Time not Specified", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                }else{
                    Toast toast = Toast.makeText(this, "Event Date not Specified", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
            }else{
                Toast toast = Toast.makeText(this, "Event Address Not Specified", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        }else{
            Toast toast = Toast.makeText(this, "Event Title Not Specified", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

    }


}

