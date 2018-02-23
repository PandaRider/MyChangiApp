package com.example.chris.changiappv3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class dayplanner  extends FragmentActivity implements OnMapReadyCallback {

    public LocationDbHelper locationDbHelper;
    public SQLiteDatabase locationDb;
    TextView testview;
    EditText MapsSearch;
    Button navigate;

    private GoogleMap mMap;
    private Marker marker;
    Context context;
    boolean algo;
    float budget;
    ArrayList<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayplanner);
        context = this;

        list = new ArrayList<>();
        locationDbHelper = new LocationDbHelper(this);
        locationDb = locationDbHelper.getWritableDatabase();
        testview = findViewById(R.id.test);
        testview.setMovementMethod(new ScrollingMovementMethod());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MapsSearch = findViewById(R.id.search);
        navigate = findViewById(R.id.NavigateButton);

        Intent intentExtras = getIntent();
        algo = intentExtras.getBooleanExtra("algo", false);
        budget = intentExtras.getFloatExtra("budget", 20);
        System.out.println(budget);

        onClickGetEntireDb();


    }

    public void onClickGetEntireDb() {

        //TO DO 3.7 Call the query or rawQuery method of the spendingDb object and store the result in a Cursor object
        final String SQL_QUERY_TABLE = "SELECT * FROM " + LocationsContract.LocationEntry.TABLE_NAME;

        //TO DO 3.8 Extract the data from the Cursor object and display it on the textView widget
        Cursor cursor = locationDb.rawQuery(SQL_QUERY_TABLE, null);

        String outstring = "";
        int indexRemarks = cursor.getColumnIndex(LocationsContract.LocationEntry.COL_LOCATIONNAME);
        int indexAmount = cursor.getColumnIndex(LocationsContract.LocationEntry.COL_AMOUNT);
        int counter = 0;
        while (cursor.moveToNext()) {
            String myAmount = cursor.getString(indexAmount);
            String location = cursor.getString(indexRemarks);
//            addRg(location,counter,Integer.parseInt(myAmount));
//            counter+=1;
            // System.out.println(location);
            convert(location);
            outstring = outstring + location + " $" + myAmount + "\n";
        }
        if (list.isEmpty()) {
            testview.setText("No plans has been added");
        } else {
            new AsyncFetch().execute();
        }


    }

    public void removeEntireDb(View view) {
        list.clear();
        locationDb.delete(LocationsContract.LocationEntry.TABLE_NAME, null, null);
        onClickGetEntireDb();
      //  AsyncFetch.
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void MapsSearch(View view) {

        Geocoder geocoder;
        List<Address> addresses;

        geocoder = new Geocoder(this, Locale.getDefault());

        try {

            String locationsearch = MapsSearch.getText().toString();

            addresses = geocoder.getFromLocationName(locationsearch + " singappore", 1);
            double latitude = addresses.get(0).getLatitude();
            double longitude = addresses.get(0).getLongitude();
            LatLng changiAirport = new LatLng(latitude, longitude);
            marker.setPosition(changiAirport);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(changiAirport));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
            marker.setTitle(locationsearch);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //    public void addRg(String location,int id,int Amount){
//        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
//
//        RadioButton radioButton = new RadioButton(this);
//        testview.setText(location+" ");
//        radioButton.setId(id);//set radiobutton id and store it somewhere
//        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
//        rg.addView(radioButton, params);
//    }
    public void convert(String location) {
        System.out.println(location);
        if (location.equals("Marina Bay Sands")) {
            list.add(0);
        } else if (location.equals("Singapore Flyer")) {
            list.add(1);
        } else if (location.equals("Vivo City")) {
            list.add(2);
        } else if (location.equals("Universal Studios")) {
            list.add(3);
        } else if (location.equals("Buddha Tooth Relic Temple")) {
            list.add(4);
        } else if (location.equals("Singapore Zoo")) {
            list.add(5);
        } else if (location.equals("Orchard Road")) {
            list.add(6);
        } else if (location.equals("Jurong Bird Park")) {
            list.add(7);
        } else if (location.equals("Singapore Botanic Gardens")) {
            list.add(8);
        } else if (location.equals("SUTD")) {
            list.add(9);
        }


    }

    @Override
    protected void onStop(){
        super.onStop();

    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        String signature = "";
        boolean mode;

        @Override
        protected String doInBackground(String... strings) {
            Log.i("mode",Boolean.toString(mode));
            if (mode == false) {
                //Linking Brute Algorithm to the Application
                BruteForce.TripObject o = BruteForce.CallBrute(list, budget);
                signature = o.getInfoPrint();
            } else {

                Log.i("TAG", "testtest");
                //Fast algo
                NearestNeighbour fastNN = new NearestNeighbour();
                double myAmt = 20.0;
                fastNN.setBudget(myAmt);

                //convert ArrayList<Integer> to int[]
                int[] ints = new int[list.size()];
                for (int i = 0, len = list.size(); i < len; i++)
                    ints[i] = list.get(i);
                fastNN.setDestination(ints);
                fastNN.getModeOfTransport();
                String pathStr = fastNN.getString();
                signature = "Total Time: " + fastNN.getTotalTime() + "mins" + "                                     Total Cost: $" + fastNN.getTotalCost() + "\n"
                         + pathStr;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            testview.setText(signature);
        }
        @Override
        protected void onPreExecute() {
            testview.setText("Loading plans...");
                mode=algo;

            if (list.size()>=5 && algo==false){
                mode=true;
                Toast.makeText(context,"NOTICE:Fast algo has been activated to accomodate your large request",Toast.LENGTH_LONG).show();
            }
        }

    }
}
