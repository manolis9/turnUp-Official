package com.example.manolis.googlemapspractice;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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



public class MainActivity extends Activity {

    EditText addressEditText;

    private GoogleMap googleMap;

    LatLng addressPos;

    Marker addressMarker;

   //static final LatLng MyPosition = new LatLng(50, -70);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressEditText = (EditText) findViewById(R.id.addressEditText);


        try {

            if (googleMap == null) {

                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            googleMap.setMyLocationEnabled(true);

            googleMap.setBuildingsEnabled(true);

            googleMap.getUiSettings().setZoomControlsEnabled(true);

           //Marker marker = googleMap.addMarker(new MarkerOptions().position(MyAddress).title("turnUp"));


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    public void showAddressMarker(View view) {

        String newAddress = addressEditText.getText().toString();

        if(newAddress != null){
            new PlaceMarker().execute(newAddress);
        }
        else System.out.print("Error - Address Null");

    }

    class PlaceMarker extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            String startAddress = params[0];

            startAddress = startAddress.replaceAll(" ", "%20");

            getLatLong(startAddress);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            addressMarker = googleMap.addMarker(new MarkerOptions().position(addressPos).title("turnUp"));

        }
    }

    protected void getLatLong(String address){

        String uri = "http://maps.google.com/maps/api.geocode.json?address=" +
                address + "&sensor=false";

        HttpGet httpGet = new HttpGet(uri);

        HttpClient client = new DefaultHttpClient();

        HttpResponse response;

        StringBuilder stringBuilder = new StringBuilder();

        try{

            response = client.execute(httpGet);

            HttpEntity entity = response.getEntity();

            InputStream stream = entity.getContent();

            int byteData;

            while ((byteData = stream.read()) != -1){
                stringBuilder.append((char) byteData);
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double lat = 0.0, lng = 0.0;

        JSONObject jsonObject;

        try{
            jsonObject = new JSONObject(stringBuilder.toString());

            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").getDouble("lng");

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").getDouble("lat");

            addressPos = new LatLng(lat, lng);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
