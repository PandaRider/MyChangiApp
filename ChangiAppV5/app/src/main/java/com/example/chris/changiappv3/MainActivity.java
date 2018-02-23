package com.example.chris.changiappv3;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    private RecyclerView mRVLocations; //mRVFishPrice
    private AdapterLocation mAdapter; //mAdapter

    public LocationDbHelper locationDbHelper;
    public SQLiteDatabase locationDb;

    Button planner;
    SharedPreferences sharedPref;
    ArrayList<String> list;
    boolean algo;
    Float budget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list=new ArrayList<>();
        list.clear();
        planner=findViewById(R.id.dayplanner);
        locationDbHelper=new LocationDbHelper(this);
        locationDb=locationDbHelper.getWritableDatabase();


        sharedPref= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);


        //1st argument: using this key, get the value stored in sharedPreferences
        //2nd argument: if there is no value stored, then the default value is false
        final boolean attractions = sharedPref.getBoolean("attactions",true);
        final boolean lodging= sharedPref.getBoolean("lodging",true);
        final boolean shopping = sharedPref.getBoolean("shopping",true);
        algo=sharedPref.getBoolean("algo",true);
        budget=Float.parseFloat(sharedPref.getString("budget","20"));

        Attractions(attractions);
        lodging(lodging);
        Shopping(shopping);

        planner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this, dayplanner.class);
                    intent.putExtra("algo",algo);
                    intent.putExtra("budget",budget);
                    startActivity(intent);

            }
        });
        //Make call to AsyncTask
        new AsyncFetch().execute();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals("algo")){
            algo=sharedPref.getBoolean("algo",true);
        }
        if(key.equals("budget")){
            budget=Float.parseFloat(sharedPref.getString("budget","20"));
        }
        if (key.equals("attractions")){
            boolean attractions = sharedPref.getBoolean("attractions",false);
            Attractions(attractions);
        }
        if (key.equals("shopping")){
            boolean shopping = sharedPref.getBoolean("shopping",false);
            Shopping(shopping);
        }
        if (key.equals("lodging")){
            boolean eating= sharedPref.getBoolean("lodging",false);
            lodging(eating);
        }
        for(String i:list){
            System.out.println(i);
        }
        new AsyncFetch().execute();
        System.out.println("onSharedPreferenceChanged");
    }

    public void Attractions(boolean status){
    if(status){
        list.add("Attractions");
    }
    else{
        if(list.contains("Attractions")){
            list.remove("Attractions");}
    }
    }

    public void lodging(boolean status){
        if(status){
            list.add("lodging");
        }
        else{
            if(list.contains("lodging")){
                list.remove("lodging");}
        }
    }

    public void Shopping(boolean status){
        if(status){
            list.add("Shopping");
        }
        else{
            if(list.contains("Shopping")){
                list.remove("Shopping");}
        }
    }


    protected void dp(View view){
        Intent intent=new Intent(this,dayplanner.class);
        startActivity(intent);
    }

    public void removeEntireDb(View view){
        list.clear();
        locationDb.delete(LocationsContract.LocationEntry.TABLE_NAME,null,null);
    }

    //Menu========================================================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //This intent triggers the Second Activity upon the selection of Settings
        if(id == R.id.settings){
            //code for the intent goes here
            Intent intent=new Intent(this,Settings_Activity.class);
            startActivity(intent);
            return true;
        }
        return true;
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            InputStream input;
            StringBuilder result =null;
            try {
                input = getAssets().open("data.json");

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Pass data to onPostExecute method

            } catch (IOException e) {
                e.printStackTrace();
            }

            return (result.toString());
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<DataLocation> data=new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    DataLocation Data = new DataLocation();
                    Data.loationImage= json_data.getString("img");
                    Data.loationName= json_data.getString("location_name");
                    Data.catName= json_data.getString("cat_name");
                    Data.ratings= json_data.getString("rating");
                    Data.price= json_data.getInt("price");
                    if(list.contains(Data.catName)) {
                        data.add(Data);
                    }
                }

                // Setup and Handover data to recyclerview
                mRVLocations = (RecyclerView)findViewById(R.id.locationlist);
                mAdapter = new AdapterLocation(MainActivity.this, data);
                mRVLocations.setAdapter(mAdapter);
                mRVLocations.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }


    }
}
